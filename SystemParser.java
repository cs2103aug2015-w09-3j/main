package memori;

public class SystemParser extends FieldsParser {

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		// TODO Auto-generated method stub
		return new MemoriCommand(cmdType);
	}

}
