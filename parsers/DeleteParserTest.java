//@@author A0108454H
package memori.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeleteParserTest {

	@Test
	public final void testDeleteWithNegativeNumber() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber2() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1 1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber3() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1 - 1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber4() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = " 1-1-1 ";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber5() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "3-1  4-5-1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}

}