package memori;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class GoogleSync {

	private static final String PULL_MESSAGE = "Number of events added =%1$s\n";
	private static final String PUSH_MESSAGE = "Number of events synced =%1$s\n";
	private static final String PUSH_ERROR ="Unable to sync local eventsto Google Calendar right now\n";
	
	private com.google.api.services.calendar.Calendar googleCalendar;
	private GoogleCRUD crud;
	private ArrayList<MemoriEvent> remoteCopy;
	private boolean isConnected;
	private Queue<SyncObject> thingsToSync;

	public GoogleSync() {

		ErrorSuppressor.supress();
		googleCalendar = GCalConnect.getCalendarService();
		ErrorSuppressor.unsupress();
		thingsToSync = new LinkedList<SyncObject>();
		if (googleCalendar != null) {
			crud = new GoogleCRUD(googleCalendar);

			try {
				remoteCopy = crud.retrieveAllEvents();
				isConnected = true;
			} catch (UnknownHostException e) {
				isConnected = false;
				System.out.println("Not connected to google Calendar");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void executeCommand(MemoriEvent memoriEvent, MemoriCommand cmd) {
		SyncObject newEntry = new SyncObject(cmd, memoriEvent);
		thingsToSync.offer(newEntry);
		isConnected = true;
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
		for(MemoriEvent e : remoteCopy) {
			if (Collections.binarySearch(localEvents, e, MemoriEvent.externalIdComparator) < 0) {
				toBeAdded.add(e);
			}
		}
		for (MemoriEvent e : toBeAdded) {
			localCopy.addRemote(e);
		}
		return String.format(PULL_MESSAGE, toBeAdded.size());
	}

	public String pushEvents(MemoriCalendar localCopy){
		ArrayList<MemoriEvent> localEvents = localCopy.getEvents();
		ArrayList<MemoriEvent> toBePushed = new ArrayList<MemoriEvent>();
		MemoriCommand addCommand = new MemoriCommand(MemoriCommandType.ADD,null);
		for(MemoriEvent e : localEvents) {
			if(e.getExternalCalId() == null){
				toBePushed.add(e);
			}
		}
		for(MemoriEvent e: toBePushed){
			executeCommand(e,addCommand);
		}
		if(thingsToSync.isEmpty())
			return String.format(PUSH_MESSAGE, toBePushed.size());
		else
			return PUSH_ERROR;
	}

}