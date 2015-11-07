package memori.parsers;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;

public class MemoriParserTest {
	public static final String DATE_FORMAT = "dd MMM yyyy HH:mm E";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	
	//when user enters an invalid command
	@Test
	public final void testAdd() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
	
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add234324 -n jayden");
		assertTrue(compareAdd(addResults,expected));
	}
	//when user enters an invalid command
	@Test
	public final void testAdd1() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
	
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add234324 -n jayden -s tmr -e tmr");
		assertTrue(compareAdd(addResults,expected));
	}
	//when user enters an valid add function and valid name field
	@Test
	public final void testAdd2() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		Date StartDate = null;
		Date EndDate = null;
		MemoriCommand expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		MemoriCommand addResults = mp.parse("add -n jayden");
		assertTrue(compareAdd(addResults,expected));
	}
	//when user tries to enter an valid command but with no fields entered
	@Test
	public final void testAdd3() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add");
		assertTrue(compareAdd(addResults,expected));
	}
	//when user tries to enter an valid command but no name is entered
	@Test
	public final void testAdd4() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -s tmr -etmr -lsengkang");
		assertTrue(compareAdd(addResults,expected));
	}
	//when users indicate he wants to add a event with a name but is blank
	@Test
	public final void testAdd5() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n");
		assertTrue(compareAdd(addResults,expected));
	}
	//when the user indicates he wants to add a start date but is blank
	@Test
	public final void testAdd6() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n jayden -s");
		assertTrue(compareAdd(addResults,expected));
	}
	//when the user indicates he wants to add a description but is blank
	@Test
	public final void testAdd7() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n jayden -l sengkang -d");
		assertTrue(compareAdd(addResults,expected));
	}
	//when the user indicates he wants to add a event but never add a name
	@Test
	public final void testAdd8() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n -l sengkang -d with friends");
		assertTrue(compareAdd(addResults,expected));
	}
	@Test
	public final void testAdd9() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n -e tmr -shaha");
		assertTrue(compareAdd(addResults,expected));
	}
	//when the user add a event but ending and starting date is a invalid format
	@Test
	public final void testAdd10() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -njayden -e ahaha -shaha");
		assertTrue(compareAdd(addResults,expected));
	}
	
	//when the user enters an invalid add command
	@Test
	public final void testAdd11() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add 3432432523");
		assertTrue(compareAdd(addResults,expected));
	}
	//when the user wants to add a event but the command is type in caps
	@Test
	public final void testAdd12() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		DateParser dp = new DateParser();
		Date StartDate = dp.parseDate("tmr");
		Date EndDate = dp.parseDate("mon");
		MemoriCommand expected = new MemoriCommand(add,StartDate,EndDate,stringField);
		MemoriCommand addResults = mp.parse("ADD -n jayden -s tmr -e mon");
		assertTrue(compareAdd(addResults,expected));
	}
	//when the user wants to exit the program
	@Test
	public final void testExit() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("exit");
		assertTrue(exitResults.getType().equals(exit));
	}
	//when the user wants to exit the program but was type in caps
	@Test
	public final void testExit1() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("EXIT");
		assertTrue(exitResults.getType().equals(exit));
	}
	//wants to exit but accidently uses add extra character  
	@Test
	public final void testExit2() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("exit 123213");
		assertTrue(exitResults.getType().equals(exit));
	}
	//when the user wants to exit but type in extra characters
	@Test
	public final void testExit3() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("exit       ");
		assertTrue(exitResults.getType().equals(exit));
	}
	@Test
	public final void testExit4() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("exit         123123 ");
		assertTrue(exitResults.getType().equals(exit));
	}
	//when a invalid command is entered
	@Test
	public final void testExit5() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("ExTi");
		assertTrue(exitResults.getType().equals(MemoriCommandType.INVALID));
	}
	//when the user wants to undo but add in extra character
	@Test
	public final void testUndo() {
		MemoriCommandType undo = MemoriCommandType.UNDO;
		MemoriParser mp = new MemoriParser();
		MemoriCommand undoResults = mp.parse("undo 123123");
		assertTrue(undoResults.getType().equals(undo));
	}
	//when the user wants to undo but is not in full caps 
	@Test
	public final void testUndo1() {
		MemoriCommandType undo = MemoriCommandType.UNDO;
		MemoriParser mp = new MemoriParser();
		MemoriCommand undoResults = mp.parse("Undo");
		assertTrue(undoResults.getType().equals(undo));
	}
	//when the user wants to undo but is not in full caps
	@Test
	public final void testUndo2() {
		MemoriCommandType undo = MemoriCommandType.UNDO;
		MemoriParser mp = new MemoriParser();
		MemoriCommand undoResults = mp.parse("undo");
		assertTrue(undoResults.getType().equals(undo));
	}
	//when the user wants to delete 1-10
	@Test
	public final void testDelete() {
		MemoriCommandType delete = MemoriCommandType.DELETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer>expectedIndex = new ArrayList<Integer>();
		addIndex(1,10,expectedIndex);
		MemoriCommand expected = new MemoriCommand(delete,expectedIndex);
		MemoriCommand results = mp.parse("delete 1 -10");
		assertTrue(compareIndexes(results,expected));
	}
	//when the users wants to delete line 10 - 50
	@Test
	public final void testDelete1() {
		MemoriCommandType delete = MemoriCommandType.DELETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer>expectedIndex = new ArrayList<Integer>();
		addIndex(10,50,expectedIndex);
		MemoriCommand expected = new MemoriCommand(delete,expectedIndex);
		MemoriCommand results = mp.parse("delete 10 -50");
		assertTrue(compareIndexes(results,expected));
	}
	//when the users wants to delete line 50 - 10
	@Test
	public final void testDelete2() {
		MemoriCommandType delete = MemoriCommandType.DELETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer>expectedIndex = new ArrayList<Integer>();
		addIndex(10,50,expectedIndex);
		Collections.sort(expectedIndex);
		MemoriCommand expected = new MemoriCommand(delete,expectedIndex);
		MemoriCommand results = mp.parse("delete 50 -10");
		assertTrue(compareIndexes(results,expected));
	}
	//when the users wants to delete line 50 - 10-1
		@Test
		public final void testDelete3() {
			MemoriCommandType delete = MemoriCommandType.DELETE;
			MemoriParser mp = new MemoriParser();
			MemoriCommand expected = new MemoriCommand("");
			MemoriCommand results = mp.parse("delete 50 -10 -1");
			assertTrue(results.getType().equals(MemoriCommandType.INVALID));
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
	public boolean compareAdd(MemoriCommand results, MemoriCommand expected) {

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
	
	public boolean compareSort(MemoriCommand results, MemoriCommand expected) {
		
		Boolean[] resultsField = results.getMemoriField();
		Boolean[] expectedField = results.getMemoriField();
		if ((expectedField == null)
				&& (resultsField == null)) {
			return true;
		}
		
		for(int i = 0; i < resultsField.length; i++){
			if(resultsField[i]!=expectedField[i]){
				return false;
			}
		}
	
		return true;
	}
	
	private boolean compareIndexes(MemoriCommand results,MemoriCommand expected){
		
		MemoriCommandType resultsCommand = results.getType();
		MemoriCommandType expectedCommand = expected.getType();
		ArrayList<Integer> resultsIndex = results.getIndexes();
		ArrayList<Integer> expectedIndex = results.getIndexes();
		if(!resultsCommand.equals(expectedCommand)){
			return false;
		}
		if(resultsIndex.size()
				!=expectedIndex.size()){
			return false;
		}
		Collections.sort(resultsIndex);
		Collections.sort(expectedIndex);
		for(int index = 0 ; index < resultsIndex.size(); index++){
			if(resultsIndex.get(index)!=expectedIndex.get(index)){
				return false;
			}
		}	
		return true;
	}
	public void addIndex(int lower,int upper,ArrayList<Integer>ExpectedIndex){
		for(;lower<upper+1;lower++){
			ExpectedIndex.add(lower);
		}
	}




}

