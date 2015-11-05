package memori.logic;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import memori.googleSync.MemoriSync;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;

public class MemoriCalendarTest {

	@BeforeClass
	public static void setUpBforeClass() throws Exception {
		
	}

	@Test
	public void testExecuteRead() {
		MemoriCalendar calendar= new MemoriCalendar();
		MemoriSync google = new MemoriSync();
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		indexes.add(1);
		MemoriCommand mc = new MemoriCommand(MemoriCommandType.READ, indexes);
		String expected= "dsadsadasa";
		String observed = calendar.execute(mc,google);
		assertTrue(expected.equals(observed));
		
		
	}
	public void testExecute(){
		
	}

}
