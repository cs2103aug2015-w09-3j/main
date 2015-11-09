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

public class MemoriSync {
	/** Initial Sync Messages */
	private static final String PULL_MESSAGE = "Number of events added locally =%1$s\n";
	private static final String PUSH_MESSAGE = "Number of events synced to google =%1$s\n";
	private static final String PUSH_ERROR = "Unable to sync local events to Google Calendar right now\n";
	private static final String PULL_ERROR = "Unable to pull your events from google\n";

	/** Service Object used to authenticate the program */
	private com.google.api.services.calendar.Calendar googleCalendar;

	/** Component used to perform CRUD functions on Google */
	private GoogleCRUD crud;

	/**
	 * A copy of all the Google Calendar events converted to memoriEvent objects
	 */
	private ArrayList<MemoriEvent> remoteCopy;

	/** Mark a list of events to not remove from Google during initial sync */
	private ArrayList<MemoriEvent> doNotDelete;

	/** Connection Status */
	private boolean isConnected;

	/** A queue of objects to manage what to sync */
	private Queue<SyncObject> thingsToSync;

	/**
	 * MemoriStorage Component to save the syncing queue if the program is
	 * offline
	 */
	private MemoriStorage st;

	/** Default constructor */
	public MemoriSync() {
		ErrorSuppressor.supress();
		googleCalendar = GCalConnect.getCalendarService();
		ErrorSuppressor.unsupress();
		st = MemoriStorage.getInstance();
		SyncObjectQueue wrapper = st.loadQueue();
		if (wrapper == null) {
			thingsToSync = new LinkedList<SyncObject>();
		} else {
			thingsToSync = wrapper.theQueue;
		}
		Queue<SyncObject> tempQueue = new LinkedList<SyncObject>();
		while (!thingsToSync.isEmpty()) {
			SyncObject current = thingsToSync.poll();
			if (current.getCommand().getType() == MemoriCommandType.DELETE) {
				tempQueue.offer(current);
			}
		}
		doNotDelete = new ArrayList<MemoriEvent>();
		thingsToSync = tempQueue;
	}

