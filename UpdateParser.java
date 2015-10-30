package memori;

import java.util.Date;

public class UpdateParser extends FieldsParser {
	private String INVALID_MESSAGE = "Oops, the index you try to update is not available."
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
				UpdateFilledFields();
				//printFilledFields();
				if((FilledFields[START_INDEX]==true)&&(startEnd[0]==null)){
					return new MemoriCommand(INVALID_MESSAGE);
				}
				if((FilledFields[END_INDEX]==true)&&(startEnd[1]==null)){
					return new MemoriCommand(INVALID_MESSAGE);
				}
				//System.out.println(startEnd[0]);
				return new MemoriCommand(cmdType,startEnd[0],startEnd[1],stringFields,index,FilledFields);
			}
	
			return new MemoriCommand(INVALID_MESSAGE);
		}catch(NumberFormatException e){
			
			return new MemoriCommand(INVALID_MESSAGE);
		}
	}
	public void printFilledFields(){
		for(int i=0; i<FilledFields.length; i++){
			System.out.println(FilledFields[i]);
		}
	}
	public void printFields(String[] stringFields){
		for(int i = 0;i < stringFields.length; i++){
			System.out.println(stringFields[i]);
		}
		
	}
}
