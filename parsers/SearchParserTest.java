package memori.parsers;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class SearchParserTest {
	public static final String DATE_FORMAT = "dd MMM yyyy HH:mm E";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	
	//when the user would like to perform a search but did not fill in both start and end date
	@Test
	public final void testSearch() {
		MemoriCommandType search = MemoriCommandType.SEARCH;
		SearchParser sp = new SearchParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand results = sp.parse(search,"-n jayden -s tmr ");
	}
	//when the user would like to perform a search but no field is added
	@Test
	public final void testSearch1() {
		MemoriCommandType search = MemoriCommandType.SEARCH;
		SearchParser sp = new SearchParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand results = sp.parse(search,"-n jayden -s tmr -e haha ");
	}
	//when the user would like to perform a search but no fields is added
	@Test
	public final void testSearch2() {
		MemoriCommandType search = MemoriCommandType.SEARCH;
		SearchParser sp = new SearchParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand results = sp.parse(search,"");
	}
	//when the user would like to perform a search but indicate a invalid field
	@Test
	public final void testSearch3() {
		MemoriCommandType search = MemoriCommandType.SEARCH;
		SearchParser sp = new SearchParser();
		MemoriCommand expected = new MemoriCommand("");
		MemoriCommand results = sp.parse(search,"-werwe");
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
	
}
