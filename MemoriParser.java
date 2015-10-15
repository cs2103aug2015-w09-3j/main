package memori;

public class MemoriParser {

	// commandConfig Array indexes
	private static final int COMMAND_TYPE = 0;
	private static final int FIELDS = 1;
	private String[] commandConfig = new String[2];

	public MemoriCommand parse(String userInput) {
		String[] commandConfig = seperateCommand(userInput);
		MemoriCommandType cmdType = determineCommandType(commandConfig[COMMAND_TYPE]);
		FieldsParser fp = createFieldsParser(cmdType);
		// jayden handles if empty input or no fields added invalid
		if ((userInput.length() == 0) || (commandConfig.length == 1)) {
			return new MemoriCommand();
		}
		return fp.parse(cmdType, commandConfig[FIELDS]);
	}

	private FieldsParser createFieldsParser(MemoriCommandType cmdType) {
		switch (cmdType) {
		case ADD:
			return new AddParser();
		case UPDATE:
			return new UpdateParser();
		case DELETE:
			return new DeleteParser();
		case READ:
			return new ReadParser();
		default:
			return null;
		}
	}

	private static String[] seperateCommand(String userInput) {
		return userInput.split(" ", 2);
	}

	private MemoriCommandType determineCommandType(String commandTypeString) {
		commandTypeString = commandTypeString.toUpperCase();
		switch (commandTypeString) {
		case "ADD":
			return MemoriCommandType.ADD;
		case "UPDATE":
			return MemoriCommandType.UPDATE;
		case "DELETE":
			return MemoriCommandType.DELETE;
		case "READ":
			return MemoriCommandType.READ;
		default:
			return MemoriCommandType.INVALID;
		}
	}

	public static void main(String[] args) {
		MemoriParser parser = new MemoriParser();
		String userinput = "name:abc start:tomorrow end:yesterday";
	}

}
