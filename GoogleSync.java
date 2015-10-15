package memori;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

public class GoogleSync {

	private com.google.api.services.calendar.Calendar googleCalendar;
	private GoogleCRUD crud;
	private List<Event> eventList;
	private boolean isConnected = false;

	public GoogleSync() {

		ErrorSuppressor.supress();
		googleCalendar = GCalConnect.getCalendarService();
		ErrorSuppressor.unsupress();
		crud = new GoogleCRUD(googleCalendar);

		try {
			eventList = crud.retrieveAllEvents();
			isConnected = true;
		} catch (UnknownHostException e) {
			isConnected = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connected to google Calendar");

	}

	public String executeCommand(MemoriEvent memoriEvent, int mode) {
		if (isConnected) {
			isConnected = crud.executeCmd(memoriEvent, mode);
		}
		return Boolean.toString(isConnected);
	}
	
	public MemoriEvent retrieveRemote(MemoriEvent local){
		MemoriEvent remote = crud.retrieveRemote(local);
		if( remote != null){
			return remote;
		}
		else{
			isConnected = false;
			return null;
		}
	}

	public boolean IsConnected() {
		return isConnected;
	}

	public static void main(String[] args) {
		GoogleSync gs = new GoogleSync();
		Date start = null;
		Date end = null;
		String[] arg = { "name", "description", "location" };
		MemoriEvent me = new MemoriEvent(arg[0], start, end, 5,"google", arg[1], arg[2]);
		//me.setExternalCalId("blank");
		/*Scanner sc = new Scanner(System.in);
		sc.next();
		System.out.println(gs.executeCommand(me, 0));
		System.out.println(me.getExternalCalId());
		me.update("testupdate", null, null, null, null);
		//System.out.println(gs.executeCommand(me, 1));
		sc.next();
		System.out.println(gs.executeCommand(me, 2));
		*/
	}

}