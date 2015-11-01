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
		UpdateFilledFields();
		//when there is an end date but no start date or vise versa
		//return invalid memory command
		if((FilledFields[START_INDEX]==true)&&(FilledFields[END_INDEX]==true)){
			if((startEnd[0]!=null)&&(startEnd[1]!=null)){
				return new MemoriCommand(cmdType,startEnd[0],startEnd[1],StringFields);
			}else{
				return new MemoriCommand(INVALID_MESSAGE);
			}
		}else if((FilledFields[START_INDEX]==false)&&(FilledFields[END_INDEX]==false)){
				return new MemoriCommand(cmdType,startEnd[0],startEnd[1],StringFields);
		}else{
			return new MemoriCommand(INVALID_MESSAGE);
	
		}
	}
	public void printFilledFields(){
		for(int i =0; i<FilledFields.length;i++){
			System.out.println(FilledFields[i]);
		}
	}
}
