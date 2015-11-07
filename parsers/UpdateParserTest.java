//@@author A0108454H
package memori.parsers;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class UpdateParserTest {
	//test case when users did not indicate a index
	@Test
	public final void update1() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "-s");
		assertTrue(compareTo(result, Expected));
	}
	//test case when user indicates that he would like to update a certain line
	//but did not enter any fields
	@Test
	public final void update2() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "1");
		assertTrue(compareTo(result, Expected));
	}
	//when users forgot to indicate which index he would like to update
	@Test
	public final void update3() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "-n jayden -s tmr -e tmr");
		assertTrue(compareTo(result, Expected));
	}
	//
	@Test
	public final void update4() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "-n jayden -s tmr -e tmr");
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
		
		String[] resultsArgs = results.getCommandArgs();
		String[] expectedArgs = results.getCommandArgs();
		Date expectedStartDate = expected.getStart();
		Date resultStartDate = results.getStart();
		Date expectedEndDate = expected.getEnd();
		Date resultEndDate = results.getEnd();
		
		if ((expectedArgs == null)
				&& (resultsArgs == null)) {
			return true;
		}else {
			for(int i = 0; i < resultsArgs.length;i++){
				if(resultsArgs[i].equals(expectedArgs[i])!=true){
					return false;
				}
			}
		}
		
		if((expectedStartDate!=null)&&(resultStartDate!=null)){
			if(!expectedStartDate.equals(resultStartDate)){
				return false;
			}
		}
		
		if((expectedEndDate!=null)&&(resultEndDate!=null)){
			if(expectedEndDate.compareTo(resultEndDate) != 0){
				return false;
			}
		}	
		return true;
	}
}
