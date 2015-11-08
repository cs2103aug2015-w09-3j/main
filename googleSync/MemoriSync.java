//@@author A0098038W
package memori.googleSync;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import memori.ErrorSuppressor;
import memori.Storage.MemoriStorage;
import memori.logic.MemoriCalendar;
import memori.logic.MemoriEvent;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;
import memori.ui.MemoriUI;

public class MemoriSync {

	private static final String PULL_MESSAGE = "Number of events added locally =%1$s\n";
	private static final String PUSH_MESSAGE = "Number of events synced to google =%1$s\n";
	private static final String PUSH_ERROR = "Unable to sync local events to Google Calendar right now\n";
	private static final String PULL_ERROR = "Unable to pull your events from google\n";
	

	private com.google.api.services.calendar.Calendar googleCalendar;
	private GoogleCRUD crud;
	private ArrayList<MemoriEvent> remoteCopy;
	private ArrayList<MemoriEvent> doNotDelete;
	private boolean isConnected;
	private Queue<SyncObject> thingsToSync;
	
	private MemoriStorage st;

	public MemoriSync() {

		ErrorSuppressor.supress();
		googleCalendar = GCalConnect.getCalendarService();
		ErrorSuppressor.unsupress();
		st = MemoriStorage.getInstance();
		SyncObjectQueue wrapper = st.loadQueue();
		if(wrapper == null){
			thingsToSync = new LinkedList<SyncObject>();
		}else{
			thingsToSync = wrapper.theQueue;
		}
		Queue<SyncObject> tempQueue =  new LinkedList<SyncObject>();
		while(!thingsToSync.isEmpty()){
			SyncObject current  = thingsToSync.poll();
			if(current.getCommand().getType() == MemoriCommandType.DELETE){
				tempQueue.offer(current);
			}
		}
		doNotDelete = new ArrayList<MemoriEvent>();
		thingsToSync  = tempQueue;
	}

