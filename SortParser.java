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

}
