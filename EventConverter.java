package memori;

import java.util.Date;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class EventConverter {
	
	public static final Date END_OF_TIME = new Date(9999999999999L);
	public static final Date START_OF_TIME = new Date(-9999999999999L);
	
	public static Event toGoogle(MemoriEvent me){
		Event event = new Event();
		DateTime end ;
		DateTime start;
		if(me.getStart() != null){
			start = new DateTime(me.getStart());
		}
		else{
			start = new DateTime(START_OF_TIME);
		}
		if(me.getEnd() != null){
			end = new DateTime(me.getEnd());
		}
		else{
			end = new DateTime(END_OF_TIME);
		
		}
		event.setSummary(me.getName());
		event.setLocation(me.getLocation());
		event.setDescription(me.getDescription());
		event.setStart(new EventDateTime().setDateTime(start));
		event.setEnd(new EventDateTime().setDateTime(end));

		return event;
	}
	
	public static MemoriEvent toMemori(Event ge){
		if(ge == null)
			return null;
		String name = ge.getSummary();
		String description = ge.getDescription();
		Date start = new Date(ge.getStart().getDateTime().getValue());
		Date end = new Date(ge.getEnd().getDateTime().getValue());
		if(start.equals(START_OF_TIME)){
			start = null;
		}
		if(end.equals(END_OF_TIME)){
			end = null;
		}
		String location = ge.getLocation();
		int internalId = MemoriEvent.INTERNAL_ID_WILDCARD;
		
		return new MemoriEvent(name,start,end,internalId, description,location, location);
		
	}

}
