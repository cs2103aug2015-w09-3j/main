package memori;

import java.util.Date;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class EventConverter {
	
	private static final String NAME = "NAME";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String LOCATION = "LOCATION";
	private static final String EID = "EID";
	private static final Date START = new Date(0);
	private static final Date END = new Date(0);
	private static final int INTERNAL_ID = 0;

	
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
		event.setId(me.getExternalCalId());
		return event;
	}
	
	public static MemoriEvent toMemori(Event ge){
		if(ge == null)
			return null;
		String name = ge.getSummary();
		String description = ge.getDescription();
		DateTime startDT = ge.getStart().getDateTime();
		DateTime endDT = ge.getEnd().getDateTime();
		if(startDT == null){
			startDT = ge.getStart().getDate();
		}
		if(endDT == null){
			endDT = ge.getEnd().getDate();
		}
		Date start = new Date(startDT.getValue());
		Date end = new Date(endDT.getValue());
		if(start.equals(START_OF_TIME)){
			start = null;
		}
		if(end.equals(END_OF_TIME)){
			end = null;
		}
		String location = ge.getLocation();
		int internalId = MemoriEvent.INTERNAL_ID_WILDCARD;
		String externalId = ge.getId();
		MemoriEvent me = new MemoriEvent(name,start,end,internalId, externalId,description, location);
		me.setExternalCalId(ge.getId());
		return me;
		
	}
	
	public static void main(String[] args){
		MemoriEvent me = new MemoriEvent(NAME, START, END, INTERNAL_ID, EID, DESCRIPTION, LOCATION);
		Event ge = new Event();
		ge.setSummary(NAME);
		DateTime temp = new DateTime(START);
		ge.setStart(new EventDateTime().setDateTime(temp));
		temp = new DateTime(END);
		ge.setEnd(new EventDateTime().setDateTime(temp));
		ge.setId(EID);
		ge.setDescription(DESCRIPTION);
		ge.setLocation(LOCATION);
		MemoriEvent converted = EventConverter.toMemori(ge);
		System.out.println(me.equals(converted));
	}

}
