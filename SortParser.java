package memori;

import java.util.Date;

public class SortParser extends FieldsParser{
	
	public SortParser(){
		init();
	}
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		extractFields(cmdFields);
		updateFilledFields();
		return new MemoriCommand(cmdType,FilledFields);
	}
	
	//determine the field that the user would like to sort


}
