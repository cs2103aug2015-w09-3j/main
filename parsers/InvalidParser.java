//@@author A0108454H
package memori.parsers;

// handle invalid
public class InvalidParser extends FieldsParser{
	public static final String INVALID_MESSAGE = "Oops, the command you have entered is not available."
			+ "Please try again"+"\n";
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		
		return new MemoriCommand(INVALID_MESSAGE);
	}

}

