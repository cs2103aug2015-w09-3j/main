//@@author A0108454H
package memori.parsers;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class AddParserTest {
	public static final String DATE_FORMAT = "dd MMM yyyy HH:mm E";
	@Test
	public final void add1() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriCommand Expected = new MemoriCommand("");
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-s");
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
		assertTrue(compareTo(result, Expected));
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
		MemoriCommand Expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, " -stmr -l sengkang -d with friends");
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
		MemoriCommand result = ap.parse(add, " -l home -d with family");
		assertTrue(compareTo(result, Expected));
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
		MemoriCommand Expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		AddParser ap = new AddParser();
		MemoriCommand result = ap.parse(add, "-n jayden -s thurs -e fri -l sengkang ");
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
		assertTrue(compareTo(result, Expected));
	}
	/**
	 * compare the start date , end date and all the string fields
	 * of the expected and and the results of a add parser.
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
		System.out.println(resultStartDate);
		System.out.println(expectedStartDate);
		System.out.println(resultEndDate);
		System.out.println(expectedEndDate);
		if(!resultsCommand.equals(expectedCommand)){
			return false;
		}
		if ((expectedArgs == null)
				&& (resultsArgs == null)) {
			return true;
		}else {
			for(int i = 0; i < resultsArgs.length;i++){
				if(resultsArgs[i].equals(expectedArgs[i])!=true){
					System.out.println("herere");
					return false;
				}
			}
		}
		
		if((expectedStartDate!=null)&&(resultStartDate!=null)){
			if(!expectedStartDate.equals(resultStartDate)){
				System.out.println(expectedStartDate.compareTo(resultStartDate));
				return false;
			}
		}
		
		if((expectedEndDate!=null)&&(resultEndDate!=null)){
			if(expectedEndDate.compareTo(resultEndDate) != 0){
				System.out.println("herherhere");
				return false;
			}
		}	
		return true;
	}
	
}
