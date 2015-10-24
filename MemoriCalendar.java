package memori;

import java.util.ArrayList;
import java.util.Comparator;

import edu.emory.mathcs.backport.java.util.Collections;

public class MemoriCalendar {
	private static final String MESSAGE_ADD = "Event Added.\n";
	private static final String MESSAGE_DELETE = "Deleted.\n";
	private static final String MESSAGE_INVALID_KEYWORD = "Keyword not found.\n";
	private static final String MESSAGE_READ = "Reading: \n";
	private static final String MESSAGE_SORT = "Sorted.\n";
	private static final String MESSAGE_UPDATE = "Updated Event %d \n";
	private static final String LINE_INDEX_DOES_NOT_EXISTS = "Line index does not exists.\n";
	private static final String MESSAGE_EMPTYFILE = "File is Empty.\n";
	private static final String INDEX_HEADER = "No: ";
	private static final String NAME_HEADER = "Name of Event:";
	private static final String START_HEADER = "Start: ";
	private static final String END_HEADER = "End: ";
	private static final String DISPLAY_FORMAT = "%1$s %2$s  %3$s    %4$s\n";
	
	private ArrayList<MemoriEvent> memoriCalendar;
	private ArrayList<MemoriEvent> searchedList;
	private int maxId = 0;
	private boolean maxIdSet = false;

	public MemoriCalendar() {
		this.memoriCalendar = new ArrayList<MemoriEvent>();
	}
	
	public ArrayList<MemoriEvent> getEvents() {
		return memoriCalendar;
	}

	public void sortBy(Comparator<MemoriEvent> comparator) {
		Collections.sort(memoriCalendar, comparator);
	}
	
	private String sort(MemoriCommand command, GoogleSync googleSync){		
		Boolean[] f_values = command.getMemoriField();
		
		for(int i = 0; i < f_values.length; i++){
			if(f_values[i] == Boolean.TRUE){
				if(i == 0){
					this.sortBy(MemoriEvent.nameComparator);
				}else if(i == 1){
					this.sortBy(MemoriEvent.startDateComparator);
				}else if(i == 2){
					this.sortBy(MemoriEvent.endDateComparator);
				}else if(i == 3){
					this.sortBy(MemoriEvent.descriptionComparator);
				}else{
					this.sortBy(MemoriEvent.locationComparator);
				}
			}
		}
		return MESSAGE_SORT;
	}
	
	private String search(MemoriCommand command, GoogleSync googleSync){
		String text = command.getName();
		MemoriEvent taskLine;
		MemoriEvent displayText;
		for(int i = 0; i < memoriCalendar.size(); i++){
			taskLine = memoriCalendar.get(i);
			String name = taskLine.getName();
			String description = taskLine.getDescription();
			String location = taskLine.getLocation();
			if(command.getStart() != null){
				if(command.getStart() == taskLine.getStart()){
					displayText = memoriCalendar.get(i);
					searchedList.add(displayText);
				}
			}else{
				if(command.getEnd() != null){
					if(command.getEnd() == taskLine.getEnd()){
						displayText = memoriCalendar.get(i);
						searchedList.add(displayText);
					}
				}else{
					if((name.contains(text)) || (description.contains(text))
							|| (location.contains(text))){
						displayText = memoriCalendar.get(i);
						searchedList.add(displayText);
					}
				}
			}
			
		}
		
		if(!searchedList.isEmpty()){
			String paddedNameHeader = padRight(NAME_HEADER, MemoriEvent.NAME_CUT_OFF);
			String paddedStartHeader = padRight(START_HEADER, MemoriEvent.DATE_FORMAT.length());
			String output = String.format(DISPLAY_FORMAT, INDEX_HEADER, paddedNameHeader, paddedStartHeader, END_HEADER);
			int i = 1;
			for (MemoriEvent e : searchedList) {
				String index = padRight(Integer.toString(i), INDEX_HEADER.length());
				output += index + " " + e.display() + "\n";
				i++;
			}
			return output;
		}else{
			return MESSAGE_INVALID_KEYWORD;
		}
		
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
		case SORT:
			return sort(command, googleSync);
		case SEARCH:
			return search(command, googleSync);
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