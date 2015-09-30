package memori;

public class MemoriCommand {
	private MemoriCommandType commandType;
	private String[] commandArgs;
	
	public static final int NAME = 0;
	public static final int START = 1;
	public static final int END =  2;
	
	public MemoriCommand(String commandTypeString ,String[] commandArgs){
		this.commandArgs = commandArgs;
		commandType = determineCommandType(commandTypeString);
	}
	
	private MemoriCommandType  determineCommandType(String commandTypeString){
		commandTypeString = commandTypeString.toUpperCase();
		switch (commandTypeString) {
        case "ADD":
            return MemoriCommandType.ADD;
        case "UPDATE":
        	 return MemoriCommandType.UPDATE;
        case "DELETE":
             return MemoriCommandType.DELETE;
        default:
        	 return MemoriCommandType.INVALID;
		}
	}
	public MemoriCommandType getType(){
		return commandType;
	}
	
	public String getStart(){
		return commandArgs[START];
	}
	
	public String getEnd(){
		return commandArgs[END];
	}
	
	public String getName(){
		return commandArgs[NAME];
	}
}
