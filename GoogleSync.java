package memori;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class GoogleSync {
	
	/** Store print Stream */
	private static final PrintStream originalErr = System.err;
	
	public static void supressError(){

		System.setErr(new PrintStream(new OutputStream() {
		    public void write(int b) {
		    }
		}));
	}


	public static void main(String[] args) {
		// Build a new authorized API client service.
		// Note: Do not confuse this class with the
		// com.google.api.services.calendar.model.Calendar class.
		supressError();
		try {
			com.google.api.services.calendar.Calendar service = GCalConnect.getCalendarService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setErr(originalErr);
		String[] strArr = {"hi"};
		System.out.println(strArr[1]);
	}

}