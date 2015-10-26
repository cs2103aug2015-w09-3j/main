package memori;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class GoogleSync {

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
		while(isConnected){
			SyncObject headOfQueue = thingsToSync.peek();
			if(headOfQueue != null){
				isConnected = crud.executeCmd(headOfQueue.getEvent(), headOfQueue.getCommand());
				if(isConnected){
					thingsToSync.poll();
				}
			}
			else{
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
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public boolean IsConnected() {
		return isConnected;
	}

	public void pullEvents(MemoriCalendar localCopy) {
		localCopy.sortBy(MemoriEvent.externalIdComparator);
		ArrayList<MemoriEvent> localEvents = localCopy.getEvents();
		ArrayList<MemoriEvent> toBeAdded = new ArrayList<MemoriEvent>();
		for (MemoriEvent e : remoteCopy) {
			if (Collections.binarySearch(localEvents, e, MemoriEvent.externalIdComparator) < 0) {
				toBeAdded.add(e);
			}
		}
		for (MemoriEvent e : toBeAdded) {
			localCopy.addRemote(e);
		}
		System.out.println("Number of events Added =" + toBeAdded.size());
	}

	public static void main(String[] args) {
		GoogleSync gs = new GoogleSync();
		for (MemoriEvent e : gs.remoteCopy) {
			System.out.println(e.display());
		}

	}

}