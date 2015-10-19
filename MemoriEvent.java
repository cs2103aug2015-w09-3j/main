package memori;

import java.util.Comparator;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemoriEvent {

	private static final int NAMECUTOFF = 20;
	public static final int INTERNAL_ID_WILDCARD = -1;
	private static final String[] ATTRIBUTESNAMES = { "name", "start", "end", "internalId", "description",
			"externalCalId", };
	private static final String HEADER_READ = "Name of Event:%1$s\nStart:%2$s\nEnd:%3$s\n"
			+ "Description:%4$s\nLocation:%5$s\n";

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
		return String.format(HEADER_READ, name, start, end, description, location);
	}

	public String display() {
		StringBuilder output = new StringBuilder();
		if (this.name.length() > NAMECUTOFF) {
			output.append(this.name.substring(0, NAMECUTOFF - 1));
		}

		else {
			output.append(this.name);
			while (output.length() < 20)
				output.append(" ");
		}
		output.append(start);
		output.append(" ");
		output.append(end);
		return output.toString();
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
			if (!this.name.equals(other.getName()))
				return false;
			else if (!this.description.equals(other.getDescription()))
				return false;
			else if (!this.start.equals(other.getStart()))
				return false;
			else if (!this.end.equals(other.getEnd()))
				return false;
			else if (!this.location.equals(other.getLocation()))
				return false;
			else
				return true;
		}
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
