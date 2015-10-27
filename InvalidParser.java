package memori;
// handle invalid
public class InvalidParser extends FieldsParser{
	public String INVALID_MESSAGE = "Oops, the command you have entered is not available."
			+ "Please try again";
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		
		return new MemoriCommand(INVALID_MESSAGE);
	}

}

