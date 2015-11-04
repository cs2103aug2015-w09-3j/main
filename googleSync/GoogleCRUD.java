//@@author A0098038W
package memori.googleSync;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import memori.logic.MemoriEvent;
import memori.parsers.MemoriCommand;

public class GoogleCRUD {
	private static final int ADD = 0;
	private static final int UPDATE = 1;
	private static final int RETRIEVE = 2;
	private static final int DELETE = 3;

	private static final String CALENDAR_ID = "primary";
	private static final int MAX_RESULTS = 100000;

	private com.google.api.services.calendar.Calendar googleCalendar;

	public GoogleCRUD(com.google.api.services.calendar.Calendar googleCalendar) {
		this.googleCalendar = googleCalendar;
	}

	public boolean executeCmd(MemoriEvent me, MemoriCommand cmd) {
		switch (cmd.getType()) {
		case ADD:
			return addEvent(me);
		case UPDATE:
			return updateEvent(me);
		case DELETE:
			return deleteEvent(me);
		default:
			return false;
		}
	}

	public MemoriEvent retrieveRemote(MemoriEvent me) {
		try {
			Event event = new Event();
			event.setId(me.getExternalCalId());
			event = executeEvent(event, RETRIEVE);
			if (event.getStart() == null)
				return null;
			return EventConverter.toMemori(event);
		} catch (UnknownHostException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public ArrayList<MemoriEvent> retrieveAllEvents() throws IOException, UnknownHostException {
		Events events = googleCalendar.events().list(CALENDAR_ID).setMaxResults(MAX_RESULTS).execute();
		List<Event> items = events.getItems();
		ArrayList<MemoriEvent> remoteCopy =  new ArrayList<MemoriEvent>();
		for(Event e: items){
			remoteCopy.add(EventConverter.toMemori(e));
		}
		return remoteCopy;
	}

	private boolean addEvent(MemoriEvent memoriEvent) {
		Event event = EventConverter.toGoogle(memoriEvent);

		try {
			event = executeEvent(event, ADD);
			memoriEvent.setExternalCalId(event.getId());
			System.out.println(event.getHtmlLink());
		} catch (UnknownHostException e) {
			System.out.println("Host execute fail");
			return false;
		} catch (IOException e) {
			System.out.println("IO execute fail");
			return false;
		}
		return true;
	}

	private boolean updateEvent(MemoriEvent memoriEvent) {

		Event event = EventConverter.toGoogle(memoriEvent);
		String GcalEventId = memoriEvent.getExternalCalId();

		try {
			if (GcalEventId != null) {
				event.setId(GcalEventId);
				event = executeEvent(event, UPDATE);
			} else {
				event = executeEvent(event, ADD);
				memoriEvent.setExternalCalId(event.getId());
			}
			System.out.println(event.getHtmlLink());
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	private boolean deleteEvent(MemoriEvent memoriEvent) {
		Event event = new Event();
		event.setId(memoriEvent.getExternalCalId());
		try {
			event = executeEvent(event, DELETE);
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private Event executeEvent(Event event, int mode) throws IOException, UnknownHostException {
		switch (mode) {
		case ADD:
			return googleCalendar.events().insert(CALENDAR_ID, event).execute();
		case UPDATE:
			return googleCalendar.events().update(CALENDAR_ID, event.getId(), event).execute();
		case RETRIEVE:
			return googleCalendar.events().get(CALENDAR_ID, event.getId()).execute();
		case DELETE:
			googleCalendar.events().delete(CALENDAR_ID, event.getId()).execute();
		default:
			return null;
		}
	}
}
