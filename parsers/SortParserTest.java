package memori.parsers;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class SortParserTest {

	@Test
	public final void sort1() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		MemoriCommand Expected = new MemoriCommand("");
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "");
		assertTrue(compareTo(result, Expected));
	}
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
	@Test
	public final void sort6() {
		MemoriCommandType sort = MemoriCommandType.SORT;
		MemoriCommand Expected = new MemoriCommand("");
		SortParser sp = new SortParser();
		MemoriCommand result = sp.parse(sort, "-d -e -s");
		assertTrue(compareTo(result, Expected));
	}
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
