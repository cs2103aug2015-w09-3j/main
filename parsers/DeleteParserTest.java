package memori.parsers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

public class DeleteParserTest {
	private ArrayList<Integer> deleteExpectedIndex;
	
	@Test
	public final void testDeleteWithNegativeNumber() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber2() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1 1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber3() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1 - 1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber4() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "1-1-1 ";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber5() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "3-1  4-5-1";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithRange() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "3-1";
		deleteExpectedIndex = new ArrayList<Integer>();
		deleteExpectedIndex.add(1);
		deleteExpectedIndex.add(2);
		deleteExpectedIndex.add(3);
		Collections.sort(deleteExpectedIndex);
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,deleteExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange1() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "3   -1";
		deleteExpectedIndex = new ArrayList<Integer>();
		deleteExpectedIndex.add(1);
		deleteExpectedIndex.add(2);
		deleteExpectedIndex.add(3);

		Collections.sort(deleteExpectedIndex);
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,deleteExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange2() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "   32     -      50  ";
		deleteExpectedIndex = new ArrayList<Integer>();
		addIndex(32,50);
	
		Collections.sort(deleteExpectedIndex);
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,deleteExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange3() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40 ";
		deleteExpectedIndex = new ArrayList<Integer>();
		deleteExpectedIndex.add(40);
	
		Collections.sort(deleteExpectedIndex);
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,deleteExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange4() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40   50    40             ";
		deleteExpectedIndex = new ArrayList<Integer>();
		deleteExpectedIndex.add(40);
		deleteExpectedIndex.add(50);
		Collections.sort(deleteExpectedIndex);
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,deleteExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange5() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40 - 50 60 -70";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithRange6() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40 - 50 60 -70  ";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithRange7() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40 -  -70  ";
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithRange8() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40-   7";
		deleteExpectedIndex = new ArrayList<Integer>();
		addIndex(7,40);
	
		Collections.sort(deleteExpectedIndex);
		DeleteParser dp = new DeleteParser();
		MemoriCommand get = dp.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,deleteExpectedIndex));
	}
	public void addIndex(int lower,int upper){
		for(;lower<upper+1;lower++){
			deleteExpectedIndex.add(lower);
		}
	}
	private boolean equals(ArrayList<Integer>deleteResultsIndex
			,ArrayList<Integer>deleteExpectedIndex){
		
		if(deleteResultsIndex.size()
				!=deleteExpectedIndex.size()){
			return false;
		}
		
		for(int index = 0 ; index < deleteResultsIndex.size(); index++){
			if(deleteResultsIndex.get(index)!=deleteExpectedIndex.get(index)){
				return false;
			}
		}	
		return true;
	}
}