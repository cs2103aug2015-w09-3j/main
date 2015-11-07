//@@author A0108454H
package memori.parsers;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class UpdateParserTest {
	public static final String DATE_FORMAT = "dd MMM yyyy HH:mm E";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			DATE_FORMAT);

	// test case when users did not indicate a index
	@Test
	public final void update1() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "-s");
		assertTrue(compareTo(result, Expected));
	}

	// test case when user indicates that he would like to update a certain line
	// but did not enter any fields
	@Test
	public final void update2() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1");
		assertTrue(compareTo(result, Expected));
	}

	// when users forgot to indicate which index he would like to update
	@Test
	public final void update3() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "-n jayden -s tmr -e tmr");
		assertTrue(compareTo(result, Expected));
	}

	//
	@Test
	public final void update4() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1 -n jayden -s tmr -e tmr");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
	}

	/**
	 * compare the start date in string format , end date in string format and
	 * all the string fields of the expected and and the results of a add
	 * parser.
	 * 
	 * @param results
	 * @param expected
	 * @return
	 */
	public boolean compareTo(MemoriCommand results, MemoriCommand expected) {

		MemoriCommandType resultsCommand = expected.getType();
		MemoriCommandType expectedCommand = expected.getType();
		String[] resultsArgs = results.getCommandArgs();
		String[] expectedArgs = results.getCommandArgs();
		Date expectedStartDate = expected.getStart();
		Date resultStartDate = results.getStart();
		Date expectedEndDate = expected.getEnd();
		Date resultEndDate = results.getEnd();
		System.out.println(expectedCommand+""+resultsCommand);
		//if both is an invalid command straight return true
		if((expectedCommand.equals(MemoriCommandType.INVALID))
				&&(resultsCommand.equals(MemoriCommandType.INVALID))){
			
			
			return true;
		}
		// if memori command is not the same return false
		if (!resultsCommand.equals(expectedCommand)) {
			
			return false;
		}
		// if string fields of name , description location is not the same
		// return false
		for (int i = 0; i < resultsArgs.length; i++) {
			if (resultsArgs[i].equals(expectedArgs[i]) != true) {
				
				return false;
			}
		}

		if (((expectedStartDate != null) && (resultStartDate == null))
				|| ((expectedStartDate == null) && (resultStartDate != null))) {
			
			return false;
		}

		if (((expectedEndDate != null) && (resultEndDate == null))
				|| ((expectedEndDate == null) && (resultEndDate != null))) {
	
			return false;
		}
		
		if(((expectedStartDate != null) && (resultStartDate != null))
				|| ((expectedStartDate != null) && (resultStartDate != null))){
			
			if (!DATE_FORMATTER.format(expectedStartDate).equals(
				DATE_FORMATTER.format(resultStartDate))) {
				
				return false;
			}
		}
		
		if(((expectedEndDate != null) && (resultEndDate != null))
				|| ((expectedEndDate != null) && (resultEndDate != null))){
			
			if (!DATE_FORMATTER.format(expectedEndDate).equals(
				DATE_FORMATTER.format(resultEndDate))) {
				
				return false;
			}
		}

		return true;
	}
	
}
