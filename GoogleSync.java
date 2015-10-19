package memori;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

public class GoogleSync {

	private com.google.api.services.calendar.Calendar googleCalendar;
	private GoogleCRUD crud;
	public ArrayList<MemoriEvent> remoteCopy;
	private boolean isConnected = false;

	public GoogleSync() {

		ErrorSuppressor.supress();
		googleCalendar = GCalConnect.getCalendarService();
		ErrorSuppressor.unsupress();
		if (googleCalendar != null) {
			crud = new GoogleCRUD(googleCalendar);

			try {
				remoteCopy = crud.retrieveAllEvents();
				isConnected = true;
				System.out.println("Connected to google Calendar");
			} catch (UnknownHostException e) {
				isConnected = false;
				System.out.println("Not connected to google Calendar");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public String executeCommand(MemoriEvent memoriEvent, MemoriCommand cmd) {
		if (isConnected) {
			isConnected = crud.executeCmd(memoriEvent, cmd);
		}
		return Boolean.toString(isConnected);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public boolean IsConnected() {
		return isConnected;
	}

	public void pullEvents(MemoriCalendar localCopy) {
		localCopy.sort(MemoriEvent.ExternalIdComparator);
		ArrayList<MemoriEvent> localEvents = localCopy.getEvents();
		ArrayList<MemoriEvent> toBeAdded = new ArrayList<MemoriEvent>();
		for (MemoriEvent e : remoteCopy) {
			if (Collections.binarySearch(localEvents, e, MemoriEvent.ExternalIdComparator) < 0) {
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