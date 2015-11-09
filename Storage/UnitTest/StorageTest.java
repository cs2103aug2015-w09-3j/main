//@@author A0121262X
package memori.Storage.UnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import memori.Storage.MemoriSettings;
import memori.Storage.MemoriStorage;
import memori.logic.MemoriCalendar;
import memori.logic.MemoriEvent;

public class StorageTest {
	private MemoriCalendar testMemoriCalendar;
	private MemoriCalendar testMemoriCalendar2;

	/**
	 * tests if saving and loading works properly by comparing 2 calendars.
	 * 1 of the calendars will be saved with a task, while the 2nd calendar loads the storage.
	 */
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
		
		//compare
		boolean status = true;
		for (int i = 0; i < events.size(); i++) {
			if (!(events.get(i).equals((events2.get(i))))) {
					status = false;
			}
		}
		
		assertEquals(true, status);
	}

}
