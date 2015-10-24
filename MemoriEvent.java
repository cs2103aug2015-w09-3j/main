package memori;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemoriEvent {
	public static final int INTERNAL_ID_WILDCARD = -1;
	public static final int NAME_CUT_OFF = 30;
	public static final String DATE_FORMAT = "dd MMM yy HH:mm E";
	
	private static final String HEADER_READ = "Name of Event:%1$s\nStart:%2$s\nEnd:%3$s\n"
			+ "Description:%4$s\nLocation:%5$s\n";
	private static final String DISPLAY_FORMAT = "%1$s  %2$s  %3$s";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

	private String name;
	private String description;
	private String location;
	private String externalCalId;
	private Date start;
	private Date end;
	private int internalId;

	public MemoriEvent(String name, Date start, Date end, int internalId, String externalCalId, String description,
			String location) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.internalId = internalId;
		this.externalCalId = externalCalId;
		this.description = description;
		this.location = location;
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

	public void setExternalCalId(String id) {
		this.externalCalId = id;
	}

	public void setInternalCalId(int id) {
		this.internalId = id;
	}

	public void update(String name, Date start, Date end, String description, String location) {
		if (!name.isEmpty())
			this.name = name;
		if (start != null)
			this.start = start;
		if (end != null)
			this.end = end;
		if (!name.isEmpty())
			this.description = description;
		if (!location.isEmpty())
			this.location = location;
	}

	public String read() {
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
		return String.format(HEADER_READ, name, startString, endString, description, location);
	}

	private String padRight(String s, int n) {
		 return String.format("%1$-" + n + "s", s);  
	}

	public String display() {
		String startString;
		String endString;

		if (start == null) {
			startString = padRight("", DATE_FORMAT.length() +2 );
		} else {
			startString = DATE_FORMATTER.format(start);
		}
		if (end == null) {
			endString = "";
		} else {
			endString = DATE_FORMATTER.format(end);
		}

		String name;
		if (this.name.length() > NAME_CUT_OFF) {
			name = this.name.substring(0, NAME_CUT_OFF);
		} else {
			name = padRight(this.name, NAME_CUT_OFF);
		}
		return String.format(DISPLAY_FORMAT, name, startString, endString);
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
			if (!this.name.equals(other.getName())){
				System.out.println("name false");
				return false;
			}	
			else if (!Compare(this.description, other.getDescription())){
				System.out.println(this.description);
				System.out.println(other.description);
				return false;
			}
			else if (!Compare(this.start, other.getStart())){
				System.out.println(this.start);
				System.out.println(other.start);
				System.out.println("start false");
				return false;
			}
			else if (!Compare(this.end,other.getEnd())){
				System.out.println("end false");
				return false;
			}
			else if (!Compare(this.location,other.getLocation()))
				return false;
			else
				return true;
		}
	}
	
	private static <T> boolean Compare(T item1, T item2){
		if(item1 ==null && item2 == null)
			return true;
		else if(item1 == null && item2 != null || item2 == null && item1 !=null)
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
			String eID1 = me1.getExternalCalId();
			String eID2 = me2.getExternalCalId();

			return eID1.compareTo(eID2);
		}
	};

	public static Comparator<MemoriEvent> externalIdComparator = new Comparator<MemoriEvent>() {
		public int compare(MemoriEvent me1, MemoriEvent me2) {
			String eID1 = me1.getExternalCalId();
			String eID2 = me2.getExternalCalId();

			return eID1.compareTo(eID2);
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

}
