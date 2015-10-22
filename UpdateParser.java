package memori;

import java.util.Date;

public class UpdateParser extends FieldsParser {

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
				return new MemoriCommand(cmdType,startEnd[0],startEnd[1],stringFields,index,FilledFields);
			}
	
			return new MemoriCommand();
		}catch(NumberFormatException e){
			
			return new MemoriCommand();
		}
	}
	public void printExtract(){
		for(int i=0; i<fields.length; i++){
			System.out.println("updateFields"+fields[i]);
		}
	}
	public void printFields(String[] stringFields){
		for(int i = 0;i < stringFields.length; i++){
			System.out.println(stringFields[i]);
		}
		
	}
}
