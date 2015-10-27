package memori;

import java.util.Date;

public class SortParser extends FieldsParser{
	
	public SortParser(){
		init();
	}
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		extractFields(cmdFields);
		UpdateFilledFields();
		return new MemoriCommand(cmdType,FilledFields);
	}
	
	//determine the field that the user would like to sort
	public void printField(){

		for(int i = 0;i < FilledFields.length; i++){
			System.out.println(FilledFields[i]);
		}
		
	
	}

}
