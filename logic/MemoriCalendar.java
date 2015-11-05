//@@author A0113645L
package memori.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import memori.googleSync.EventConverter;
import memori.googleSync.MemoriSync;
import memori.parsers.MemoriCommand;

public class MemoriCalendar {
	private static final String MESSAGE_ADD = "Event Added.\n";
	private static final String MESSAGE_DELETE = "Event Deleted.\n";
	private static final String MESSAGE_NO_RESULTS = "No results found\n";
	private static final String MESSAGE_READ = "Reading:\n";
	private static final String MESSAGE_SORT = "Sorted.\n";
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
	private static final String COMPLETE_HEADER = "Completed:";
	private static final String DISPLAY_FORMAT = "%1$s %2$s  %3$s  %4$s %5$s\n";
	private static final String SEARCH_CONDITION_CHANGED= "Your search conditions has been changed\n";
	private static final String CURRENT_SEARCH_CONDITIONS = "Current search conditions:\n";
	private static final String SEARCH_KEYWORD = "Keyword:%1$s\n";
	// Display Modes
	public static final int MAIN = 0;
	public static final int SEARCH = 1;

	// Search mode
	public static final int DATE = 0;
	public static final int NAME = 0;

	// start of day
	private static final int FIRST_HOUR = 0;
	private static final int FIRST_MIN = 0;
	private static final int FIRST_SEC = 0;
	private static final int LAST_HOUR = 23;
	private static final int LAST_MIN = 59;
	private static final int LAST_SEC = 59;

	private ArrayList<MemoriEvent> memoriCalendar;
	private ArrayList<MemoriEvent> searchedList;
	private int maxId = 0;
	private boolean maxIdSet = false;
	private Date searchStart;
	private Date searchEnd;
	private String searchText;

	public MemoriCalendar() {
		this.memoriCalendar = new ArrayList<MemoriEvent>();
		
	}
	
