//@@author A0108454H
package memori.parsers;

import java.util.Date;

public class UpdateParser extends FieldsParser {
	private String INVALID_MESSAGE = "Oops, the index you try to update is not available."+"\n"
			+ "Please try again.";
	public UpdateParser() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		try{
			String[] splitted = fields.split(" ",2);
			if(splitted.length == 2){
				int index = Integer.parseInt(splitted[0]);
				extractFields(splitted[1]);
				String[] stringFields = extractStrings(); 
				Date[] startEnd = extractDates();
				if((FilledFields[START_INDEX]==true)&&(startEnd[0]==null)){
					return new MemoriCommand(INVALID_MESSAGE);
				}
				if((FilledFields[END_INDEX]==true)&&(startEnd[1]==null)){
					return new MemoriCommand(INVALID_MESSAGE);
				}
				return new MemoriCommand(cmdType,startEnd[0],startEnd[1],stringFields,index,FilledFields);
			}
	
			return new MemoriCommand(INVALID_MESSAGE);
		}catch(NumberFormatException e){
			
			return new MemoriCommand(INVALID_MESSAGE);
		}
	}

}
