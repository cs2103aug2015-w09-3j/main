//@@author A0098038W
package memori.googleSync;

import java.util.Calendar;
import java.util.Date;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import memori.logic.MemoriEvent;

public class EventConverter {
	private static final String UNNAMED = "Unnamed Google Event";
	public static final Date END_OF_TIME = new Date(9999999999999L);
	public static final Date START_OF_TIME = new Date(-9999999999999L);

	public static Event toGoogle(MemoriEvent me) {
		Event event = new Event();
		DateTime end;
		DateTime start;
		if (me.getStart() != null) {
			start = new DateTime(me.getStart());
		} else {
			start = new DateTime(START_OF_TIME);
		}
		if (me.getEnd() != null) {
			end = new DateTime(me.getEnd());
		} else {
			end = new DateTime(END_OF_TIME);

		}
		event.setSummary(me.getName());
		event.setLocation(me.getLocation());
		event.setDescription(me.getDescription());
		event.setStart(new EventDateTime().setDateTime(start));
		event.setEnd(new EventDateTime().setDateTime(end));
		event.setId(me.getExternalCalId());
		return event;
	}

	public static MemoriEvent toMemori(Event ge) {
		if (ge == null)
			return null;
		String name = ge.getSummary();
		if (name == null) {
			name = UNNAMED;
		}
		String description = ge.getDescription();
		DateTime startDT = ge.getStart().getDateTime();
		DateTime endDT = ge.getEnd().getDateTime();
		if (startDT == null) {
			startDT = ge.getStart().getDate();
		}
		if (endDT == null) {
			endDT = ge.getEnd().getDate();
		}
		Date start = new Date(startDT.getValue());
		Date end = new Date(endDT.getValue());
		Calendar cal = Calendar.getInstance();
		cal.setTime(START_OF_TIME);
		int startOfTimeYear = cal.get(Calendar.YEAR);
		int startOfTimeMonth = cal.get(Calendar.MONTH);
		int startOfTimeDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(END_OF_TIME);
		int endOfTimeYear = cal.get(Calendar.YEAR);
		int endOfTimeMonth = cal.get(Calendar.MONTH);
		int endOfTimeDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(start);
		int startYear = cal.get(Calendar.YEAR);
		int startMonth = cal.get(Calendar.MONTH);
		int startDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(end);
		int endYear = cal.get(Calendar.YEAR);
		int endMonth = cal.get(Calendar.MONTH);
		int endDay = cal.get(Calendar.DAY_OF_MONTH);
		if (startYear == startOfTimeYear && startMonth == startOfTimeMonth && startDay == startOfTimeDay) {
			start = null;
		}
		if (endYear == endOfTimeYear && endMonth == endOfTimeMonth && endDay == endOfTimeDay) {
			end = null;
		}
		String location = ge.getLocation();
		int internalId = MemoriEvent.INTERNAL_ID_WILDCARD;
		String externalId = ge.getId();
		if(description == null){
			description = "";
		}
		if(location == null){
			location = "";
		}
		MemoriEvent me = new MemoriEvent(name, start, end, internalId, externalId, description, location);
		me.setExternalCalId(ge.getId());
		return me;

	}
}
