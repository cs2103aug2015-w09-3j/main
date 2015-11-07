//@@author A0108454H
package memori.parsers;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class AddParserTest {
	public static final String DATE_FORMAT = "dd MMM yyyy HH:mm E";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	@Test
	public final void add1() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriCommand Expected = new MemoriCommand("");
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-s");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
	}

	@Test
	public final void add2() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		Date StartDate = null;
		Date EndDate = null;
		MemoriCommand Expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
	}

	@Test
	public final void add3() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		Date StartDate = DateParser.parseDate("tmr");
		Date EndDate = null;
		MemoriCommand Expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -stmr -l sengkang -d with friends");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
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
		MemoriCommand Expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -l home -d with family");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
	}
	@Test
	public final void add5() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		Date StartDate = DateParser.parseDate("thurs");
		Date EndDate = DateParser.parseDate("fri");
		MemoriCommand Expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -s thurs -e fri -l sengkang ");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
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
		MemoriCommand Expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -l sengkang -d with friends ");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
	}
	@Test
	public final void add7() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		Date StartDate = DateParser.parseDate("5 nov 9am");
		Date EndDate = DateParser.parseDate("6 nov 10pm");
		MemoriCommand Expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -s 5 nov 9am -e 6 nov 10pm -l sengkang ");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
	}
	@Test
	public final void add8() {
		MemoriCommandType add = MemoriCommandType.ADD;
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		stringField[1] = "sengKang";
		stringField[2] = "with friends";
		Date StartDate = DateParser.parseDate("haha");
		Date EndDate = DateParser.parseDate("haha");
		MemoriCommand Expected = new MemoriCommand("");
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -s haha -e haha -l sengkang -dwith friends");
		System.out.println(Expected.getType()+""+result.getType());
		assertTrue(compareTo(result, Expected));
	}@Test
	public final void add9() {
		MemoriCommandType add = MemoriCommandType.ADD;

		MemoriCommand Expected = new MemoriCommand("");
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n -s -e -l -d");
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
