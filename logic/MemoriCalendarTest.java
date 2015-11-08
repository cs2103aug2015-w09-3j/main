package memori.logic;

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
	private static final String MESSAGE_CHANGE_SEARCH= "Your search conditions has been changed\n";
	
	private MemoriCalendar calendar = new MemoriCalendar();
	
	private MemoriSync google = new MemoriSync();
	
	private ArrayList<MemoriEvent> searchedList = new ArrayList<MemoriEvent>();;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar = new MemoriCalendar();
		google = new MemoriSync();
		calendar.initialize();
		google.initialize(ui, calendar);
		calendar.display();
	}

	@Test
	public void testExecuteRead() {
		//MemoriCalendar calendar= new MemoriCalendar();
		//MemoriSync google = new MemoriSync();
		initialize();
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		indexes.add(1);
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.READ, indexes);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains("Reading: 1"));
	}
	
	@Test
	public void testExecuteAdd(){
		String[] fieldStr = new String[] {"test", "testDescribe", "testPlace"};
		Date start = null;
		Date end = null;
		calendar.initialize();
		google.initialize(new MemoriUI(), calendar);
		calendar.display();
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.ADD, start, end, fieldStr);
		String observed = calendar.execute(mc, google);
		assertTrue(observed.contains(MESSAGE_ADD));
	}
	
	@Test
	public void testExecuteDelete(){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		calendar.initialize();
		google.initialize(new MemoriUI(), calendar);
		calendar.display();
		indexes.add(1);
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.DELETE, indexes);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains(MESSAGE_DELETE));
	}
	
	@Test
	public void testExecuteUpdate(){
		String[] fieldStr = new String[] {"test", "testDescribe", "testPlace"};
		Date start = null;
		Date end = null;
		int index = 1;
		Boolean[] memoriField = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
				, Boolean.FALSE};
		//ArrayList<Integer> indexes = new ArrayList<Integer>();
		
		calendar.initialize();
		google.initialize(new MemoriUI(), calendar);
		calendar.display();
		//indexes.add(1);
		
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.UPDATE, start, end, fieldStr, index,
				memoriField);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains("Updated Event 1"));
	}
	
	@Test
	public void testExecuteSortName(){
		Boolean[] memoriField = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
				, Boolean.FALSE};
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains(MESSAGE_SORT));
	}
	
	@Test
	public void testExecuteSortStart(){
		Boolean[] memoriField = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE
				, Boolean.FALSE};
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains(MESSAGE_SORT));
	}
	
	@Test
	public void testExecuteSortEnd(){
		Boolean[] memoriField = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE
				, Boolean.FALSE};
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains(MESSAGE_SORT));
	}
	
	@Test
	public void testExecuteSortDescription(){
		Boolean[] memoriField = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE
				, Boolean.FALSE};
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains(MESSAGE_SORT));
	}
	
	@Test
	public void testExecuteSortLocation(){
		Boolean[] memoriField = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
				, Boolean.TRUE};
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.SORT, memoriField);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains(MESSAGE_SORT));
	}
	
	@Test
	public void testExecuteSearch(){
		String[] fieldStr = new String[] {"test", "testDescribe", "testPlace"};
		Date start = null;
		Date end = null;
		calendar.initialize();
		google.initialize(new MemoriUI(), calendar);
		calendar.display();
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.SEARCH, start, end, fieldStr);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains("Your search conditions has been changed."));
	}
	
	@Test
	public void testExecuteToggleComplete(){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		calendar.initialize();
		google.initialize(new MemoriUI(), calendar);
		calendar.display();
		indexes.add(1);
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.OPEN, indexes);
		String observed = calendar.execute(mc,google);
		assertTrue(observed.contains("Tasks have been reopened"));
	}

}
