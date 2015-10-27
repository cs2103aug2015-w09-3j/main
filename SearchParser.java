 package memori;

import java.util.Date;

public class SearchParser extends FieldsParser {
	private String INVALID_MESSAGE = "Oops, the date format you try to enter is not available."
			+ "Please try again.";
	
	public SearchParser() {
		// TODO Auto-generated constructor stub
		init();
	}
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		
		extractFields(cmdFields);
		String[] StringFields = extractStrings();
		Date[] startEnd = extractDates();
		//when there is an end date but no start date or vise versa
		//return invalid memory command
		if((startEnd[0]==null)&&(startEnd[1]!=null)){
			
			return new MemoriCommand(INVALID_MESSAGE);
		}else if((startEnd[0]!=null)&&(startEnd[1]==null)){
			
			return new MemoriCommand(INVALID_MESSAGE);
		}else{
			return new MemoriCommand(cmdType,startEnd[0],startEnd[1],StringFields);
		}
	
	}
	
}
