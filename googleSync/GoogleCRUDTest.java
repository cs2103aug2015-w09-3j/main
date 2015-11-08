package memori.googleSync;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import memori.logic.MemoriEvent;
import memori.parsers.DateParser;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;

public class GoogleCRUDTest {
	
	private com.google.api.services.calendar.Calendar googleCalendar = GCalConnect.getCalendarService();
	private GoogleCRUD crud = new GoogleCRUD(googleCalendar);
	private static final String NAME = "test name";
	private static final Date START = DateParser.parseDate("today");
	private static final Date END = DateParser.parseDate("today");
	private static final String LOCATION = "location";
	private static final String DESCRIPTION = "description";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Test
	public void testExecuteAddEvent() {
		MemoriEvent  me = new  MemoriEvent(NAME, START, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		Boolean status  = crud.executeCmd(me, cmd);
		String externalId = me.getExternalCalId();
		MemoriEvent remote = crud.retrieveRemote(me);
		assertTrue(status);
		assertTrue(me.equals(remote));
	
	}
	
	@Test
	public void testNoStart(){
		MemoriEvent  me = new  MemoriEvent(NAME, END, START, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		Boolean status  = crud.executeCmd(me, cmd);
		MemoriEvent remote = crud.retrieveRemote(me);
		assertTrue(me.equals(remote));
		assertTrue(status);
	}
	@Test
	public void testExecuteAddDeadLine() {
		MemoriEvent  me = new  MemoriEvent(NAME, null, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		Boolean status  = crud.executeCmd(me, cmd);
		MemoriEvent remote = crud.retrieveRemote(me);
		assertTrue(status);
		assertTrue(me.equals(remote));
	}
	@Test
	public void testExecuteAddFloat() {
		MemoriEvent  me = new  MemoriEvent(NAME, null, null, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		Boolean status  = crud.executeCmd(me, cmd);
		MemoriEvent remote = crud.retrieveRemote(me);
		String externalIdtoUpdate = me.getExternalCalId();
		assertTrue(status);
		assertTrue(me.equals(remote));
	}
	@Test
	public void testUpdatewithNoEID(){
		MemoriEvent  me = new  MemoriEvent(NAME + "Updated", null, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.UPDATE);
		Boolean status  = crud.executeCmd(me, cmd);
		MemoriEvent remote = crud.retrieveRemote(me);
		assertTrue(status);
		assertTrue(me.equals(remote));
	}
	@Test
	public void testUpdatewithEID(){
		MemoriEvent  me = new  MemoriEvent(NAME, START, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		String externalId = me.getExternalCalId();
		me = new  MemoriEvent(NAME + "Updated", null, END, 0,externalId, LOCATION,DESCRIPTION);
		cmd  = new MemoriCommand(MemoriCommandType.UPDATE);
		Boolean status  = crud.executeCmd(me, cmd);
		MemoriEvent remote = crud.retrieveRemote(me);
		assertTrue(status);
		assertTrue(me.equals(remote));
	}
	@Test
	public void testDeletewtihNoEID(){
		MemoriEvent  me = new  MemoriEvent(NAME, null, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.DELETE);
		Boolean status  = crud.executeCmd(me, cmd);
		assertTrue(status);
	}
	@Test
	public void testDeletewtihEID(){
		MemoriEvent  me = new  MemoriEvent("DELETE", START, END, 0,null, LOCATION,DESCRIPTION);
		MemoriCommand cmd  = new MemoriCommand(MemoriCommandType.ADD);
		crud.executeCmd(me, cmd);
		String externalId = me.getExternalCalId();
		me = new  MemoriEvent(NAME, null, END, 0,externalId, LOCATION,DESCRIPTION);
		cmd  = new MemoriCommand(MemoriCommandType.DELETE);
		Boolean status  = crud.executeCmd(me, cmd);
		assertTrue(status);
	}


}
