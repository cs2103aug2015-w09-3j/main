//@@author A0113645L
package memori.logic;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import memori.parsers.FieldsParser;

public class MemoriEvent {
	public static final int INTERNAL_ID_WILDCARD = -1;
	public static final int NAME_CUT_OFF = 20;
	public static final String DATE_FORMAT = "dd/MM/yy HH:mm E";

	private static final String DISPLAY_FORMAT = "%1$s  %2$s  %3$s  %4$s";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	//Acknowledgement fields
	private static final String NAME_FIELD = "Name: %1$s\n";
	private static final String START_FIELD = "Start: %1$s\n";
	private static final String END_FIELD = "End: %1$s\n";
	private static final String DESCRIPTION_FIELD = "Description: %1$s\n";
	private static final String LOCATION_FIELD = "Location: %1$s\n";
	private static final String COMPLETE_FIELD = "Completed: %1$s\n";
	
	private String name;
	private String description;
	private String location;
	private String externalCalId;
	private Date start;
	private Date end;
	private Date updateTime;
	private int internalId;
	private boolean complete;

	public MemoriEvent(String name, Date start, Date end, int internalId, String externalCalId, String description,
			String location) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.internalId = internalId;
		this.externalCalId = externalCalId;
		this.description = description;
		this.location = location;
		this.complete = false;
		this.updateTime = new Date(System.currentTimeMillis());
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getExternalCalId() {
		return externalCalId;
	}

	public int getInternalId() {
		return internalId;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public String getLocation() {
		return location;
	}
	
	public Date getUpdate() {
		return updateTime;
	}

	public void setExternalCalId(String id) {
		this.externalCalId = id;
	}

	public void setInternalCalId(int id) {
		this.internalId = id;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	
	public void setUpdate(Date newUpdateTime) {
		this.updateTime = newUpdateTime;
	}

	public String update(String name, Date start, Date end, String description, String location,
			Boolean[] filledFields) {
		String updateText = "";
		if (filledFields[FieldsParser.NAME_INDEX]) {
			this.name = name;
			updateText += String.format(NAME_FIELD, name);
		}
		if (filledFields[FieldsParser.START_INDEX]) {
			this.start = start;
			updateText += String.format(START_FIELD, start.toString());
		}
		if (filledFields[FieldsParser.END_INDEX]) {
			this.end = end;
			updateText += String.format(END_FIELD, end.toString());
		}
		if (filledFields[FieldsParser.DESCRIPTION_INDEX]) {
			this.description = description;
			updateText += String.format(DESCRIPTION_FIELD, description);
		}
		if (filledFields[FieldsParser.LOCATION_INDEX]) {
			this.location = location;
			updateText += String.format(LOCATION_FIELD, location);
		}
		this.updateTime = new Date(System.currentTimeMillis());
		return updateText;
	}
	
	public void replace(MemoriEvent other) {
		this.name = other.name;
		this.description = other.description;
		this.start = other.start;
		this.end = other.end;
		this.location = other.location;
		this.updateTime = other.updateTime;
	}


	public String read() {
		String output = "";
		String startString;
		String endString;
		if (start == null) {
			startString = "";
		} else {
			startString = DATE_FORMATTER.format(start);
		}
		if (end == null) {
			endString = "";
		} else {
			endString = DATE_FORMATTER.format(end);
		}
		output += String.format(NAME_FIELD, name);
		output += String.format(START_FIELD, startString);
		output += String.format(END_FIELD, endString);
		output += String.format(DESCRIPTION_FIELD, description);
		output += String.format(LOCATION_FIELD, location);
		output += String.format(COMPLETE_FIELD, complete);
		return output;
	}

	private String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}
	
	public String display() {
		String startString;
		String endString;
		String completeOutputStr;

		if (start == null) {
			startString = padRight("", DATE_FORMAT.length() + 2);
		} else {
			startString = DATE_FORMATTER.format(start);
		}
		if (end == null) {
			endString = padRight("", DATE_FORMAT.length() + 2);
		} else {
			endString = DATE_FORMATTER.format(end);
		}

		String name;
		if (this.name.length() > NAME_CUT_OFF) {
			name = this.name.substring(0, NAME_CUT_OFF);
		} else {
			name = padRight(this.name, NAME_CUT_OFF);
		}
	
		String completeStr = String.valueOf(this.complete);
		if(this.complete == true){
			completeOutputStr = String.format("%5s", "Y");
		}else{
			completeOutputStr = String.format("%5s", "N");
		}
		return String.format(DISPLAY_FORMAT, name, startString, endString, completeOutputStr);
	}

	public String toJson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(this);
	}

