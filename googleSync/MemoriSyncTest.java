//@@author A0098038W
package memori.googleSync;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import memori.logic.MemoriCalendar;
import memori.logic.MemoriEvent;
import memori.parsers.DateParser;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;
import memori.ui.MemoriUI;

public class MemoriSyncTest {
	private static MemoriSync ms = new MemoriSync();
	private static MemoriCalendar calendar = new MemoriCalendar();
	private static final String NAME = "test name";
	private static final Date START = DateParser.parseDate("today");
	private static final Date END = DateParser.parseDate("today");
	private static final String LOCATION = "location";
	private static final String DESCRIPTION = "description";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ms.initialize(calendar);
		calendar.initialize();
		calendar.display();
	}

	@Test
	public void testAddNewRequestAdd() {
		MemoriEvent  me = new  MemoriEvent(NAME, START, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		ms.addNewRequest(me, cmd);
		MemoriEvent remote = ms.retrieveRemote(me);
		assertTrue(me.equals(remote));
	}
	
	@Test
	public void testAddNewRequestDelete() {
		MemoriEvent  me = new  MemoriEvent("DELETE", START, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		ms.addNewRequest(me, cmd);
		cmd  = new MemoriCommand(MemoriCommandType.DELETE);
		ms.addNewRequest(me, cmd);
		ArrayList<MemoriEvent> afterDelete = ms.retrieveAll();
		assertTrue(!afterDelete.contains(me));
	}
	
	@Test
	public void testAddNewRequestUpdate() {
		MemoriEvent  me = new  MemoriEvent(NAME, START, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		ms.addNewRequest(me, cmd);
		String externalID = me.getExternalCalId();
		MemoriEvent me2  = new MemoriEvent(NAME + "UPDATED", START, END, 0,externalID, LOCATION,DESCRIPTION);
		cmd  = new MemoriCommand(MemoriCommandType.UPDATE);
		ms.addNewRequest(me2, cmd);
		MemoriEvent remote = ms.retrieveRemote(me);
		assertTrue(me2.equals(remote));
	}
	
}
