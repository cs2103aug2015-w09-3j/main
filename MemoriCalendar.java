package memori;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MemoriCalendar {
	private static final String MESSAGE_ADD = "Event Added.\n";
	private static final String MESSAGE_DELETE = "Event %1$d Deleted.\n";
	private static final String MESSAGE_INVALID_KEYWORD = "Keyword not found.\n";
	private static final String MESSAGE_READ = "Reading: \n";
	private static final String MESSAGE_SORT = "Sorted.";
	private static final String MESSAGE_UPDATE = "Updated Event %1$d \n";
	private static final String MESSAGE_INVALID_INPUT_NAME = "Invalid input. Name should not be empty. \n";
	private static final String MESSAGE_INVALID_INPUT_START = "Invalid input. New start date should not be later"
																+ " than original end date. \n";
	private static final String MESSAGE_INVALID_INPUT_END = "Invalid input. New end date should not be earlier"
			                                                  + " than original start date. \n";
	private static final String MESSAGE_INVALID_INPUT_GENERAL = "Invalid input. \n";
	private static final String MESSAGE_COMPLETE = "Tasks have been marked complete. \n";
	private static final String LINE_INDEX_DOES_NOT_EXISTS = "Line index does not exists.\n";
	private static final String MESSAGE_EMPTYFILE = "File is Empty.\n";
	private static final String INDEX_HEADER = "No: ";
	private static final String NAME_HEADER = "Name of Event:";
	private static final String START_HEADER = "Start: ";
	private static final String END_HEADER = "End: ";
	private static final String COMPLETE_HEADER = "Complete: ";
	//private static final String DISPLAY_FORMAT = "%1$s %2$s  %3$s    %4$s\n";
	private static final String DISPLAY_FORMAT = "%1$s %2$s  %3$s    %4$s	%5$s\n";

	// Display Modes
	public static final int MAIN = 0;
	public static final int SEARCH = 1;

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

	public String execute(MemoriCommand command, GoogleSync googleSync) {
	
		switch (command.getType()) {
		case ADD:
			return add(command, googleSync);
		case UPDATE:
			return update(command, googleSync);
		case DELETE:
			return delete(command, googleSync);
		case READ:
			return read(command);
		case SORT:
			return sort(command);
		case SEARCH:
			return search(command);
		case COMPLETE:
			return complete(command, googleSync);
		default:
			return "invalid\n";
		}
	}

	public String add(MemoriCommand command, GoogleSync googleSync) {
		if (maxIdSet == false)
			findMaxId();
		maxId++;
		MemoriEvent event = new MemoriEvent(command.getName(), command.getStart(), command.getEnd(), maxId, null,
				command.getDescription(), command.getLocation());

		memoriCalendar.add(event);
		googleSync.executeCommand(event, command);

		return MESSAGE_ADD;
	}
	
	private String update(MemoriCommand command, GoogleSync googleSync) {
		MemoriEvent originalEvent;
		MemoriEvent eventCheck;
		MemoriEvent searchedEvent;
		String updateStatus = "";
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			int index = command.getIndex();

			if (memoriCalendar.size() < index) {
				return LINE_INDEX_DOES_NOT_EXISTS;
			} else {
				if(command.getName() == null){
					return MESSAGE_INVALID_INPUT_NAME;
				}
				//Currently parser takes the earlier date and puts it as the start date.
				//So this case is never approached.
				if(command.getStart() != null && command.getEnd() != null){
					if((command.getStart()).after(command.getEnd())){
						return MESSAGE_INVALID_INPUT_GENERAL;
					}
				}
				
				if(!searchedList.isEmpty()){
					if(searchedList.size() < index){
						return LINE_INDEX_DOES_NOT_EXISTS;
					}
					
					searchedEvent = searchedList.get(index - 1);
					for(int j =0; j < memoriCalendar.size(); j++){
						eventCheck = memoriCalendar.get(j);
						if((eventCheck.getName() == searchedEvent.getName()) //internalID does not work
								&& (eventCheck.getStart() == searchedEvent.getStart())
								&& (eventCheck.getEnd() == searchedEvent.getEnd())
								&& (eventCheck.getDescription() == searchedEvent.getDescription())
								&& (eventCheck.getLocation() == searchedEvent.getLocation())){
							
							if(command.getEnd() == null && command.getStart() != null
									&& eventCheck.getEnd() != null){
								if((command.getStart()).after(eventCheck.getEnd())){
									return MESSAGE_INVALID_INPUT_START;
								}
							}
							if(command.getStart() == null && command.getEnd() != null
									&& eventCheck.getStart() != null){
								if((command.getEnd()).before(eventCheck.getStart())){
									return MESSAGE_INVALID_INPUT_END;
								}
							}
							updateStatus = eventCheck.update(command.getName(), command.getStart(), command.getEnd(),
									command.getDescription(), command.getLocation(), command.getMemoriField());
							searchedList = new ArrayList<MemoriEvent>();
							googleSync.executeCommand(eventCheck, command);
						}	
					}
				}else{	
					originalEvent = memoriCalendar.get(index - 1);
					if(command.getEnd() == null && command.getStart() != null
							&& originalEvent.getEnd() != null){
						if((command.getStart()).after(originalEvent.getEnd())){
							return MESSAGE_INVALID_INPUT_START;
						}
					}
					if(command.getStart() == null && command.getEnd() != null
							&& originalEvent.getStart() != null){
						if((command.getEnd()).before(originalEvent.getStart())){
							return MESSAGE_INVALID_INPUT_END;
						}
					}
					updateStatus = originalEvent.update(command.getName(), command.getStart(), command.getEnd(),
							command.getDescription(), command.getLocation(), command.getMemoriField());
					googleSync.executeCommand(originalEvent, command);
				}
				
				
				
				
				return String.format(MESSAGE_UPDATE, index) + updateStatus;
			}
		}

	}

	private String delete(MemoriCommand command, GoogleSync googleSync) {
		MemoriEvent event;
		MemoriEvent eventCheck;
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			ArrayList<Integer> toDelete = command.getIndexes();
			Collections.sort(toDelete);
			Collections.reverse(toDelete);
			for (int i = 0; i < toDelete.size(); i++) {
				int index = toDelete.get(i);
				if (memoriCalendar.size() < index) {
					return LINE_INDEX_DOES_NOT_EXISTS;

				}
			}
			String output = "";
			for (int i = 0; i < toDelete.size(); i++) {
				int index = toDelete.get(i);
				if(!searchedList.isEmpty()){
					if(searchedList.size() < index){
						return LINE_INDEX_DOES_NOT_EXISTS;
					}
					event = searchedList.get(index - 1);
					for(int j =0; j < memoriCalendar.size(); j++){
						eventCheck = memoriCalendar.get(j);
						if((eventCheck.getName() == event.getName()) //internalID does not work
								&& (eventCheck.getStart() == event.getStart())
								&& (eventCheck.getEnd() == event.getEnd())
								&& (eventCheck.getDescription() == event.getDescription())
								&& (eventCheck.getLocation() == event.getLocation())){
							memoriCalendar.remove(j);
							searchedList = new ArrayList<MemoriEvent>();
						}
					}
					googleSync.executeCommand(event, command);
				}else{
					event = memoriCalendar.get(index - 1);
					memoriCalendar.remove(index - 1);
					googleSync.executeCommand(event, command);
				}	
				output += String.format(MESSAGE_DELETE , index);
	
			}
			return output;

		}

	}

	private String read(MemoriCommand command) {
		MemoriEvent event;
		ArrayList<Integer> toRead = command.getIndexes();
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			for (int i = 0; i < toRead.size(); i++) {
				int index = toRead.get(i);
				if (memoriCalendar.size() < index) {
					return LINE_INDEX_DOES_NOT_EXISTS;

				}
			}
			String output = "";
			for (int i = 0; i < toRead.size(); i++) {
				int index = toRead.get(i);
				event = memoriCalendar.get(index - 1);
				output += String.format(MESSAGE_READ, index);
				output += event.read();
	
			}
			return output;
		}
	}

	private String sort(MemoriCommand command) {
		Boolean[] f_values = command.getMemoriField();

		for (int i = 0; i < f_values.length; i++) {
			if (f_values[i] == Boolean.TRUE) {
				switch (i) {
				case 0:
					this.sortBy(MemoriEvent.nameComparator);
					break;
				case 1:
					this.sortBy(MemoriEvent.startDateComparator);
					break;
				case 2:
					this.sortBy(MemoriEvent.endDateComparator);
					break;
				case 3:
					this.sortBy(MemoriEvent.descriptionComparator);
					break;
				case 4:
					this.sortBy(MemoriEvent.locationComparator);
					break;
				}
				break;
			}
		}
		return MESSAGE_SORT;
	}

	private String search(MemoriCommand command) {
		searchedList = new ArrayList<MemoriEvent>();
		String text = command.getName();
		Date userStart = command.getStart();
		Date userEnd = command.getEnd();
		MemoriEvent taskLine;

		for (int i = 0; i < memoriCalendar.size(); i++) {
			taskLine = memoriCalendar.get(i);
			if (userStart != null && userEnd != null) {
				searchDates(taskLine, userStart, userEnd);
			} else {
				searchOtherFields(taskLine, text);
			}
		}
		if (!searchedList.isEmpty()) {
			return display(SEARCH);
		} else {
			return MESSAGE_INVALID_KEYWORD;
		}

	}
	
	private boolean searchDates(MemoriEvent event, Date start, Date end) {
		Date eventStart = event.getStart();
		Date eventEnd = event.getEnd();
		if (eventEnd != null && (start.before(eventEnd) || start.equals(eventEnd))
				&& (end.after(eventEnd) || end.equals(eventEnd))) {
			searchedList.add(event);
			return true;
		} else if (eventStart != null && (start.before(eventStart) || start.equals(eventStart))
				&& (end.after(eventStart) || end.equals(eventStart))) {
			searchedList.add(event);
			return true;
		}
		return false;
	}

	private void searchOtherFields(MemoriEvent event, String searchTerm) {
		String name = event.getName();
		String description = event.getDescription();
		String location = event.getLocation();

		if (name != null && name.toUpperCase().contains(searchTerm.toUpperCase())) {
			searchedList.add(event);
		} else if (description != null && description.toUpperCase().contains(searchTerm.toUpperCase())) {
			searchedList.add(event);
		} else if (location != null && location.toUpperCase().contains(searchTerm.toUpperCase())) {
			searchedList.add(event);
		}
	}
	
	private String complete(MemoriCommand command, GoogleSync googleSync) {
		MemoriEvent originalEvent;
		ArrayList<Integer> indexes = command.getIndexes();
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			for (int i = 0; i < indexes.size(); i++) {
				int index = indexes.get(i);
				if (memoriCalendar.size() < index) {
					return LINE_INDEX_DOES_NOT_EXISTS;
				} else {
					originalEvent = memoriCalendar.get(index - 1);
					originalEvent.setComplete(true);
					googleSync.executeCommand(originalEvent, command);
				}
			}
		}
		return MESSAGE_COMPLETE;
	}


	public void sortBy(Comparator<MemoriEvent> comparator) {
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

	public String display(int mode) {
		String paddedNameHeader = padRight(NAME_HEADER, MemoriEvent.NAME_CUT_OFF);
		String paddedStartHeader = padRight(START_HEADER, MemoriEvent.DATE_FORMAT.length());
		String paddedEndHeader = padRight(END_HEADER, MemoriEvent.DATE_FORMAT.length() + 2);
		String output = String.format(DISPLAY_FORMAT, INDEX_HEADER, 
				paddedNameHeader, paddedStartHeader, paddedEndHeader, COMPLETE_HEADER);
		ArrayList<MemoriEvent> toDisplay = memoriCalendar;
		if (mode == SEARCH) {
			toDisplay = searchedList;
		}
		for (int i = 1; i <= toDisplay.size(); i++) {
			String index = padRight(Integer.toString(i), INDEX_HEADER.length());
			MemoriEvent e = toDisplay.get(i - 1);
			output += index + " " + e.display() + "\n";
		}
		return output;
	}

	private String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}
}