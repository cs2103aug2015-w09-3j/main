//@@author A0108454H
package memori.parsers.parserTesting;

import static org.junit.Assert.*;

import java.util.Date;

import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;
import memori.parsers.SortParser;

import org.junit.Test;

public class SortParserTest {
	
	//when the user did not indicate which field to sort
	@Test
	public final void sort1() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		MemoriCommand Expected = new MemoriCommand("");
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "");
		assertTrue(compareTo(result, Expected));
	}
	//when the user would like to sort by start
	@Test
	public final void sort2() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		Boolean[] FilledFields = new Boolean[5];
		for(int i = 0; i < FilledFields.length; i++){
			FilledFields[i] = false;
		}
		FilledFields[1] = true;
		MemoriCommand Expected = new MemoriCommand(sort,FilledFields);
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "-s");
		assertTrue(compareTo(result, Expected));
	}
	//when the user would like to sort by end
	@Test
	public final void sort3() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		Boolean[] FilledFields = new Boolean[5];
		for(int i = 0; i < FilledFields.length; i++){
			FilledFields[i] = false;
		}
		FilledFields[2] = true;
		MemoriCommand Expected = new MemoriCommand(sort,FilledFields);
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "-e");
		assertTrue(compareTo(result, Expected));
	}
	//when the user would like to sort by location
	@Test
	public final void sort4() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		Boolean[] FilledFields = new Boolean[5];
		for(int i = 0; i < FilledFields.length; i++){
			FilledFields[i] = false;
		}
		FilledFields[3] = true;
		MemoriCommand Expected = new MemoriCommand(sort,FilledFields);
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "-l");
		assertTrue(compareTo(result, Expected));
	}
	//when the user will like to sort by description
	@Test
	public final void sort5() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		Boolean[] FilledFields = new Boolean[5];
		for(int i = 0; i < FilledFields.length; i++){
			FilledFields[i] = false;
		}
		FilledFields[4] = true;
		MemoriCommand Expected = new MemoriCommand(sort,FilledFields);
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "-d");
		assertTrue(compareTo(result, Expected));
	}
	//when the user would like to sort more than one acceptable field
	@Test
	public final void sort6() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		MemoriCommand Expected = new MemoriCommand("");
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "-d -e -s");
		assertTrue(compareTo(result, Expected));
	}
	//when the user would like to sort an unacceptable 
	@Test
	public final void sort7() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		MemoriCommand Expected = new MemoriCommand("");
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "-f");
		assertTrue(compareTo(result, Expected));
	}
	/**
	 * Compare the sort fields of both expected and results
	 * @param results
	 * @param expected
	 * @return
	 */
	public boolean compareTo(MemoriCommand results, MemoriCommand expected) {
		
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
}
