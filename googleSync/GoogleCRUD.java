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
	/** Modes for Google Calendar operations */
	private static final int ADD = 0;
	private static final int UPDATE = 1;
	private static final int RETRIEVE = 2;
	private static final int DELETE = 3;

	/** Modes for Google Calendar operations */
	private static final String CALENDAR_ID = "primary";
	private static final int MAX_RESULTS = 100000;

	/** Service Object used to authenticate the program */
	private com.google.api.services.calendar.Calendar googleCalendar;

	/** Constructor */
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
			assert false :cmd.getType();
			return false;
		}
	}

	public MemoriEvent retrieveRemote(MemoriEvent me) {
		try {
			Event event = new Event();
			event.setId(me.getExternalCalId());
			event = executeEvent(event, RETRIEVE);
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
		ArrayList<MemoriEvent> remoteCopy = new ArrayList<MemoriEvent>();
		for (Event e : items) {
			remoteCopy.add(EventConverter.toMemori(e));
		}
		return remoteCopy;
	}

	/**
	 * Converts a MemoriEvent object to a Google Calendar Event object and adds
	 * it to Google Calendar
	 * 
	 * @param memoriEvent
	 *            the local event that needs to be sync
	 * @return completion status of the operation
	 */
	private boolean addEvent(MemoriEvent memoriEvent) {
		Event event = EventConverter.toGoogle(memoriEvent);

		try {
			event = executeEvent(event, ADD);
			memoriEvent.setExternalCalId(event.getId());
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Converts a MemoriEvent object to a Google Calendar Event object and
	 * update it on Google Calendar
	 * 
	 * @param memoriEvent
	 *            the local event that needs to be sync
	 * @return completion status of the operation
	 */
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
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Deletes an MemoriEvent on Google
	 * 
	 * @param memoriEvent
	 *            the local event that needs to be sync
	 * @return completion status of the operation
	 */
	private boolean deleteEvent(MemoriEvent memoriEvent) {
		Event event = new Event();
		String externalId = memoriEvent.getExternalCalId();
		if (externalId != null) {
			event.setId(externalId);
		} else {
			return true;
		}
		try {
			event = executeEvent(event, DELETE);
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return true;
		}
		return true;
	}

	/**
	 * 
	 * @param event
	 *            a Google Event Object to be added/updated/Delete
	 * @param mode
	 *            Update/Retrieve/Add/Delete
	 * @return an update Google Event object after performing the operation
	 * @throws IOException
	 *             when incorrect Google Calendar Id or Start and End are
	 *             entered
	 * @throws UnknownHostException
	 *             when there is no connection to Google Calendar
	 */
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
			return null;
		default:
			assert false :mode;
			return null;
		}
	}
}
