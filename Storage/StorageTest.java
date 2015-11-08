//@@author A0121262X
package memori.Storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


import memori.logic.MemoriCalendar;
import memori.logic.MemoriEvent;

public class StorageTest {
	private MemoriCalendar testMemoriCalendar;
	private MemoriCalendar testMemoriCalendar2;

	@Test
	public void TestSaveAndLoadCalendar() {
		MemoriSettings testSettings = MemoriSettings.getInstance();
		MemoriStorage testStorage = MemoriStorage.getInstance();
		testSettings = testStorage.loadSettings();
		testMemoriCalendar = testStorage.loadCalendar();
		
		ArrayList<MemoriEvent> events = testMemoriCalendar.getEvents();
		MemoriEvent testEvent = new MemoriEvent("test",null,null,0,null,"","");
		events.add(testEvent);
		testStorage.saveCalendar(testMemoriCalendar);
		
		testMemoriCalendar2 = testStorage.loadCalendar();
		ArrayList<MemoriEvent> events2 = testMemoriCalendar2.getEvents();
		
		boolean status = true;
		for (int i = 0; i < events.size(); i++) {
			if (!(events.get(i).equals((events2.get(i))))) {
					status = false;
			}
		}
		
		assertEquals(true, status);
	}

}
