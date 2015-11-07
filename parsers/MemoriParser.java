//@@author A0108454H
package memori.parsers;

public class MemoriParser {
	
	// commandConfig Array indexes
	private static final int COMMAND_TYPE = 0;
	private static final int FIELDS = 1;

	private String[] commandConfig = new String[2];
	private String SystemField = "system";
	public String INVALID_MESSAGE = "Oops you entered an invalid command, please try again."+"\n";	
	
	//The main parse method
	public MemoriCommand parse(String userInput) {
		commandConfig = seperateCommand(userInput);
		MemoriCommandType cmdType = determineCommandType(commandConfig[COMMAND_TYPE]);
		FieldsParser fp = createFieldsParser(cmdType);
		return executeParse(userInput, commandConfig, cmdType, fp);	
	}
	/**
	 * this method will execute the parse function.
	 * 
	 * @param userInput
	 * @param commandConfig
	 * @param cmdType
	 * @param fp
	 * @return
	 */
	private MemoriCommand executeParse(String userInput,
			String[] commandConfig, MemoriCommandType cmdType, FieldsParser fp) {
	
		if((cmdType==MemoriCommandType.EXIT)
				||(cmdType==MemoriCommandType.UNDO)){
			
			return fp.parse(cmdType,SystemField);
		
		}else if(commandConfig.length==1||userInput.length()==0){
			
			return new MemoriCommand(INVALID_MESSAGE);
		
		}else{
			
			return fp.parse(cmdType, commandConfig[FIELDS]);
		}
	}
	/**
	 * This method will determine which type of parser to handle the user input
	 * base on user the commandType
	 * @param cmdType
	 * @return
	 */
	private FieldsParser createFieldsParser(MemoriCommandType cmdType){
	
		switch(cmdType){
		case ADD:		
			return new AddParser();
		case UPDATE:
			return new UpdateParser();
		case DELETE:
			return new IndexesParser();
		case READ:
			return new IndexesParser();
		case SEARCH:
			return new SearchParser();
		case SORT:
			return new SortParser();
		case COMPLETE:
		case OPEN:
			return new IndexesParser();
		case UNDO:
		case EXIT:
			return new SystemParser();
		default:
			return new InvalidParser();
		}
	}
	/**
	 * This function splits the users input to a commandType in a form of string
	 * and the details the user followed with the commmand Type
	 * @param userInput
	 * @return 
	 */
	private static String[] seperateCommand(String userInput) {
		return userInput.split(" ",2);
	}
	/**
	 * This function determines users command type using swith cases and return 
	 * the users commandType 
	 * @param commandTypeString
	 * @return MemoriCommandType
	 */
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
        case "OPEN":
        	return MemoriCommandType.OPEN;
        case "UNDO":
        	return MemoriCommandType.UNDO;
        case "EXIT":
        	return MemoriCommandType.EXIT;
        default:
        	 return MemoriCommandType.INVALID;
		}
	}
}