	public MemoriCalendar copy(){
		MemoriCalendar theCopy = new MemoriCalendar();
		theCopy.maxId = this.maxId;
		theCopy.maxIdSet = this.maxIdSet;
		theCopy.searchStart = (Date)this.searchStart.clone();
		theCopy.searchEnd = (Date)this.searchEnd.clone();
		theCopy.searchText = this.searchText;
		for(int i=0;i< this.memoriCalendar.size();i++){
			theCopy.memoriCalendar.add(this.memoriCalendar.get(i));
		}
		return theCopy;
	}
	public void initialize(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, FIRST_HOUR);
		calendar.set(Calendar.MINUTE, FIRST_MIN);
		calendar.set(Calendar.SECOND, FIRST_SEC);
		searchStart = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, LAST_HOUR);
		calendar.set(Calendar.MINUTE, LAST_MIN);
		calendar.set(Calendar.SECOND, LAST_SEC);
		searchEnd = calendar.getTime();
	}

	public ArrayList<MemoriEvent> getEvents() {
		return memoriCalendar;
	}
	private String setSearch(MemoriCommand command){
		searchText = command.getName();
		searchStart = command.getStart();
		searchEnd = command.getEnd();
		return SEARCH_CONDITION_CHANGED;
	}
	
	public String execute(MemoriCommand command, MemoriSync googleSync) {

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
			return setSearch(command);
		case COMPLETE:
			return complete(command, googleSync);
		default:
			return command.getInvalidMessage();
		}
	}

	public String add(MemoriCommand command, MemoriSync googleSync) {
		if (maxIdSet == false)
			findMaxId();
		maxId++;
		MemoriEvent event = new MemoriEvent(command.getName(), command.getStart(), command.getEnd(), maxId, null,
				command.getDescription(), command.getLocation());

		memoriCalendar.add(event);
		googleSync.addNewCommand(event, command);

		return MESSAGE_ADD;
	}

	private String update(MemoriCommand command, MemoriSync googleSync) {
		MemoriEvent searchedEvent;
		String updateStatus = "";
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			int index = command.getIndex();

			if (memoriCalendar.size() < index || index < 0) {
				return LINE_INDEX_DOES_NOT_EXISTS;
			} else {
				if (command.getName() == null) {
					return MESSAGE_INVALID_INPUT_NAME;
				}
				// Currently parser takes the earlier date and puts it as the
				// start date.
				// So this case is never approached.
				if (command.getStart() != null && command.getEnd() != null) {
					if ((command.getStart()).after(command.getEnd())) {
						return MESSAGE_INVALID_INPUT_GENERAL;
					}
				}

				if (!searchedList.isEmpty()) {
					if (searchedList.size() < index) {
						return LINE_INDEX_DOES_NOT_EXISTS;
					}
					searchedEvent = searchedList.get(index - 1);
					String checkStatus = checkUpdateConditions(command, searchedEvent);
					if (checkStatus != null)
						return checkStatus;
					updateStatus = searchedEvent.update(command.getName(), command.getStart(), command.getEnd(),
							command.getDescription(), command.getLocation(), command.getMemoriField());
					googleSync.addNewCommand(searchedEvent, command);
				}
				return String.format(MESSAGE_UPDATE, index) + updateStatus;
			}
		}
	}

	private String delete(MemoriCommand command, MemoriSync googleSync) {
		MemoriEvent event;
		if (memoriCalendar.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			ArrayList<Integer> toDelete = command.getIndexes();
			Collections.sort(toDelete);
			Collections.reverse(toDelete);
			for (int i = 0; i < toDelete.size(); i++) {
				int index = toDelete.get(i);
				if (memoriCalendar.size() < index || index <0 ) {
					return LINE_INDEX_DOES_NOT_EXISTS;
				}
			}
			
			for (int i = 0; i < toDelete.size(); i++) {
				int index = toDelete.get(i);
				if (!searchedList.isEmpty()) {
					event = searchedList.get(index - 1);
					int mainIndex = mainIndexMapper(event);
					memoriCalendar.remove(mainIndex);
					googleSync.addNewCommand(event, command);
				}
			}
			return MESSAGE_DELETE;
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
				//event = memoriCalendar.get(index - 1);
				if(!searchedList.isEmpty()){
					event = searchedList.get(index -1);
					output += String.format(MESSAGE_READ, index);
					output += event.read();
				}
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

	private String search() {
		searchedList = new ArrayList<MemoriEvent>();
		MemoriEvent taskLine;
		for (int i = 0; i < memoriCalendar.size(); i++) {
			taskLine = memoriCalendar.get(i);
			if (searchStart != null && searchEnd != null) {
				
				searchDates(taskLine, searchStart, searchEnd);
			} else {
				searchOtherFields(taskLine, searchText);
			}
		}
		if (!searchedList.isEmpty()) {
			return null;
		} else {
			return MESSAGE_NO_RESULTS;
		}

	}

	private boolean searchDates(MemoriEvent event, Date start, Date end) {
		Date eventStart = event.getStart();
		Date eventEnd = event.getEnd();
		if(eventStart == null){
			eventStart = EventConverter.START_OF_TIME;
		}
		if(eventEnd == null){
			eventEnd = EventConverter.END_OF_TIME;
		}
		 
		if(((start.before(eventStart) || start.equals(eventStart)) && (end.after(eventStart) || end.equals(eventStart))) ||
			((start.before(eventEnd) || start.equals(eventEnd))    && (end.after(eventEnd))|| end.equals(eventEnd))||
			((eventStart.before(start) || eventStart.equals(start)) && (eventEnd.after(start) ||  eventEnd.equals(start)))){
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

	private String complete(MemoriCommand command, MemoriSync googleSync) {
		MemoriEvent originalEvent;
		ArrayList<Integer> indexes = command.getIndexes();
		if (searchedList.isEmpty()) {
			return MESSAGE_EMPTYFILE;
		} else {
			for (int i = 0; i < indexes.size(); i++) {
				int index = indexes.get(i);
				if (searchedList.size() < index) {
					return LINE_INDEX_DOES_NOT_EXISTS;
				} else {
					originalEvent = searchedList.get(index - 1);
					originalEvent.setComplete(true);
					googleSync.addNewCommand(originalEvent, command);
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

	public String display() {
		String paddedNameHeader = padRight(NAME_HEADER, MemoriEvent.NAME_CUT_OFF);
		String paddedStartHeader = padRight(START_HEADER, MemoriEvent.DATE_FORMAT.length() + 2);
		String paddedEndHeader = padRight(END_HEADER, MemoriEvent.DATE_FORMAT.length() + 3);
		String output = CURRENT_SEARCH_CONDITIONS;
		String searchStatus = search();
		if(searchStart!= null && searchEnd != null){
			output += START_HEADER + searchStart;
			output +="\n";
			output += END_HEADER + searchEnd;
			output +="\n";
		}
		else{
			output += String.format(SEARCH_KEYWORD, searchText);
		}
		
		if(searchStatus != null){
			return  output  + searchStatus;
		}

		output += String.format(DISPLAY_FORMAT, INDEX_HEADER, paddedNameHeader, paddedStartHeader,
				paddedEndHeader, COMPLETE_HEADER);
		for (int i = 1; i <= searchedList.size(); i++) {
			String index = padRight(Integer.toString(i), INDEX_HEADER.length());
			MemoriEvent e = searchedList.get(i - 1);
			output += index + " " + e.display() + "\n";
		}
		return output;
	}

	private int mainIndexMapper(MemoriEvent toMap) {
		for (int i = 0; i < memoriCalendar.size(); i++) {
			MemoriEvent current = memoriCalendar.get(i);
			if (current.hashCode() == toMap.hashCode())
				return i;
		}
		return -1;
	}

	private String checkUpdateConditions(MemoriCommand command, MemoriEvent eventCheck) {

		if (command.getEnd() == null && command.getStart() != null && eventCheck.getEnd() != null) {
			if ((command.getStart()).after(eventCheck.getEnd())) {
				return MESSAGE_INVALID_INPUT_START;
			}
		}
		if (command.getStart() == null && command.getEnd() != null && eventCheck.getStart() != null) {
			if ((command.getEnd()).before(eventCheck.getStart())) {
				return MESSAGE_INVALID_INPUT_END;
			}
		}
		return null;
	}

	private String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}
}