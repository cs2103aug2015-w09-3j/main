package memori;

import java.util.Arrays;
import java.util.Date;

public class AddParser extends FieldsParser {

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
		System.out.println(Arrays.toString(stringFields));
		showFields();
		return new MemoriCommand(cmdType,startEnd[0],startEnd[1],stringFields);
	}

}
