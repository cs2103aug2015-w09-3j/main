package memori.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

public class CompleteParserTest {

	@Test
	public final void testCompleteWithNegativeNumber() {
		MemoriCommandType cmdType = MemoriCommandType.COMPLETE;
		String CompleteToTest = "-1";
		CompleteParser cp = new CompleteParser();
		MemoriCommand get = cp.parse(cmdType,CompleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testCompleteWithNegativeNumber2() {
		MemoriCommandType cmdType = MemoriCommandType.COMPLETE;
		String CompleteToTest = "-1 1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,CompleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testCompleteWithNegativeNumber3() {
		MemoriCommandType cmdType = MemoriCommandType.COMPLETE;
		String CompleteToTest = "-1 - 1";
		CompleteParser cp = new CompleteParser();
		MemoriCommand get = cp.parse(cmdType,CompleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testCompleteWithNegativeNumber4() {
		MemoriCommandType cmdType = MemoriCommandType.COMPLETE;
		String CompleteToTest = " 1-1-1 ";
		DeleteParser cp = new DeleteParser();
		MemoriCommand get = cp.parse(cmdType,CompleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testCompleteWithNegativeNumber5() {
		MemoriCommandType cmdType = MemoriCommandType.COMPLETE;
		String CompleteToTest = "3-1  4-5-1";
		CompleteParser dp = new CompleteParser();
		MemoriCommand get = dp.parse(cmdType,CompleteToTest);
		assertTrue(get.getIndexes()==null);
	}


}