	public static MemoriEvent fromJSON(String json) {
		MemoriEvent event = new Gson().fromJson(json, MemoriEvent.class);
		return event;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (!(obj instanceof MemoriEvent)) {
			return false;
		} else {
			MemoriEvent other = (MemoriEvent) obj;
			if (!this.name.equals(other.getName())) {
				return false;
			} else if (!Compare(this.description, other.getDescription())) {
				return false;
			} else if (!Compare(this.start, other.getStart())) {
				return false;
			} else if (!Compare(this.end, other.getEnd())) {
				return false;
			} else if (!Compare(this.location, other.getLocation()))
				return false;
			else
				return true;
		}
	}

	private static <T> boolean Compare(T item1, T item2) {
		if (item1 == null && item2 == null)
			return true;
		else if (item1 == null && item2 != null || item2 == null && item1 != null)
			return false;
		else
			return item1.equals(item2);
	}

	public static Comparator<MemoriEvent> internalIdComparator = new Comparator<MemoriEvent>() {
		public int compare(MemoriEvent me1, MemoriEvent me2) {
			int id1 = me1.getInternalId();
			int id2 = me2.getInternalId();

			return id1 - id2;
		}
	};

	public static Comparator<MemoriEvent> nameComparator = new Comparator<MemoriEvent>() {
		public int compare(MemoriEvent me1, MemoriEvent me2) {
			String name1 = me1.getName().toUpperCase();
			String name2 = me2.getName().toUpperCase();

			if(name1 != null && name2 != null){
				return name1.compareTo(name2);
			} else if (name1 != null && name2 == null) {
				return 1;
			} else if (name2 != null && name1 == null) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	public static Comparator<MemoriEvent> externalIdComparator = new Comparator<MemoriEvent>() {
		public int compare(MemoriEvent me1, MemoriEvent me2) {
			String eID1 = me1.getExternalCalId();
			String eID2 = me2.getExternalCalId();

			if(eID1 != null && eID2 != null){
				return eID1.compareTo(eID2);
			} else if (eID1 != null && eID2 == null) {
				return 1;
			} else if (eID2 != null && eID1 == null) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	public static Comparator<MemoriEvent> endDateComparator = new Comparator<MemoriEvent>() {
		public int compare(MemoriEvent me1, MemoriEvent me2) {
			Date date1 = me1.getEnd();
			Date date2 = me2.getEnd();

			if (date1 != null && date2 != null) {
				return date1.compareTo(date2);
			} else if (date1 != null && date2 == null) {
				return 1;
			} else if (date2 != null && date1 == null) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	public static Comparator<MemoriEvent> startDateComparator = new Comparator<MemoriEvent>() {
		public int compare(MemoriEvent me1, MemoriEvent me2) {
			Date date1 = me1.getStart();
			Date date2 = me2.getStart();

			if (date1 != null && date2 != null) {
				return date1.compareTo(date2);
			} else if (date1 != null && date2 == null) {
				return 1;
			} else if (date2 != null && date1 == null) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	public static Comparator<MemoriEvent> descriptionComparator = new Comparator<MemoriEvent>() {
		public int compare(MemoriEvent me1, MemoriEvent me2) {
			String description1 = me1.getDescription();
			String description2 = me2.getDescription();

			if(description1 != null && description2 != null){
				return description1.compareTo(description2);
			} else if (description1 != null && description2 == null) {
				return 1;
			} else if (description2 != null && description1 == null) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	public static Comparator<MemoriEvent> locationComparator = new Comparator<MemoriEvent>() {
		public int compare(MemoriEvent me1, MemoriEvent me2) {
			String location1 = me1.getLocation();
			String location2 = me2.getLocation();

			if(location1 != null && location2 != null){
				return location1.compareTo(location2);
			} else if (location1 != null && location2 == null) {
				return 1;
			} else if (location2 != null && location1 == null) {
				return -1;
			} else {
				return 0;
			}
		}
	};

}
