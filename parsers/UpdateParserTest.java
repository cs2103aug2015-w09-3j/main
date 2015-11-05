//@@author A0108454H
package memori.parsers;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class UpdateParserTest {

	@Test
	public final void update1() {
		MemoriCommandType update = MemoriCommandType.UPDATE;
		MemoriCommand Expected = new MemoriCommand("");
		UpdateParser up = new UpdateParser();
		MemoriCommand result = up.parse(update, "-s");
		assertTrue(compareTo(result, Expected));
	}
	public boolean compareTo(MemoriCommand results, MemoriCommand expected) {
		
		String[] resultsArgs = results.getCommandArgs();
		String[] expectedArgs = results.getCommandArgs();
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
		
		return true;
	}
}
