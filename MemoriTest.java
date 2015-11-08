package memori;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.google.api.services.calendar.Calendar;

import memori.googleSync.GCalConnect;
import memori.googleSync.MemoriSync;
import memori.logic.MemoriCalendar;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriParser;
import memori.ui.MemoriUI;

public class MemoriTest {
	private static final String MESSAGE_ADD = "Event Added.\n";
	private static final String MESSAGE_DELETE = "Event Deleted.\n";
	private static final String MESSAGE_NO_RESULTS = "No results found\n";
	private static final String MESSAGE_READ = "Reading: %1$d \n";
	private static final String MESSAGE_SORT = "Sorted.\n";
	private static final String MESSAGE_UPDATE = "Updated Event %1$d \n";
	private static final String MESSAGE_INVALID_INDEX = "Line index does not exists.\n";
	private static final String MESSAGE_INVALID_INPUT_NAME = "Invalid input. Name should not be empty. \n";
	private static final String MESSAGE_INVALID_INPUT_START = "Invalid input. New start date should not be later"
			+ " than original end date. \n";
	private static final String MESSAGE_INVALID_INPUT_END = "Invalid input. New end date should not be earlier"
			+ " than original start date. \n";
	private static final String MESSAGE_INVALID_INPUT_GENERAL = "Invalid input. \n";
	private static final String MESSAGE_COMPLETE = "Tasks have been marked complete. \n";
	private static final String MESSAGE_OPEN = "Tasks have been reopened\n";
	private static final String MESSAGE_EMPTYFILE = "File is Empty.\n";
	private static final String MESSAGE_CHANGE_SEARCH = "Your search conditions has been changed.\n";
	private static final String MESSAGE_UNDO = "Your changes have been undone.\n";
	private static final String MESSAGE_UNDO_INVALID = "You cannot undo anymore.\n";
	
	private static final String INDEX_HEADER = "No: ";
	private static final String NAME_HEADER = "Name:";
	private static final String START_HEADER = "Start: ";
	private static final String END_HEADER = "End: ";
	private static final String LOCATION_HEADER = "Location:";
	private static final String DESCRIPTION_HEADER = "Description:";

	private MemoriCalendar calendar;
	private MemoriSync sync;
	private MemoriParser parser = new MemoriParser();
	private MemoriUI ui = new MemoriUI();
	private Calendar service = GCalConnect.getCalendarService();

	public void initialize() {
		ErrorSuppressor.supress();
		try {
			service.calendars().clear("primary").execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar = new MemoriCalendar();
		sync = new MemoriSync();
		calendar.initialize();
		sync.initialize(ui, calendar);
		calendar.display();
	}

	@Test
	public void testUndoFail() {
		initialize();
		String userInput = "undo";
		MemoriCommand cmd = parser.parse(userInput);
		String response = "";
		response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_UNDO_INVALID));
	}

	@Test
	public void testAddFloat() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_ADD));
	}
	@Test
	public void testAddEvent() {
		initialize();
		String userInput = "add -n test -s today -e tomorrow";
		MemoriCommand cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_ADD));
	}
	
	@Test
	public void testAddDeadline() {
		initialize();
		String userInput = "add -n test -e tomorrow";
		MemoriCommand cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_ADD));
	}
	
	@Test
	public void testAddNoEnd() {
		initialize();
		String userInput = "add -n test -s tomorrow";
		MemoriCommand cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_ADD));
	}
	
	@Test
	public void testAddwithAllFields() {
		initialize();
		String userInput = "add -n test -s tomorrow -e the next day -d description - l location";
		MemoriCommand cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_ADD));
	}


	@Test
	public void testDeleteOne() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		calendar.display();
		userInput = "delete 1";
		cmd = parser.parse(userInput);
		response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_DELETE));
	}

	@Test
	public void testDeleteMultiple() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		for (int i = 0; i < 10; i++) {
			calendar.execute(cmd, sync);
		}
		calendar.display();
		userInput = "delete 1-10";
		cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_DELETE));
	}

	@Test
	public void testComplete1() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		calendar.execute(cmd, sync);
		calendar.display();
		userInput = "complete 1";
		cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_COMPLETE));
	}

	@Test
	public void testCompleteMultiple() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		for (int i = 0; i < 10; i++) {
			calendar.execute(cmd, sync);
		}
		calendar.display();
		userInput = "complete 1-10";
		cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		assertTrue(response.equals(MESSAGE_COMPLETE));
	}
	
	@Test
	public void testUpdateName() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		calendar.execute(cmd,sync);
		calendar.display();
		userInput = "update 1 -n update";
		cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		System.out.println(response);
		assertTrue(response.contains(String.format(MESSAGE_UPDATE, 1)));
		assertTrue(response.contains(NAME_HEADER));
	}
	
	@Test
	public void testUpdateStart() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		calendar.execute(cmd,sync);
		calendar.display();
		userInput = "update 1 -s today";
		cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		System.out.println(response);
		assertTrue(response.contains(String.format(MESSAGE_UPDATE, 1)));
		assertTrue(response.contains(START_HEADER));
	}
	
	@Test
	public void testUpdateEnd() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		calendar.execute(cmd,sync);
		calendar.display();
		userInput = "update 1 -e today";
		cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		System.out.println(response);
		assertTrue(response.contains(String.format(MESSAGE_UPDATE, 1)));
		assertTrue(response.contains(END_HEADER));
	}
	
	@Test
	public void testUpdateStartAndEnd() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		calendar.execute(cmd,sync);
		calendar.display();
		userInput = "update 1 -s today -e tomorrow";
		cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		System.out.println(response);
		assertTrue(response.contains(String.format(MESSAGE_UPDATE, 1)));
		assertTrue(response.contains(START_HEADER));
		assertTrue(response.contains(END_HEADER));
	}
	
	@Test
	public void testUpdateAllFields() {
		initialize();
		String userInput = "add -n test";
		MemoriCommand cmd = parser.parse(userInput);
		calendar.execute(cmd,sync);
		calendar.display();
		userInput = "update 1 -n updated -s today -e tomorrow -d description -l location";
		cmd = parser.parse(userInput);
		String response = calendar.execute(cmd, sync);
		System.out.println(response);
		assertTrue(response.contains(String.format(MESSAGE_UPDATE, 1)));
		assertTrue(response.contains(NAME_HEADER));
		assertTrue(response.contains(START_HEADER));
		assertTrue(response.contains(END_HEADER));
		assertTrue(response.contains(LOCATION_HEADER));
		assertTrue(response.contains(DESCRIPTION_HEADER));
	}



}