	public void initialize(MemoriUI ui, MemoriCalendar calendar) {
		if (googleCalendar != null) {
			crud = new GoogleCRUD(googleCalendar);
			try {
				processQueue();
				remoteCopy = crud.retrieveAllEvents();
				isConnected = true;
				ui.displayToUser(pullEvents(calendar));
				ui.displayToUser(pushEvents(calendar));
				checkForConflicts(calendar);
			} catch (UnknownHostException e) {
				isConnected = false;
				ui.displayToUser(PULL_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	public void checkForConflicts(MemoriCalendar calendar) {
		ArrayList<MemoriEvent> localEvents = calendar.getEvents();
		ArrayList<SyncObject> toGoogle = new ArrayList<SyncObject>();
		ArrayList<Integer> toDelete = new ArrayList<Integer>();
		Collections.sort(remoteCopy, MemoriEvent.externalIdComparator);

		//If google Calendar deleted the event or there are differences between gcal/ local use latest update time
		for (int i = 0; i < localEvents.size(); i++) {
			MemoriEvent currentLocal = localEvents.get(i);
			int result = Collections.binarySearch(remoteCopy, currentLocal, MemoriEvent.externalIdComparator);
			if (result >= 0) {
				MemoriEvent currentRemote = remoteCopy.get(result);
				if (!currentLocal.equals(currentRemote)) {
					solveDifferences(currentLocal, currentRemote, toGoogle);
				}
			} else if(!doNotDelete.contains(currentLocal)){
				toDelete.add(i);
			}
		}
		
		Collections.reverse(toDelete);
		for (int i = 0; i < toDelete.size(); i++) {
			int index = toDelete.get(i);
			localEvents.remove(index);
		}
		for (int i = 0; i < toGoogle.size(); i++) {
			MemoriEvent e = toGoogle.get(i).getEvent();
			MemoriCommand cmd = toGoogle.get(i).getCommand();
			addNewRequest(e, cmd);
		}

	}
	
	// use for undo
	public void undo(ArrayList<MemoriEvent> previous, ArrayList<MemoriEvent> next){
		Collections.sort(previous,MemoriEvent.internalIdComparator);
		Collections.sort(next,MemoriEvent.internalIdComparator);
		MemoriEvent toUpdate = null;
		//undo was an undoing an update if it was a complete nothing will sync
		if(previous.size() == next.size()){
			for(int i=0;i<previous.size(); i++){
				if(!previous.get(i).equals(next.get(i))){
					toUpdate = previous.get(i);
					toUpdate.setExternalCalId(next.get(i).getExternalCalId());
					MemoriCommand updateCmd = new MemoriCommand(MemoriCommandType.UPDATE);
					thingsToSync.add(new SyncObject(updateCmd,toUpdate));
					processQueue();
					break;
				}
			}
			
		}
		//if undo was undoing an add
		else  if(previous.size() < next.size()){
			for(int i =0; i <  next.size(); i ++){
				MemoriEvent current = next.get(i);
				if(!previous.contains(current)){
					MemoriCommand deleteCmd = new MemoriCommand(MemoriCommandType.DELETE);
					thingsToSync.add(new SyncObject(deleteCmd,current));
					processQueue();
				}
			}
		}
		//if undo was undoing an delete
		else{
			for(int i =0; i <  previous.size(); i ++){
				MemoriEvent current = previous.get(i);
				if(!next.contains(current)){
					MemoriCommand addCmd = new MemoriCommand(MemoriCommandType.ADD);
					thingsToSync.add(new SyncObject(addCmd,current));
				}
			}
			processQueue();
		}
	}

	private void solveDifferences(MemoriEvent currentLocal, MemoriEvent currentRemote, ArrayList<SyncObject> toGoogle) {
		if(currentRemote.getUpdate().after(currentLocal.getUpdate())){
			currentLocal.replace(currentRemote);
		}else{
			MemoriCommand updateCmd = new MemoriCommand(MemoriCommandType.UPDATE);
			SyncObject toUpdate = new SyncObject(updateCmd, currentLocal);
			toGoogle.add(toUpdate);
		}
	}
	
	public void addNewRequest(MemoriEvent memoriEvent, MemoriCommand cmd) {
		SyncObject newEntry = new SyncObject(cmd, memoriEvent);
		thingsToSync.offer(newEntry);
		isConnected = true;
		if(crud != null){
			processQueue();
		}
	}

	private void processQueue() {
		while (isConnected) {
			SyncObject headOfQueue = thingsToSync.peek();
			if (headOfQueue != null) {
				isConnected = crud.executeCmd(headOfQueue.getEvent(), headOfQueue.getCommand());
				if (isConnected) {
					thingsToSync.poll();
				}
			} else {
				break;
			}
		}
		SyncObjectQueue wrapper = new SyncObjectQueue();
		wrapper.theQueue = thingsToSync;
		st.saveQueue(wrapper);
	}

	public MemoriEvent retrieveRemote(MemoriEvent local) {
		MemoriEvent remote = crud.retrieveRemote(local);
		if (remote != null) {
			return remote;
		} else {
			isConnected = false;
			return null;
		}
	}

	public ArrayList<MemoriEvent> retrieveAll() {
		try {
			return crud.retrieveAllEvents();
		} catch (UnknownHostException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

	}

	public boolean IsConnected() {
		return isConnected;
	}

	public String pullEvents(MemoriCalendar localCopy) {
		localCopy.sortBy(MemoriEvent.externalIdComparator);
		ArrayList<MemoriEvent> localEvents = localCopy.getEvents();
		ArrayList<MemoriEvent> toBeAdded = new ArrayList<MemoriEvent>();
		for (MemoriEvent e : remoteCopy) {
			// not found
			if (Collections.binarySearch(localEvents, e, MemoriEvent.externalIdComparator) < 0) {
				toBeAdded.add(e);
			}
		}
		for (MemoriEvent e : toBeAdded) {
			localCopy.addRemote(e);
		}
		return String.format(PULL_MESSAGE, toBeAdded.size());
	}

	public String pushEvents(MemoriCalendar localCopy) {
		ArrayList<MemoriEvent> localEvents = localCopy.getEvents();
		ArrayList<MemoriEvent> toBePushed = new ArrayList<MemoriEvent>();
		MemoriCommand addCommand = new MemoriCommand(MemoriCommandType.ADD);
		for (MemoriEvent e : localEvents) {
			if (e.getExternalCalId() == null) {
				toBePushed.add(e);
			}
		}
		for (MemoriEvent e : toBePushed) {
			addNewRequest(e, addCommand);
			doNotDelete.add(e);
		}
		if (thingsToSync.isEmpty())
			return String.format(PUSH_MESSAGE, toBePushed.size());
		else
			return PUSH_ERROR;
	}

}