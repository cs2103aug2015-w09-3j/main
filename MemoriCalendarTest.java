package memori;

import static org.junit.Assert.*;

import org.junit.Test;

public class MemoriCalendarTest {

	MemoriCalendar mCalendar = new MemoriCalendar();
	MemoriCommand ADD;
	String mAdd = mCalendar.add(ADD);
	String testAdd = "Added.\n";
	String mExecute = mCalendar.execute(ADD);
	String testExecute = "Added.\n";
	
	@Test
	public void testAdd() {
		System.out.println("@Test add(): " + mAdd + " = " + testAdd);
		assertEquals(mAdd, testAdd);
	}

	@Test
	public void testAddRemote() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisplay() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecute() {
		System.out.println("@Test execute(): " + mExecute + " = " + testExecute);
		assertEquals(mExecute, testExecute);
	}

}
