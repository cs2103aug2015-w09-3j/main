package memori;

import java.util.Date;

public class SearchParser extends FieldsParser {

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		
		Date date = DateParser.parseDate(cmdFields); 
		String[] stringFields = new String[4];
		stringFields[0] = cmdFields;
		return new MemoriCommand(cmdType,date,date,stringFields); 
		
	}
	
}
