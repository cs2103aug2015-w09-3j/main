//@@author A0108454H
package memori.parsers;

import java.util.Date;

public class AddParser extends FieldsParser {
	private static final String INVALID_NAME_MESSAGE = "Oops,please enter the name of the event"+"\n";
	private static final String INVALID_DATE_MESSAGE = "Oops,invalid date format.Please try again"+"\n";
	private static final String INVALID_LOCATION_MESSAGE = "Oops,invalid location format.Please try again"+"\n";
	private static final String INVALID_DESCRIPTION_MESSAGE = "Oops,invalid description format.Please try again"+"\n";

	public AddParser() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		// TODO Auto-generated method stub
		
		extractFields(cmdFields);
		String[] stringFields = extractStrings();
		Date[] startEnd = extractDates();
		UpdateFilledFields();
		
		//*added if there is no name for the add event
		
		if(stringFields[0].equals("")||(cmdFields.length()==0)){
			
			return new MemoriCommand(INVALID_NAME_MESSAGE);
		
		//*if the user indicates he wants to enter a start date but there is no start date.	
		}else if((FilledFields[START_INDEX]==true)&&startEnd[0]==null){	
			
			return new MemoriCommand(INVALID_DATE_MESSAGE);
		//*if the user indicates he wants to enter a end date but there is no end date 
		}else if((FilledFields[END_INDEX]==true)&&(startEnd[1]==null)){
			
			return new MemoriCommand(INVALID_DATE_MESSAGE);
		//*if the user indicates he wants to enter a location but there is no location
		}else if((FilledFields[LOCATION_INDEX]==true)&&(stringFields[1].equals(""))){
			
			return new MemoriCommand(INVALID_LOCATION_MESSAGE);
		//if the users indicates he wants to enter a description but there is no description
		}else if((FilledFields[DESCRIPTION_INDEX]==true)&&(stringFields[2].equals(""))){
			
			return new MemoriCommand(INVALID_DESCRIPTION_MESSAGE);
		
		}
		
		return new MemoriCommand(cmdType,startEnd[0],startEnd[1],stringFields);
			
	}
	
}
