//@@author A0108454H
package memori.parsers.parserTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import memori.parsers.IndexesParser;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;

import org.junit.Test;

public class IndexesParserTest {
	private ArrayList<Integer> ExpectedIndex;
	
	@Test
	public final void testDeleteWithNegativeNumber() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber2() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1 1";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber3() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "-1 - 1";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber4() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "1-1-1 ";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithNegativeNumber5() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "3-1  4-5-1";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithRange() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "3-1";
		ExpectedIndex = new ArrayList<Integer>();
		ExpectedIndex.add(1);
		ExpectedIndex.add(2);
		ExpectedIndex.add(3);
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange1() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "3   -1";
		ExpectedIndex = new ArrayList<Integer>();
		ExpectedIndex.add(1);
		ExpectedIndex.add(2);
		ExpectedIndex.add(3);

		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange2() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "   32     -      50  ";
		ExpectedIndex = new ArrayList<Integer>();
		addIndex(32,50);
	
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange3() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40 ";
		ExpectedIndex = new ArrayList<Integer>();
		ExpectedIndex.add(40);
	
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange4() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40   50    40             ";
		ExpectedIndex = new ArrayList<Integer>();
		ExpectedIndex.add(40);
		ExpectedIndex.add(50);
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testDeleteWithRange5() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40 - 50 60 -70";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithRange6() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40 - 50 60 -70  ";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithRange7() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40 -  -70  ";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testDeleteWithRange8() {
		MemoriCommandType cmdType = MemoriCommandType.DELETE;
		String deleteToTest = "40-   7";
		ExpectedIndex = new ArrayList<Integer>();
		addIndex(7,40);
	
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testReadWithNegativeNumber() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "-1";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithNegativeNumber2() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "-1 1";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithNegativeNumber3() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "-1 - 1";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithNegativeNumber4() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "1-1-1 ";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithNegativeNumber5() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "3-1  4-5-1";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithRange() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String ReadToTest = "3-1";
		ExpectedIndex = new ArrayList<Integer>();
		ExpectedIndex.add(1);
		ExpectedIndex.add(2);
		ExpectedIndex.add(3);
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,ReadToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testReadWithRange1() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "3   -1";
		ExpectedIndex = new ArrayList<Integer>();
		ExpectedIndex.add(1);
		ExpectedIndex.add(2);
		ExpectedIndex.add(3);

		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testReadWithRange2() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "   32     -      50  ";
		ExpectedIndex = new ArrayList<Integer>();
		addIndex(32,50);
	
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testReadWithRange3() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "40 ";
		ExpectedIndex = new ArrayList<Integer>();
		ExpectedIndex.add(40);
	
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testReadWithRange4() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "40   50    40             ";
		ExpectedIndex = new ArrayList<Integer>();
		ExpectedIndex.add(40);
		ExpectedIndex.add(50);
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	@Test
	public final void testReadWithRange5() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "40 - 50 60 -70";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithRange6() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "40 - 50 60 -70  ";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithRange7() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "40 -  -70  ";
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		assertTrue(get.getIndexes()==null);
	}
	@Test
	public final void testReadWithRange8() {
		MemoriCommandType cmdType = MemoriCommandType.READ;
		String deleteToTest = "40-   7";
		ExpectedIndex = new ArrayList<Integer>();
		addIndex(7,40);
	
		Collections.sort(ExpectedIndex);
		IndexesParser ip = new IndexesParser();
		MemoriCommand get = ip.parse(cmdType,deleteToTest);
		ArrayList<Integer> deleteResultsIndex = get.getIndexes();
		Collections.sort(deleteResultsIndex);
		assertTrue(equals(deleteResultsIndex,ExpectedIndex));
	}
	public void addIndex(int lower,int upper){
		for(;lower<upper+1;lower++){
			ExpectedIndex.add(lower);
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