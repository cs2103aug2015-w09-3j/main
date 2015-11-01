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
	private static final String PUSH_ERROR = "Unable to sync local eventsto Google Calendar right now\n";
	private static final String PULL_ERROR = "Unable to pull your events from google\n";
	private static final String INVALID_PREFERENCE = "Invalid entry. Please enter 1 or 2\n";
	private static final String NOT_FOUND = "Details:%1$sThis event is no longer in your Google Calendar\n"
			+ "Enter 1 to delete from Memori\n" + "Enter 2 to add to Google\nCommand:";
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
	}

	public void SetUp(MemoriUI ui, MemoriCalendar calendar) {
		if (googleCalendar != null) {
			crud = new GoogleCRUD(googleCalendar);
			try {
				remoteCopy = crud.retrieveAllEvents();
				isConnected = true;
				ui.displayToUser(pullEvents(calendar));
				ui.displayToUser(pushEvents(calendar));
				checkForConflicts(calendar, ui);
			} catch (UnknownHostException e) {
				isConnected = false;
				ui.displayToUser(PULL_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void checkForConflicts(MemoriCalendar calendar, MemoriUI ui) {
		ArrayList<MemoriEvent> localEvents = calendar.getEvents();
		ArrayList<SyncObject> toGoogle = new ArrayList<SyncObject>();
		ArrayList<Integer> toDelete = new ArrayList<Integer>();
		Collections.sort(remoteCopy, MemoriEvent.externalIdComparator);

		for (int i = 0; i < localEvents.size(); i++) {
			MemoriEvent currentLocal = localEvents.get(i);
			int result = Collections.binarySearch(remoteCopy, currentLocal, MemoriEvent.externalIdComparator);
			// external id equals
			if (result >= 0) {
				MemoriEvent currentRemote = remoteCopy.get(result);
				if (!currentLocal.equals(currentRemote)) {
					solveDifferences(ui, toGoogle, currentLocal, currentRemote);
				}
			} else {
				solveDelete(ui, localEvents, toGoogle, toDelete, i, currentLocal);
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
			executeCommand(e, cmd);
		}

	}

	private void solveDifferences(MemoriUI ui, ArrayList<SyncObject> toGoogle, MemoriEvent currentLocal,
			MemoriEvent currentRemote) {
		int preference = retrievePreference(ui, String.format(NOT_FOUND, currentLocal.read()));
		if (preference == 1) {
			currentLocal.replace(currentRemote);
		} else if (preference == 2) {
			SyncObject toAdd = new SyncObject(new MemoriCommand(MemoriCommandType.UPDATE), currentLocal);
			toGoogle.add(toAdd);
		}
	}

	private void solveDelete(MemoriUI ui, ArrayList<MemoriEvent> localEvents, ArrayList<SyncObject> toGoogle,
			ArrayList<Integer> toDelete, int i, MemoriEvent currentLocal) {
		int preference = retrievePreference(ui, String.format(NOT_FOUND, currentLocal.read()));
		if (preference == 1) {
			toDelete.add(i);
		} else if (preference == 2) {
			SyncObject toAdd = new SyncObject(new MemoriCommand(MemoriCommandType.ADD), currentLocal);
			toGoogle.add(toAdd);
		}
	}

	private int retrievePreference(MemoriUI ui, String toShow) {
		int preference = 0;
		ui.displayToUser(toShow);
		while (true) {
			String preferenceStr = ui.takeInput().trim();
			preference = Integer.parseInt(preferenceStr);
			try {
				if (preference == 1 || preference == 2) {
					return preference;
				} else {
					ui.displayToUser(INVALID_PREFERENCE);
					ui.displayToUser(toShow);
				}
			} catch (NumberFormatException e) {
				ui.displayToUser(INVALID_PREFERENCE);
				ui.displayToUser(toShow);
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
			executeCommand(e, addCommand);
		}
		if (thingsToSync.isEmpty())
			return String.format(PUSH_MESSAGE, toBePushed.size());
		else
			return PUSH_ERROR;
	}

}