	/**
	 * Performs the initial syncing
	 *
	 * @param localCopy
	 *            emoriCalendar Object used in this instance of the program
	 * @return A string that indicates the initial sync status
	 */
	public String initialize(MemoriCalendar calendar) {
		String output = "";
		if (googleCalendar != null) {
			crud = new GoogleCRUD(googleCalendar);
			try {
				processQueue();
				remoteCopy = crud.retrieveAllEvents();
				isConnected = true;
				output += pullEvents(calendar);
				output += pushEvents(calendar);
				checkForConflicts(calendar);
			} catch (UnknownHostException e) {
				isConnected = false;
				output += PUSH_ERROR;
				output += PULL_ERROR;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	/**
	 * Checks Events on Google Calendar and adds the missing ones to Memori
	 *
	 * @param localCopy
	 *            MemoriCalendar Object used in this instance of the program
	 * @return A string that indicates how many events were added to Memori
	 */
	private String pullEvents(MemoriCalendar localCopy) {
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

	/**
	 * Checks for Events in Memori that were not synced up to Google Calendar
	 * and push them up
	 *
	 * @param localCopy
	 *            MemoriCalendar Object used in this instance of the program
	 * @return A string that indicates how many events were added to Google
	 */
	private String pushEvents(MemoriCalendar localCopy) {
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

	/**
	 * Compare existing Events on Memori and Google. If a Google Event differs
	 * from Memori use latest update time to resolve conflict. If a Memori Event
	 * has a Google Event Id but is no longer found on Google, delete that event
	 * from Memori
	 *
	 * @param localCopy
	 *            MemoriCalendar Object used in this instance of the program
	 */
	private void checkForConflicts(MemoriCalendar localCopy) {
		ArrayList<MemoriEvent> localEvents = localCopy.getEvents();
		ArrayList<SyncObject> toGoogle = new ArrayList<SyncObject>();
		ArrayList<Integer> toDelete = new ArrayList<Integer>();
		Collections.sort(remoteCopy, MemoriEvent.externalIdComparator);

		markForDeleteOrUpdate(localEvents, toGoogle, toDelete);
		deleteFromLocal(localEvents, toDelete);
		updateGoogleEvents(toGoogle);

	}

	/**
	 * Marking the Events for Delete or Update
	 *
	 * @param localEvents
	 *            An ArrayList of MemoriEvents found in the Calendar Object
	 * @param toGoogle
	 *            An ArrayList of MemoriEvents that has been marked for update
	 *            to Google
	 * @param toDelete
	 *            An ArrayList of indexes of the MemoriEvents marked for
	 *            deletion
	 */
	private void markForDeleteOrUpdate(ArrayList<MemoriEvent> localEvents, ArrayList<SyncObject> toGoogle,
			ArrayList<Integer> toDelete) {
		for (int i = 0; i < localEvents.size(); i++) {
			MemoriEvent currentLocal = localEvents.get(i);
			int result = Collections.binarySearch(remoteCopy, currentLocal, MemoriEvent.externalIdComparator);
			// event was updated
			if (result >= 0) {
				MemoriEvent currentRemote = remoteCopy.get(result);
				if (!currentLocal.equals(currentRemote)) {
					solveDifferences(currentLocal, currentRemote, toGoogle);
				}
				// event was not found so it was deleted from Gcal
			} else if (!doNotDelete.contains(currentLocal)) {
				toDelete.add(i);
			}
		}
	}

	/**
	 * Solve differences in Attributes based on Update time
	 *
	 * @param currentLocal
	 *            A copy of the event found locally on Memori
	 * @param currentRemote
	 *            A copy of the event found remotely on Google Calendar
	 * @param toGoogle
	 *            An ArrayList of MemoriEvents that has been marked for update
	 *            to Google
	 */
	private void solveDifferences(MemoriEvent currentLocal, MemoriEvent currentRemote, ArrayList<SyncObject> toGoogle) {
		if (currentRemote.getUpdate().after(currentLocal.getUpdate())) {
			currentLocal.replace(currentRemote);
		} else {
			MemoriCommand updateCmd = new MemoriCommand(MemoriCommandType.UPDATE);
			SyncObject toUpdate = new SyncObject(updateCmd, currentLocal);
			toGoogle.add(toUpdate);
		}
	}

	/**
	 * Sends update requests to Google Calendar
	 * 
	 * @param toGoogle
	 *            An ArrayList of MemoriEvents that has been marked for update
	 *            to Google
	 */
	private void updateGoogleEvents(ArrayList<SyncObject> toGoogle) {
		for (int i = 0; i < toGoogle.size(); i++) {
			MemoriEvent e = toGoogle.get(i).getEvent();
			MemoriCommand cmd = toGoogle.get(i).getCommand();
			addNewRequest(e, cmd);
		}
	}

	/**
	 * Delete events that are no longer found on Google Calendar
	 * 
	 * @param localEvents
	 * @param toDelete
	 *            An ArrayList of Indxes to delete from local Events
	 */
	private void deleteFromLocal(ArrayList<MemoriEvent> localEvents, ArrayList<Integer> toDelete) {
		Collections.reverse(toDelete);
		for (int i = 0; i < toDelete.size(); i++) {
			int index = toDelete.get(i);
			localEvents.remove(index);
		}
	}

	/**
	 * Add a request to the things to SyncQueue
	 * 
	 * @param memoriEvent
	 * @param cmd
	 */
	public void addNewRequest(MemoriEvent memoriEvent, MemoriCommand cmd) {
		SyncObject newEntry = new SyncObject(cmd, memoriEvent);
		thingsToSync.offer(newEntry);
		isConnected = true;
		if (crud != null) {
			processQueue();
		}
	}

	/** Processes whatever item is in thingsToSync Queue */
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

	/**
	 * Checks what type of undo is required and performs it on Google Calendar
	 * 
	 */
	public void undo(ArrayList<MemoriEvent> previous, ArrayList<MemoriEvent> next) {
		Collections.sort(previous, MemoriEvent.internalIdComparator);
		Collections.sort(next, MemoriEvent.internalIdComparator);
		// undo was an undoing an update if it was a complete nothing will sync
		if (previous.size() == next.size()) {
			undoUpdate(previous, next);

		}
		// if undo was undoing an add
		else if (previous.size() < next.size()) {
			undoAdd(previous, next);
		}
		// if undo was undoing an delete
		else {
			undoDelete(previous, next);
		}
	}

	/** Undo a delete on Google Calendar */
	private void undoDelete(ArrayList<MemoriEvent> previous, ArrayList<MemoriEvent> next) {
		for (int i = 0; i < previous.size(); i++) {
			MemoriEvent current = previous.get(i);
			if (!next.contains(current)) {
				MemoriCommand addCmd = new MemoriCommand(MemoriCommandType.ADD);
				thingsToSync.add(new SyncObject(addCmd, current));
			}
		}
		processQueue();
	}

	/** Undo a update on Google Calendar */
	private void undoUpdate(ArrayList<MemoriEvent> previous, ArrayList<MemoriEvent> next) {
		MemoriEvent toUpdate;
		for (int i = 0; i < previous.size(); i++) {
			if (!previous.get(i).equals(next.get(i))) {
				toUpdate = previous.get(i);
				toUpdate.setExternalCalId(next.get(i).getExternalCalId());
				MemoriCommand updateCmd = new MemoriCommand(MemoriCommandType.UPDATE);
				thingsToSync.add(new SyncObject(updateCmd, toUpdate));
				processQueue();
				break;
			}
		}
	}

	/** Undo a add on Google Calendar */
	private void undoAdd(ArrayList<MemoriEvent> previous, ArrayList<MemoriEvent> next) {
		for (int i = 0; i < next.size(); i++) {
			MemoriEvent current = next.get(i);
			if (!previous.contains(current)) {
				MemoriCommand deleteCmd = new MemoriCommand(MemoriCommandType.DELETE);
				thingsToSync.add(new SyncObject(deleteCmd, current));
				processQueue();
			}
		}
	}

	/** Retrieve a remote Copy of the MemoriEvent */
	public MemoriEvent retrieveRemote(MemoriEvent local) {
		MemoriEvent remote = crud.retrieveRemote(local);
		if (remote != null) {
			return remote;
		} else {
			isConnected = false;
			return null;
		}
	}

	/**
	 * Retrieve what's on Google Calendar and convert it to Memori Event Format
	 */
	public ArrayList<MemoriEvent> retrieveAll() {
		try {
			return crud.retrieveAllEvents();
		} catch (UnknownHostException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

	}

	/**
	 * @return connection status to Google Calendar
	 */
	public boolean IsConnected() {
		return isConnected;
	}

}