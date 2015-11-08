//@@author A0108454H
package memori.parsers.parserTesting;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import memori.parsers.DateParser;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;
import memori.parsers.UpdateParser;

import org.junit.Test;

public class UpdateParserTest {
	public static final String DATE_FORMAT = "dd MMM yyyy HH:mm E";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			DATE_FORMAT);

	// test case when users did not indicate a index
	@Test
	public final void update1() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "-s");
		assertTrue(compareTo(result, expected));
	}

	// test case when user indicates that he would like to update a certain line
	// but did not enter any fields
	@Test
	public final void update2() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1");
		assertTrue(compareTo(result, expected));
	}

	// when users forgot to indicate which index he would like to update
	@Test
	public final void update3() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "-n jayden -s tmr -e tmr");
		assertTrue(compareTo(result, expected));
	}

	// a passable update case
	@Test
	public final void update4() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		Date StartDate = DateParser.parseDate("tmr");
		Date EndDate = DateParser.parseDate("tmr");
		Boolean[] FilledFields = new Boolean[5];
		for (int i = 0; i < FilledFields.length; i++) {
			FilledFields[i] = false;
		}
		FilledFields[0] = true;
		FilledFields[1] = true;
		FilledFields[2] = true;
		MemoriCommand expected = new MemoriCommand(update, StartDate, EndDate,
				stringField, 1, FilledFields);
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1 -n jayden -s tmr -e tmr");
		assertTrue(compareTo(result, expected));
	}

	// when the users never indicate a accepted field to update
	@Test
	public final void update5() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1 -q -o ");
		assertTrue(compareTo(result, expected));
	}

	// when the users dates are not of an excepted format
	@Test
	public final void update6() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1 -elove -shaha ");
		assertTrue(compareTo(result, expected));
	}

	// when the users dates are empty
	@Test
	public final void update7() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1 -e -s ");
		assertTrue(compareTo(result, expected));
	}

	// when the index is not excepted
	@Test
	public final void update8() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1asdfdsfds -e -s ");
		assertTrue(compareTo(result, expected));
	}

	//allowed update 
	@Test
	public final void update9() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		Date StartDate = DateParser.parseDate("");
		Date EndDate = DateParser.parseDate("");
		Boolean[] FilledFields = new Boolean[5];
		for (int i = 0; i < FilledFields.length; i++) {
			FilledFields[i] = false;
		}
		FilledFields[0] = true;
		FilledFields[3] = true;
		FilledFields[4] = true;
		MemoriCommand expected = new MemoriCommand(update, StartDate, EndDate,
				stringField, 1, FilledFields);
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1 -njayden -d -l");
		assertTrue(compareTo(result, expected));
	}

	/**
	 * compare the start date in string format , end date in string
	 * format,FilledFields, index to update and all the string fields of the
	 * expected and the results of updateparser.
	 * 
	 * @param results
	 * @param expected
	 * @return
	 */
	public boolean compareTo(MemoriCommand results, MemoriCommand expected) {

		MemoriCommandType resultsCommand = results.getType();
		MemoriCommandType expectedCommand = expected.getType();
		String[] resultsArgs = results.getCommandArgs();
		String[] expectedArgs = results.getCommandArgs();
		Date expectedStartDate = expected.getStart();
		Date resultStartDate = results.getStart();
		Date expectedEndDate = expected.getEnd();
		Date resultEndDate = results.getEnd();
		Boolean[] resultsField = results.getMemoriField();
		Boolean[] expectedField = results.getMemoriField();
		int expectedIndex = expected.getIndex();
		int resultsIndex = results.getIndex();
		// if both is an invalid command straight return true
		if ((expectedCommand.equals(MemoriCommandType.INVALID))
				&& (resultsCommand.equals(MemoriCommandType.INVALID))) {

			return true;
		}
		// if memori command is not the same return false
		if (!resultsCommand.equals(expectedCommand)) {

			return false;
		}
		if (resultsIndex != expectedIndex) {

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

		if (((expectedStartDate != null) && (resultStartDate != null))
				|| ((expectedStartDate != null) && (resultStartDate != null))) {

			if (!DATE_FORMATTER.format(expectedStartDate).equals(
					DATE_FORMATTER.format(resultStartDate))) {

				return false;
			}
		}

		if (((expectedEndDate != null) && (resultEndDate != null))
				|| ((expectedEndDate != null) && (resultEndDate != null))) {

			if (!DATE_FORMATTER.format(expectedEndDate).equals(
					DATE_FORMATTER.format(resultEndDate))) {

				return false;
			}
		}

		for (int i = 0; i < resultsField.length; i++) {
			if (resultsField[i] != expectedField[i]) {
				return false;
			}
		}
		return true;
	}

}
