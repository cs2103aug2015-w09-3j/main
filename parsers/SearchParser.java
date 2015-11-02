 package memori.parsers;

import java.util.Date;

public class SearchParser extends FieldsParser {
	private String INVALID_MESSAGE = "Oops, the date format you try to enter is not available."
			+ "Please try again."+"\n";
	private boolean legitField = false;
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
		updateLegitField();
		if(legitField == false){
			return new MemoriCommand(INVALID_MESSAGE);
		}
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
	public void updateLegitField(){
		for(int i = 0;i < FilledFields.length; i++){
			if(FilledFields[i]==true){
				legitField = true; 
			}
		}
		
	}


}
