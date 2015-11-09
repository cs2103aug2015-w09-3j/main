package memori.parsers.UnitTest;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import memori.parsers.DateParser;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;
import memori.parsers.MemoriParser;

import org.junit.Test;

public class MemoriParserTest {
	public static final String DATE_FORMAT = "dd MMM yyyy HH:mm E";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			DATE_FORMAT);

	// when user enters an invalid command
	@Test
	public final void testInvalid() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("43543254325earewr");
		assertTrue(compareAdd(addResults, expected));
	}

	// when user enters an invalid command
	@Test
	public final void testInvalid1() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("43543254325earewr  -n -s");
		assertTrue(compareAdd(addResults, expected));
	}
	// when user enters an invalid command
	@Test
	public final void testAdd() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add234324 -n jayden");
		assertTrue(compareAdd(addResults, expected));
	}

	// when user enters an invalid command
	@Test
	public final void testAdd1() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp
				.parse("add234324 -n jayden -s tmr -e tmr");
		assertTrue(compareAdd(addResults, expected));
	}

	// when user enters an valid add function and valid name field
	@Test
	public final void testAdd2() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		String[] stringField = new String[3];
		stringField[0] = "jayden";
		Date StartDate = null;
		Date EndDate = null;
		MemoriCommand expected = new MemoriCommand(add, StartDate, EndDate,
				stringField);
		MemoriCommand addResults = mp.parse("add -n jayden");
		assertTrue(compareAdd(addResults, expected));
	}

	// when user tries to enter an valid command but with no fields entered
	@Test
	public final void testAdd3() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add");
		assertTrue(compareAdd(addResults, expected));
	}

	// when user tries to enter an valid command but no name is entered
	@Test
	public final void testAdd4() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -s tmr -etmr -lsengkang");
		assertTrue(compareAdd(addResults, expected));
	}

	// when users indicate he wants to add a event with a name but is blank
	@Test
	public final void testAdd5() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n");
		assertTrue(compareAdd(addResults, expected));
	}

	// when the user indicates he wants to add a start date but is blank
	@Test
	public final void testAdd6() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n jayden -s");
		assertTrue(compareAdd(addResults, expected));
	}

	// when the user indicates he wants to add a description but is blank
	@Test
	public final void testAdd7() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n jayden -l sengkang -d");
		assertTrue(compareAdd(addResults, expected));
	}

	// when the user indicates he wants to add a event but never add a name
	@Test
	public final void testAdd8() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp
				.parse("add -n -l sengkang -d with friends");
		assertTrue(compareAdd(addResults, expected));
	}

	@Test
	public final void testAdd9() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -n -e tmr -shaha");
		assertTrue(compareAdd(addResults, expected));
	}

	// when the user add a event but ending and starting date is a invalid
	// format
	@Test
	public final void testAdd10() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add -njayden -e ahaha -shaha");
		assertTrue(compareAdd(addResults, expected));
	}

	// when the user enters an invalid add command
	@Test
	public final void testAdd11() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand addResults = mp.parse("add 3432432523");
		assertTrue(compareAdd(addResults, expected));
	}

	// when the user wants to add a event but the command is type in caps
	@Test
	public final void testAdd12() {
		MemoriCommandType add = MemoriCommandType.ADD;
		MemoriParser mp = new MemoriParser();
		String[] stringField = new String[3];
		stringField[0] = "jayden";

		Date StartDate = DateParser.parseDate("tmr");
		Date EndDate = DateParser.parseDate("mon");
		MemoriCommand expected = new MemoriCommand(add, StartDate, EndDate,
				stringField);
		MemoriCommand addResults = mp.parse("ADD -n jayden -s tmr -e mon");
		assertTrue(compareAdd(addResults, expected));
	}

	// when the user wants to exit the program
	@Test
	public final void testExit() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("exit");
		assertTrue(exitResults.getType().equals(exit));
	}

	// when the user wants to exit the program but was type in caps
	@Test
	public final void testExit1() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("EXIT");
		assertTrue(exitResults.getType().equals(exit));
	}

	// wants to exit but accidently uses add extra character
	@Test
	public final void testExit2() {
		MemoriCommandType exit = MemoriCommandType.EXIT;
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("exit 123213");
		assertTrue(exitResults.getType().equals(exit));
	}

	// when the user wants to exit but type in extra characters
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

	// when a invalid command is entered
	@Test
	public final void testExit5() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand exitResults = mp.parse("ExTi");
		assertTrue(exitResults.getType().equals(MemoriCommandType.INVALID));
	}

	// when the user wants to undo but add in extra character
	@Test
	public final void testUndo() {
		MemoriCommandType undo = MemoriCommandType.UNDO;
		MemoriParser mp = new MemoriParser();
		MemoriCommand undoResults = mp.parse("undo 123123");
		assertTrue(undoResults.getType().equals(undo));
	}

	// when the user wants to undo but is not in full caps
	@Test
	public final void testUndo1() {
		MemoriCommandType undo = MemoriCommandType.UNDO;
		MemoriParser mp = new MemoriParser();
		MemoriCommand undoResults = mp.parse("Undo");
		assertTrue(undoResults.getType().equals(undo));
	}

	// when the user wants to undo but is not in full caps
	@Test
	public final void testUndo2() {
		MemoriCommandType undo = MemoriCommandType.UNDO;
		MemoriParser mp = new MemoriParser();
		MemoriCommand undoResults = mp.parse("undo");
		assertTrue(undoResults.getType().equals(undo));
	}

	// when the users wants to complete line 10 - 50
	@Test
	public final void testComplete1() {
		MemoriCommandType complete = MemoriCommandType.COMPLETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		addIndex(10, 50, expectedIndex);
		MemoriCommand expected = new MemoriCommand(complete, expectedIndex);
		MemoriCommand results = mp.parse("complete 10 -50");
		assertTrue(compareIndexes(results, expected));
	}

	// when the users wants to complete line 50 - 10
	@Test
	public final void testComplete2() {
		MemoriCommandType complete = MemoriCommandType.COMPLETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		addIndex(10, 50, expectedIndex);
		Collections.sort(expectedIndex);
		MemoriCommand expected = new MemoriCommand(complete, expectedIndex);
		MemoriCommand results = mp.parse("complete 50 -10");
		assertTrue(compareIndexes(results, expected));
	}

	// when the users wants to complete line 50 - 10-1
	@Test
	public final void testComplete3() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete 50 -10 -1");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to complete line 50 - 10 1-40
	@Test
	public final void testComplete4() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete 50 -10 1-40");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to complete a negative number
	@Test
	public final void testComplete5() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete -1");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to complete a negative number
	@Test
	public final void testComplete6() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete -1 - 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete a negative number
	@Test
	public final void testComplete7() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete -1 -- 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete a negative number
	@Test
	public final void testComplete8() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete -1 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete a negative number
	@Test
	public final void testComplete9() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete 20 -10 40");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete something invalid
	@Test
	public final void testComplete10() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete 20  40-");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete something invalid
	@Test
	public final void testComplete11() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete 20  40-10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete but is not in a line
	@Test
	public final void testComplete12() {

		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("complete 20  40-10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to line 30 20 40
	@Test
	public final void testComplete13() {
		MemoriCommandType complete = MemoriCommandType.COMPLETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		expectedIndex.add(20);
		expectedIndex.add(30);
		expectedIndex.add(40);
		MemoriCommand expected = new MemoriCommand(complete, expectedIndex);
		MemoriCommand results = mp.parse("complete 30 20 40");
		assertTrue(compareIndexes(results, expected));
	}

	// when the users wants to delete line 10 - 50
	@Test
	public final void testDelete1() {
		MemoriCommandType delete = MemoriCommandType.DELETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		addIndex(10, 50, expectedIndex);
		MemoriCommand expected = new MemoriCommand(delete, expectedIndex);
		MemoriCommand results = mp.parse("delete 10 -50");
		assertTrue(compareIndexes(results, expected));
	}

	// when the users wants to delete line 50 - 10
	@Test
	public final void testDelete2() {
		MemoriCommandType delete = MemoriCommandType.DELETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		addIndex(10, 50, expectedIndex);
		Collections.sort(expectedIndex);
		MemoriCommand expected = new MemoriCommand(delete, expectedIndex);
		MemoriCommand results = mp.parse("delete 50 -10");
		assertTrue(compareIndexes(results, expected));
	}

	// when the users wants to delete line 50 - 10-1
	@Test
	public final void testDelete3() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete 50 -10 -1");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete line 50 - 10 1-40
	@Test
	public final void testDelete4() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete 50 -10 1-40");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete a negative number
	@Test
	public final void testDelete5() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete -1");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete a negative number
	@Test
	public final void testDelete6() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete -1 - 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete a negative number
	@Test
	public final void testDelete7() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete -1 -- 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete a negative number
	@Test
	public final void testDelete8() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete -1 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete a negative number
	@Test
	public final void testDelete9() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete 20 -10 40");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete something invalid
	@Test
	public final void testDelete10() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete 20  40-");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete something invalid
	@Test
	public final void testDelete11() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete 20  40-10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete but is not in a line
	@Test
	public final void testDelete12() {

		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("delete 20  40-10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to line 30 20 40
	@Test
	public final void testDelete13() {
		MemoriCommandType delete = MemoriCommandType.DELETE;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		expectedIndex.add(20);
		expectedIndex.add(30);
		expectedIndex.add(40);
		MemoriCommand expected = new MemoriCommand(delete, expectedIndex);
		MemoriCommand results = mp.parse("delete 30 20 40");
		assertTrue(compareIndexes(results, expected));
	}

	// when the users wants to read line 10 - 50
	@Test
	public final void testRead1() {
		MemoriCommandType Read = MemoriCommandType.READ;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		addIndex(10, 50, expectedIndex);
		MemoriCommand expected = new MemoriCommand(Read, expectedIndex);
		MemoriCommand results = mp.parse("Read 10 -50");
		assertTrue(compareIndexes(results, expected));
	}

	// when the users wants to sort line 50 - 10
	@Test
	public final void testRead2() {
		MemoriCommandType read = MemoriCommandType.READ;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		addIndex(10, 50, expectedIndex);
		Collections.sort(expectedIndex);
		MemoriCommand expected = new MemoriCommand(read, expectedIndex);
		MemoriCommand results = mp.parse("read 50 -10");
		assertTrue(compareIndexes(results, expected));
	}

	// when the users wants to read line 50 - 10-1
	@Test
	public final void testRead3() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("READ 50 -10 -1");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to read line 50 - 10 1-40
	@Test
	public final void testRead4() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("read 50 -10 1-40");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to read a negative number
	@Test
	public final void testRead5() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("read -1");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to read a negative number
	@Test
	public final void testRead6() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("read -1 - 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to read a negative number
	@Test
	public final void testRead7() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("reda -1 -- 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to read a negative number
	@Test
	public final void testRead8() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("read -1 10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to read a negative number
	@Test
	public final void testRead9() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("read 20 -10 40");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to read something invalid
	@Test
	public final void testRead10() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("read 20  40-");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete something invalid
	@Test
	public final void testRead11() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("read 20  40-10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to delete but is not in a line
	@Test
	public final void testRead12() {

		MemoriParser mp = new MemoriParser();
		MemoriCommand results = mp.parse("read 20  40-10");
		assertTrue(results.getType().equals(MemoriCommandType.INVALID));
	}

	// when the users wants to dead line 30 20 40
	@Test
	public final void testRead13() {
		MemoriCommandType read = MemoriCommandType.READ;
		MemoriParser mp = new MemoriParser();
		ArrayList<Integer> expectedIndex = new ArrayList<Integer>();
		expectedIndex.add(20);
		expectedIndex.add(30);
		expectedIndex.add(40);
		MemoriCommand expected = new MemoriCommand(read, expectedIndex);
		MemoriCommand results = mp.parse("read 30 20 40");
		assertTrue(compareIndexes(results, expected));
	}

	// test case when users did not indicate a index
	@Test
	public final void testUpdate1() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update -s");
		assertTrue(compareUpdate(result, expected));
	}

	// test case when user indicates that he would like to update a certain line
	// but did not enter any fields
	@Test
	public final void testUpdate2() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update 1");
		assertTrue(compareUpdate(result, expected));
	}

	// when users forgot to indicate which index he would like to update
	@Test
	public final void testUpdate3() {
		MemoriCommand expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update -n jayden -s tmr -e tmr");
		assertTrue(compareUpdate(result, expected));
	}

	// a passable update case
	@Test
	public final void testUpdate4() {
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
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update 1 -n jayden -s tmr -e tmr");
		assertTrue(compareUpdate(result, expected));
	}

	// when the users never indicate a accepted field to update
	@Test
	public final void testUpdate5() {
		MemoriCommand expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update 1 -q -o ");
		assertTrue(compareUpdate(result, expected));
	}

	// when the users dates are not of an excepted format
	@Test
	public final void testUpdate6() {
		MemoriCommand expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update 1 -elove -shaha ");
		assertTrue(compareUpdate(result, expected));
	}

	// when the users name is blank
	@Test
	public final void testUpdate7() {
		MemoriCommand expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update 1 -n -etmr -stmr ");
		assertTrue(compareUpdate(result, expected));
	}

	// when the index is not excepted
	@Test
	public final void testUpdate8() {
		MemoriCommand expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update 1asdfdsfds -e -s ");
		assertTrue(compareUpdate(result, expected));
	}

	// allowed update
	@Test
	public final void testUpdate9() {
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
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("update 1 -n jayden -d -l");
		assertTrue(compareUpdate(result, expected));
	}

	// when the user would like to perform a search but did not fill in both
	// start and end date
	@Test
	public final void testSearch() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand results = mp.parse("search -n jayden -s tmr ");
	}

	// when the user would like to perform a search but an invalid end field is
	// added
	@Test
	public final void testSearch1() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand results = mp.parse("search -n jayden -s tmr -e haha ");
	}

	// when the user would like to perform a search but no fields is added
	@Test
	public final void testSearch2() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand results = mp.parse("search");
	}

	// when the user would like to perform a search but indicate a invalid field
	@Test
	public final void testSearch3() {
		MemoriParser mp = new MemoriParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand results = mp.parse("search -werwe");
	}

	// when the user did not indicate which field to sort
	@Test
	public final void testSort1() {
		MemoriCommand Expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("sort");
		assertTrue(compareSort(result, Expected));
	}

	// when the user would like to sort by start
	@Test
	public final void testSort2() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		Boolean[] FilledFields = new Boolean[5];
		for (int i = 0; i < FilledFields.length; i++) {
			FilledFields[i] = false;
		}
		FilledFields[1] = true;
		MemoriCommand Expected = new MemoriCommand(sort, FilledFields);
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("sort -s");
		assertTrue(compareSort(result, Expected));
	}

	// when the user would like to sort by end
	@Test
	public final void sort3() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		Boolean[] FilledFields = new Boolean[5];
		for (int i = 0; i < FilledFields.length; i++) {
			FilledFields[i] = false;
		}
		FilledFields[2] = true;
		MemoriCommand Expected = new MemoriCommand(sort, FilledFields);
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("sort -e");
		assertTrue(compareSort(result, Expected));
	}

	// when the user would like to sort by location
	@Test
	public final void sort4() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		Boolean[] FilledFields = new Boolean[5];
		for (int i = 0; i < FilledFields.length; i++) {
			FilledFields[i] = false;
		}
		FilledFields[3] = true;
		MemoriCommand Expected = new MemoriCommand(sort, FilledFields);
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("sort -l");
		assertTrue(compareSort(result, Expected));
	}

	// when the user will like to sort by description
	@Test
	public final void sort5() {

		MemoriCommandType sort = MemoriCommandType.SORT;
		Boolean[] FilledFields = new Boolean[5];
		for (int i = 0; i < FilledFields.length; i++) {
			FilledFields[i] = false;
		}
		FilledFields[4] = true;
		MemoriCommand Expected = new MemoriCommand(sort, FilledFields);
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("sort -d");
		assertTrue(compareSort(result, Expected));
	}

	// when the user would like to sort more than one acceptable field
	@Test
	public final void sort6() {
		MemoriCommand Expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("sort -d -e -s");
		assertTrue(compareSort(result, Expected));
	}

	// when the user would like to sort an unacceptable
	@Test
	public final void sort7() {
		MemoriCommand Expected = new MemoriCommand("");
		MemoriParser mp = new MemoriParser();
		MemoriCommand result = mp.parse("sort -f");
		assertTrue(compareSort(result, Expected));
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

		// if both is an invalid command straight return true
		if (expectedCommand.equals(MemoriCommandType.INVALID)
				&& resultsCommand.equals(MemoriCommandType.INVALID)) {

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
		// viseversa return false
		if (((expectedStartDate != null) && (resultStartDate == null))
				|| ((expectedStartDate == null) && (resultStartDate != null))) {

			return false;
		}
		// if either expectedEndDate is null but resultEndDate is not null
		// viseversa return false
		if (((expectedEndDate != null) && (resultEndDate == null))
				|| ((expectedEndDate == null) && (resultEndDate != null))) {

			return false;
		}
		// if both resultsStartDate and expectedStartDate is not null
		// compare the start dates
		if (((expectedStartDate != null) && (resultStartDate != null))
				|| ((expectedStartDate != null) && (resultStartDate != null))) {

			if (!DATE_FORMATTER.format(expectedStartDate).equals(
					DATE_FORMATTER.format(resultStartDate))) {

				return false;
			}
		}
		// if both resultsEndDate and expectedEndDate is not null
		// compare the end dates
		if (((expectedEndDate != null) && (resultEndDate != null))
				|| ((expectedEndDate != null) && (resultEndDate != null))) {

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
		MemoriCommandType resultsCommand = results.getType();
		MemoriCommandType expectedCommand = expected.getType();

		if (resultsCommand != expectedCommand) {
			return false;
		}

		if ((expectedField == null) && (resultsField == null)) {
			return true;
		}

		for (int i = 0; i < resultsField.length; i++) {
			if (resultsField[i] != expectedField[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * compare the start date in string format , end date in string
	 * format,FilledFields, index to update and all the string fields of the
	 * expected and the results of MemoriParser.
	 * 
	 * @param results
	 * @param expected
	 * @return
	 */
	public boolean compareUpdate(MemoriCommand results, MemoriCommand expected) {

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

	private boolean compareIndexes(MemoriCommand results, MemoriCommand expected) {

		MemoriCommandType resultsCommand = results.getType();
		MemoriCommandType expectedCommand = expected.getType();
		ArrayList<Integer> resultsIndex = results.getIndexes();
		ArrayList<Integer> expectedIndex = results.getIndexes();
		if (!resultsCommand.equals(expectedCommand)) {
			return false;
		}
		if (resultsIndex.size() != expectedIndex.size()) {
			return false;
		}
		Collections.sort(resultsIndex);
		Collections.sort(expectedIndex);
		for (int index = 0; index < resultsIndex.size(); index++) {
			if (resultsIndex.get(index) != expectedIndex.get(index)) {
				return false;
			}
		}
		return true;
	}

	public void addIndex(int lower, int upper, ArrayList<Integer> ExpectedIndex) {
		for (; lower < upper + 1; lower++) {
			ExpectedIndex.add(lower);
		}
	}

}
