//@@author A0098038W
package memori.logic.UnitTest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.services.calendar.Calendar;

import memori.ErrorSuppressor;
import memori.googleSync.GCalConnect;
import memori.googleSync.MemoriSync;
import memori.logic.MemoriCalendar;
import memori.logic.MemoriEvent;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;
import memori.ui.MemoriUI;

public class MemoriCalendarTest {

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
	private static final String MESSAGE_CHANGE_SEARCH = "Your search conditions has been changed\n";

	private MemoriCalendar calendar = new MemoriCalendar();

	private MemoriSync google = new MemoriSync();

	private Calendar service = GCalConnect.getCalendarService();
	private MemoriUI ui = new MemoriUI();

	@BeforeClass
	public static void setUpBforeClass() throws Exception {

	}

	public void initialize() {
		ErrorSuppressor.supress();
		try {
			service.calendars().clear("primary").execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		calendar = new MemoriCalendar();
		google = new MemoriSync();
		calendar.initialize();
		google.initialize(calendar);
		calendar.display();
	}
	@Test
	public void testExecuteAddEvent() {
		initialize();
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = new Date();
		Date end = new Date();
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		String observed = calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		MemoriEvent latestEvent = main.get(main.size() - 1);
		assertTrue(observed.contains(MESSAGE_ADD));
		assertTrue(expectedEvent.equals(latestEvent));
	}
	@Test
	public void testExecuteAddDeadline() {
		initialize();
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = new Date();
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		String observed = calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		MemoriEvent latestEvent = main.get(main.size() - 1);
		assertTrue(observed.contains(MESSAGE_ADD));
		assertTrue(expectedEvent.equals(latestEvent));
	}
	@Test
	public void testExecuteAddNoEnd() {
		initialize();
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = new Date();
		Date end = null;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		String observed = calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		MemoriEvent latestEvent = main.get(main.size() - 1);
		assertTrue(observed.contains(MESSAGE_ADD));
		assertTrue(expectedEvent.equals(latestEvent));
	}
	@Test
	public void testExecuteAddFloat() {
		initialize();
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		String observed = calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		MemoriEvent latestEvent = main.get(main.size() - 1);
		assertTrue(observed.contains(MESSAGE_ADD));
		assertTrue(expectedEvent.equals(latestEvent));
	}
	@Test
	public void testExecuteDeleteOne() {
		initialize();
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent latestEvent = main.get(main.size() - 1);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		indexes.add(1);
		calendar.display();
		mc = new MemoriCommand(MemoriCommandType.DELETE, indexes);
		calendar.execute(mc, google);

		assertTrue(!main.contains(latestEvent));
	}
	@Test
	public void testExecuteDeleteMultiple() {
		initialize();
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			calendar.execute(mc, google);
			indexes.add(i + 1);
		}
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent latestEvent = main.get(main.size() - 1);
		calendar.display();
		mc = new MemoriCommand(MemoriCommandType.DELETE, indexes);
		calendar.execute(mc, google);
		assertTrue(!main.contains(latestEvent));
	}
	@Test
	public void testExecuteUpdateName() {
		initialize();
		Boolean[] memoriField = new Boolean[] { true, false, false, false, false };
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		int index = 1;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		calendar.execute(mc, google);
		fieldStr[0] = "updated";
		calendar.display();
		mc = new MemoriCommand(MemoriCommandType.UPDATE, start, end, fieldStr, index, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent latestEvent = main.get(main.size() - 1);
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		assertTrue(latestEvent.equals(expectedEvent));
	}
	@Test
	public void testExecuteUpdateStart() {
		initialize();
		Boolean[] memoriField = new Boolean[] { false, true, false, false, false };
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		int index = 1;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		calendar.execute(mc, google);
		start = new Date();
		calendar.display();
		mc = new MemoriCommand(MemoriCommandType.UPDATE, start, end, fieldStr, index, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent latestEvent = main.get(main.size() - 1);
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		assertTrue(latestEvent.equals(expectedEvent));
	}
	@Test
	public void testExecuteUpdateEnd() {
		initialize();
		Boolean[] memoriField = new Boolean[] { false, false, true, false, false };
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		int index = 1;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		calendar.execute(mc, google);
		end = new Date();
		calendar.display();
		mc = new MemoriCommand(MemoriCommandType.UPDATE, start, end, fieldStr, index, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent latestEvent = main.get(main.size() - 1);
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		assertTrue(latestEvent.equals(expectedEvent));
	}
	@Test
	public void testExecuteUpdateLocation() {
		initialize();
		Boolean[] memoriField = new Boolean[] { false, false, false, true, false };
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		int index = 1;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		calendar.execute(mc, google);
		calendar.display();
		fieldStr[1] = "updated";
		mc = new MemoriCommand(MemoriCommandType.UPDATE, start, end, fieldStr, index, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent latestEvent = main.get(main.size() - 1);
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		assertTrue(latestEvent.equals(expectedEvent));
	}
	@Test
	public void testExecuteUpdateDescription() {
		initialize();
		Boolean[] memoriField = new Boolean[] { false, false, false, false, true };
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		int index = 1;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		calendar.execute(mc, google);
		calendar.display();
		fieldStr[2] = "updated";
		mc = new MemoriCommand(MemoriCommandType.UPDATE, start, end, fieldStr, index, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent latestEvent = main.get(main.size() - 1);
		MemoriEvent expectedEvent = new MemoriEvent(fieldStr[0], start, end, 0, null, fieldStr[2], fieldStr[1]);
		assertTrue(latestEvent.equals(expectedEvent));
	}
	@Test
	public void testExecuteSortName() {
		initialize();
		String[] fieldStr = new String[] { "", "testDescribe", "testPlace" };
		Date start = new Date();
		Date end = new Date();
		Boolean[] memoriField = new Boolean[] { true, false, false, false, false };
		MemoriCommand mc;
		String[] names = new String[] { "5", "name", "123name", "HELLO", "hello", "blah" };
		for (int i = 0; i < names.length; i++) {
			fieldStr[0] = names[i];
			mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
			calendar.execute(mc, google);
		}
		mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> sorted = calendar.getEvents();
		boolean sortStatus = true;

		for (int i = 0; i < sorted.size() - 1; i++) {
			String first = sorted.get(i).getName();
			String second = sorted.get(i + 1).getName();
			if (first.compareToIgnoreCase(second) > 0) {
				sortStatus = false;
				break;
			}
		}
		assertTrue(sortStatus);
	}
	@Test
	public void testExecuteSortStart() {
		initialize();
		String[] fieldStr = new String[] { "", "testDescribe", "testPlace" };
		Date start = new Date();
		Date end = new Date();
		Boolean[] memoriField = new Boolean[] { false, true, false, false, false };
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		Date[] startDates = new Date[] { new Date(888888888L), new Date(222222222222L), new Date(3333333333333L),
				new Date(0), new Date() };
		for (int i = 0; i < startDates.length; i++) {
			start = startDates[i];
			mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
			calendar.execute(mc, google);
		}
		mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> sorted = calendar.getEvents();
		boolean sortStatus = true;

		for (int i = 0; i < sorted.size() - 1; i++) {
			Date first = sorted.get(i).getStart();
			Date second = sorted.get(i + 1).getStart();
			if (first.compareTo(second) > 0) {
				sortStatus = false;
				break;
			}
		}
		assertTrue(sortStatus);
	}
	@Test
	public void testExecuteSortEnd() {
		initialize();
		String[] fieldStr = new String[] { "", "testDescribe", "testPlace" };
		Date start = new Date();
		Date end = new Date();
		Boolean[] memoriField = new Boolean[] { false, false, true, false, false };
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		Date[] endDates = new Date[] { new Date(888888888L), new Date(222222222222L), new Date(3333333333333L),
				new Date(0), new Date() };
		for (int i = 0; i < endDates.length; i++) {
			end = endDates[i];
			mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
			calendar.execute(mc, google);
		}
		mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> sorted = calendar.getEvents();
		boolean sortStatus = true;

		for (int i = 0; i < sorted.size() - 1; i++) {
			Date first = sorted.get(i).getEnd();
			Date second = sorted.get(i + 1).getEnd();
			if (first.compareTo(second) > 0) {
				sortStatus = false;
				break;
			}
		}
		assertTrue(sortStatus);
	}
	@Test
	public void testExecuteSortDescription() {
		initialize();
		String[] fieldStr = new String[] { "name", "testDescribe", "testPlace" };
		Date start = new Date();
		Date end = new Date();
		Boolean[] memoriField = new Boolean[] { false, false, false, false, true };
		MemoriCommand mc;
		String[] names = new String[] { "5", "name", "123name", "HELLO", "hello", "blah" };
		for (int i = 0; i < names.length; i++) {
			fieldStr[2] = names[i];
			mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
			calendar.execute(mc, google);
		}
		mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> sorted = calendar.getEvents();
		boolean sortStatus = true;

		for (int i = 0; i < sorted.size() - 1; i++) {
			String first = sorted.get(i).getName();
			String second = sorted.get(i + 1).getName();
			if (first.compareToIgnoreCase(second) > 0) {
				sortStatus = false;
				break;
			}
		}
		assertTrue(sortStatus);
	}
	@Test
	public void testExecuteSortLocation() {
		initialize();
		String[] fieldStr = new String[] { "name", "testDescribe", "testPlace" };
		Date start = new Date();
		Date end = new Date();
		Boolean[] memoriField = new Boolean[] { true, false, false, true, false };
		MemoriCommand mc;
		String[] names = new String[] { "5", "name", "123name", "HELLO", "hello", "blah" };
		for (int i = 0; i < names.length; i++) {
			fieldStr[1] = names[i];
			mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
			calendar.execute(mc, google);
		}
		mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> sorted = calendar.getEvents();
		boolean sortStatus = true;

		for (int i = 0; i < sorted.size() - 1; i++) {
			String first = sorted.get(i).getLocation();
			String second = sorted.get(i + 1).getDescription();
			if (first.compareToIgnoreCase(second) > 0) {
				sortStatus = false;
				break;
			}
		}
		assertTrue(sortStatus);
	}

	@Test
	public void testExecuteSearchKeyWord() {
		initialize();
		String[] fieldStr = new String[] { "", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		MemoriCommand mc;
		String[] names = new String[] { "5", "name", "123name", "HELLO", "hello", "blah" };
		for (int i = 0; i < names.length; i++) {
			fieldStr[0] = names[i];
			mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
			calendar.execute(mc, google);
		}

		fieldStr[0] = "name";
		mc = new MemoriCommand(MemoriCommandType.SEARCH, start, end, fieldStr);
		calendar.execute(mc, google);
		calendar.display();
		ArrayList<MemoriEvent> searchedList = calendar.getSearch();
		assertTrue(searchedList.size() == 2);
	}
	
	@Test
	public void testExecuteSearchDates() {
		initialize();
		String[] fieldStr = new String[] { "", "testDescribe", "testPlace" };
		Date start = new Date();
		Date end = new Date();
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		Date[] endDates = new Date[] { new Date(), new Date(222222222222L), new Date(3333333333333L),
				new Date(0), new Date(888888888L) };
		for (int i = 0; i < endDates.length; i++) {
			start = endDates[i];
			end = endDates[i];
			mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
			calendar.execute(mc, google);
		}

		start = new Date(888888888L);
		end = new Date(222222222222L);
		mc = new MemoriCommand(MemoriCommandType.SEARCH, start, end, fieldStr);
		calendar.execute(mc, google);
		calendar.display();
		ArrayList<MemoriEvent> searchedList = calendar.getSearch();
		assertTrue(searchedList.size() == 2);
	}
	
	@Test
	public void testExecuteToggleComplete() {
		initialize();
		String[] fieldStr = new String[] { "test", "testDescribe", "testPlace" };
		Date start = null;
		Date end = null;
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			calendar.execute(mc, google);
		}
		indexes.add(3);
		indexes.add(2);
		indexes.add(5);
		indexes.add(7);
		ArrayList<MemoriEvent> main = calendar.getEvents();
		MemoriEvent latestEvent = main.get(main.size() - 1);
		calendar.display();
		mc = new MemoriCommand(MemoriCommandType.COMPLETE, indexes);
		calendar.execute(mc, google);
		ArrayList<MemoriEvent> searchedList = calendar.getSearch();
		assertTrue(searchedList.get(2).getComplete());
		assertTrue(searchedList.get(1).getComplete());
		assertTrue(searchedList.get(4).getComplete());
		assertTrue(searchedList.get(6).getComplete());
	}

}
