package memori;

public class ReadParser extends FieldsParser {

	public ReadParser() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		try{
			int index = Integer.parseInt(fields);
			return new MemoriCommand(cmdType,index);
		}catch(NumberFormatException e){
			return null;
		}
	}

}
