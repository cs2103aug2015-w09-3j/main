//@@author A0108454H
package memori.parsers.parserTesting;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

import java.util.Date;

import memori.parsers.AddParser;
import memori.parsers.DateParser;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;

import org.junit.Test;

public class AddParserTest {
	public static final String DATE_FORMAT = "dd MMM yyyy HH:mm E";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	@Test
	public final void add1() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriCommand expected = new MemoriCommand("");
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-s");
		
		assertTrue(compareTo(result, expected));
	}

	@Test
	public final void add2() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		Date StartDate = null;
		Date EndDate = null;
		MemoriCommand expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden");
		
		assertTrue(compareTo(result, expected));
	}

	@Test
	public final void add3() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		DateParser dp = new DateParser();
		Date StartDate = dp.parseDate("tmr");
		Date EndDate = null;
		MemoriCommand expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -stmr -l sengkang -d with friends");
		
		assertTrue(compareTo(result, expected));
	}
	@Test
	public final void add4() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "home";
		stringField[2] = "with family";
		Date StartDate = null;
		Date EndDate = null;
		MemoriCommand expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -l home -d with family");
		
		assertTrue(compareTo(result, expected));
	}
	@Test
	public final void add5() {
		MemoriCommandType add = MemoriCommandType.ADD;
		DateParser dp = new DateParser();
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		Date StartDate = dp.parseDate("thurs");
		Date EndDate = dp.parseDate("fri");
		MemoriCommand expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -s thurs -e fri -l sengkang ");
		
		assertTrue(compareTo(result, expected));
	}
	@Test
	public final void add6() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		Date StartDate = null;
		Date EndDate = null;
		MemoriCommand expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -l sengkang -d with friends ");
		
		assertTrue(compareTo(result, expected));
	}
	@Test
	public final void add7() {
		MemoriCommandType add = MemoriCommandType.ADD;
		DateParser dp = new DateParser();
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		Date StartDate = dp.parseDate("5 nov 9am");
		Date EndDate = dp.parseDate("6 nov 10pm");
		MemoriCommand expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -s 5 nov 9am -e 6 nov 10pm -l sengkang ");
		
		assertTrue(compareTo(result, expected));
	}
	@Test
	public final void add8() {
		MemoriCommandType add = MemoriCommandType.ADD;
		DateParser dp = new DateParser();
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		Date StartDate = dp.parseDate("haha");
		Date EndDate = dp.parseDate("haha");
		MemoriCommand expected = new MemoriCommand("");
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -s haha -e haha -l sengkang -dwith friends");
		
		assertTrue(compareTo(result, expected));
	}@Test
	public final void add9() {
		MemoriCommandType add = MemoriCommandType.ADD;
		DateParser dp = new DateParser();
		MemoriCommand expected = new MemoriCommand("");
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n -s -e -l -d");
		
		assertTrue(compareTo(result, expected));
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

		MemoriCommandType resultsCommand = results.getType();
		MemoriCommandType expectedCommand = expected.getType();
		String[] resultsArgs = results.getCommandArgs();
		String[] expectedArgs = results.getCommandArgs();
		Date expectedStartDate = expected.getStart();
		Date resultStartDate = results.getStart();
		Date expectedEndDate = expected.getEnd();
		Date resultEndDate = results.getEnd();
		
		//if both is an invalid command straight return true
		if(expectedCommand.equals(MemoriCommandType.INVALID)
				&&resultsCommand.equals(MemoriCommandType.INVALID)){
			
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
		// if either expectedStartDate is null but result start Date is not null
		//viseversa return false
		if (((expectedStartDate != null) && (resultStartDate == null))
				|| ((expectedStartDate == null) && (resultStartDate != null))) {
			
			return false;
		}
		// if either expectedEndDate is null but resultEndDate is not null
		//viseversa return false
		if (((expectedEndDate != null) && (resultEndDate == null))
				|| ((expectedEndDate == null) && (resultEndDate != null))) {
	
			return false;
		}
		//if both resultsStartDate and expectedStartDate is not null
		//compare the start dates
		if(((expectedStartDate != null) && (resultStartDate != null))
				|| ((expectedStartDate != null) && (resultStartDate != null))){
			
			if (!DATE_FORMATTER.format(expectedStartDate).equals(
				DATE_FORMATTER.format(resultStartDate))) {
				
				return false;
			}
		}
		//if both resultsEndDate and expectedEndDate is not null
		//compare the end dates
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
