package memori;

import java.io.IOException;

public class GoogleSync {


	public static void main(String[] args) {
		// Build a new authorized API client service.
		// Note: Do not confuse this class with the
		// com.google.api.services.calendar.model.Calendar class.
		try {
			com.google.api.services.calendar.Calendar service = GCalConnect.getCalendarService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}