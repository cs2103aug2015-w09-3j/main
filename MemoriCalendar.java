package memori;

import java.util.ArrayList;
import java.util.Comparator;

import edu.emory.mathcs.backport.java.util.Collections;

public class MemoriCalendar {
	private static final String MESSAGE_ADD = "Event Added.\n";
	private static final String MESSAGE_DELETE = "Deleted.\n";
	private static final String MESSAGE_UPDATE = "Updated: \n";
	private static final String MESSAGE_READ = "Reading: \n";
	private static final String LINE_INDEX_DOES_NOT_EXISTS = "Line index does not exists.\n";
	private static final String MESSAGE_EMPTYFILE = "File is Empty.\n";
	private static final String INDEX_HEADER = "No: ";
	private static final String NAME_HEADER = "Name of Event:";
	private static final String START_HEADER = "Start: ";
	private static final String END_HEADER = "End: ";
	private static final String DISPLAY_FORMAT = "%1$s %2$s  %3$s    %4$s\n";
	
	private ArrayList<MemoriEvent> memoriCalendar;
	private int maxId = 0;
	private boolean maxIdSet = false;

	public MemoriCalendar() {
		this.memoriCalendar = new ArrayList<MemoriEvent>();
	}
	
	public ArrayList<MemoriEvent> getEvents() {
		return memoriCalendar;
	}

	public void sort(Comparator<MemoriEvent> comparator) {
		Collections.sort(memoriCalendar, comparator);
	}

	private void findMaxId() {
		for (MemoriEvent e : memoriCalendar) {
			int internalId = e.getInternalId();
			if (internalId > maxId) {
				maxId = internalId;
			}

		}
		maxIdSet = true;
	}

	public String addRemote(MemoriEvent event) {
		if (maxIdSet == false)
			findMaxId();
		maxId++;
		event.setInternalCalId(maxId);
		memoriCalendar.add(event);
		return MESSAGE_ADD;
	}

	public String display() {
		String paddedNameHeader = padRight(NAME_HEADER, MemoriEvent.NAME_CUT_OFF);
		String paddedStartHeader = padRight(START_HEADER, MemoriEvent.DATE_FORMAT.length());
		String output = String.format(DISPLAY_FORMAT, INDEX_HEADER, paddedNameHeader, paddedStartHeader, END_HEADER);
		
		for (int i = 1; i <= memoriCalendar.size() ; i++) {
			String index = padRight(Integer.toString(i), INDEX_HEADER.length());
			MemoriEvent e = memoriCalendar.get(i-1);
			
			if(i != memoriCalendar.size()){
				output += index + " " + e.display() + "\n";
			}
			else{
				output += index + " " + e.display();
			}
		}
		return output;
	}

	private String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public String execute(MemoriCommand command, GoogleSync googleSync) {
		switch (command.getType()) {
		case ADD:
			return add(command,googleSync);
		case UPDATE:
			return update(command,googleSync);
		case DELETE:
			return delete(command,googleSync);
		case READ:
			return read(command);
		default:
			return "invalid";
		}

	}
	
	public String add(MemoriCommand command, GoogleSync googleSync) {
		if (maxIdSet == false)
			findMaxId();
		maxId++;
		MemoriEvent event = new MemoriEvent(command.getName(), command.getStart(), command.getEnd(), maxId, "google",
				command.getDescription(), command.getLocation());

		memoriCalendar.add(event);
		googleSync.executeCommand(event, command);
		
		return MESSAGE_ADD;
	}

	private String read(MemoriCommand command) {
		MemoriEvent displayText;
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			int index = command.getIndex();

			if (memoriCalendar.size() < index) {
				return LINE_INDEX_DOES_NOT_EXISTS;
			} else {
				displayText = memoriCalendar.get(index - 1);
				return displayText.read();
			}
		}
	}

	private String delete(MemoriCommand command, GoogleSync googleSync) {
		MemoriEvent event;
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			int index = command.getIndex();
			// to implement getIndex in MemoriCommand that returns a String

			if (memoriCalendar.size() < index) {
				return LINE_INDEX_DOES_NOT_EXISTS;
			} else {
				event = memoriCalendar.get(index - 1);
				memoriCalendar.remove(index - 1);
				googleSync.executeCommand(event, command);
				return String.format(MESSAGE_DELETE);
			}

		}

	}

	private String update(MemoriCommand command, GoogleSync googleSync) {
		MemoriEvent originalEvent;
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			int index = command.getIndex();

			if (memoriCalendar.size() < index) {
				return LINE_INDEX_DOES_NOT_EXISTS;
			} else {
				originalEvent = memoriCalendar.get(index - 1);
				originalEvent.update(command.getName(), command.getStart(), command.getEnd(), command.getDescription(),
						command.getLocation());
				googleSync.executeCommand(originalEvent, command);
				return String.format(MESSAGE_UPDATE, index);
			}
		}

	}
}