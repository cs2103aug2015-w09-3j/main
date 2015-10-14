package memori;
// handle invalid
public class InvalidParser extends FieldsParser{

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		
		return new MemoriCommand();
	}

	
	
}
