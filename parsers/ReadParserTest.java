//@@author A0108454H
package memori.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReadParserTest {

	@Test
	public final void testReadWithNegativeNumber() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String ReadToTest = "-1";
		ReadParser rp = new ReadParser();
		MemoriCommand get = rp.parse(cmdType,ReadToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithNegativeNumber2() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String ReadToTest = "-1 1";
		ReadParser rp = new ReadParser();
		MemoriCommand get = rp.parse(cmdType,ReadToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithNegativeNumber3() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String ReadToTest = "-1 - 1";
		ReadParser rp = new ReadParser();
		MemoriCommand get = rp.parse(cmdType,ReadToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithNegativeNumber4() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String ReadToTest = " 1-1-1 ";
		ReadParser rp = new ReadParser();
		MemoriCommand get = rp.parse(cmdType,ReadToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithNegativeNumber5() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String ReadToTest = "3-1  4-5-1";
		ReadParser rp = new ReadParser();
		MemoriCommand get = rp.parse(cmdType,ReadToTest);
		assertTrue(get.getIndexes()==null);
	}

}
 