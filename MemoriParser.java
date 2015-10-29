package memori;

public class MemoriParser {
	
	// commandConfig Array indexes
	private static final int COMMAND_TYPE = 0;
	private static final int FIELDS = 1;
	private String[] commandConfig = new String[2];
	public String INVALID_MESSAGE = "No fields is added, please enter one or more fields";	
	public MemoriCommand parse(String userInput) {
		String[] commandConfig = seperateCommand(userInput);
		MemoriCommandType cmdType = determineCommandType(commandConfig[COMMAND_TYPE]);
		FieldsParser fp = createFieldsParser(cmdType);
		return executeParse(userInput, commandConfig, cmdType, fp);	
	}

	private MemoriCommand executeParse(String userInput,
			String[] commandConfig, MemoriCommandType cmdType, FieldsParser fp) {
		if((userInput.length()==0)){
			return new MemoriCommand(INVALID_MESSAGE);
		}
		else if((cmdType==MemoriCommandType.EXIT)||(cmdType==MemoriCommandType.UNDO)
				||cmdType==MemoriCommandType.REDO){
			return fp.parse(cmdType,"system");
		}else if(commandConfig.length == 1){
			return new MemoriCommand(INVALID_MESSAGE);
		}else{
			return fp.parse(cmdType, commandConfig[FIELDS]);
		}
	}
	
	private FieldsParser createFieldsParser(MemoriCommandType cmdType){
	
		switch(cmdType){
		case ADD:		
			return new AddParser();
		case UPDATE:
			return new UpdateParser();
		case DELETE:
			return new DeleteParser();
		case READ:
			return new ReadParser();
		case SEARCH:
			return new SearchParser();
		case SORT:
			return new SortParser();
		case COMPLETE:
			return new CompleteParser();
		case UNDO:
			return new SystemParser();
		case REDO:	
			return new SystemParser();
		case EXIT:
			return new SystemParser();
		default:
			return new InvalidParser();
		}
	}
	
	private static String[] seperateCommand(String userInput) {
		return userInput.split(" ",2);
	}
	
	private MemoriCommandType determineCommandType(String commandTypeString){
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
        case "SEARCH":
        	return MemoriCommandType.SEARCH;
        case "SORT":
        	return MemoriCommandType.SORT;
        case "COMPLETE":
        	return MemoriCommandType.COMPLETE;
        case "UNDO":
        	return MemoriCommandType.UNDO;
        case "EXIT":
        	return MemoriCommandType.EXIT;
        case "REDO":	
        	return MemoriCommandType.REDO;
        default:
        	 return MemoriCommandType.INVALID;
		}
	}
		
	
	public static void main(String[] args){
		MemoriParser parser = new  MemoriParser();
		String userinput = "name:abc start:tomorrow end:yesterday";
	}
	
}
