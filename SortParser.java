package memori;

import java.util.Date;

public class SortParser extends FieldsParser{
	
	public SortParser(){
		init();
	}
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
	
		return determineSortCommand(cmdType, cmdFields, FilledFields);
	}
	
	//determine the field that the user would like to sort
	private MemoriCommand determineSortCommand(MemoriCommandType cmdType,
			String cmdFields, Boolean[] FilledFields) {
		if(cmdFields.contains(FIELD_IDENTIFIERS[NAME_INDEX])){
			FilledFields[NAME_INDEX] = true;
			return new MemoriCommand(cmdType,FilledFields);
		}else if(cmdFields.contains(FIELD_IDENTIFIERS[START_INDEX])){
			FilledFields[START_INDEX] = true;;
			return new MemoriCommand(cmdType,FilledFields);
		}else if(cmdFields.contains(FIELD_IDENTIFIERS[END_INDEX])){
			FilledFields[END_INDEX] = true;
			return new MemoriCommand(cmdType,FilledFields);
		}else if(cmdFields.contains(FIELD_IDENTIFIERS[LOCATION_INDEX])){
			FilledFields[LOCATION_INDEX] = true;
			return new MemoriCommand(cmdType,FilledFields);
		}else if(cmdFields.contains(FIELD_IDENTIFIERS[PRIORITY_INDEX])){
			FilledFields[PRIORITY_INDEX] = true;
			return new MemoriCommand(cmdType,FilledFields);
		}else if(cmdFields.contains(FIELD_IDENTIFIERS[DESCRIPTION_INDEX])){
			FilledFields[DESCRIPTION_INDEX] = true;
			return new MemoriCommand(cmdType,FilledFields);
		}else{
			return new MemoriCommand();
		}
	}

}
