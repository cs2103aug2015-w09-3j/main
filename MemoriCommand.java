package memori;

import java.util.ArrayList;
import java.util.Date;

public class MemoriCommand {
	private MemoriCommandType commandType;
	private String[] commandArgs;
	private Boolean[] memoriFields;
	private Date start;
	private Date end;
	private int index = 0;
	private ArrayList<Integer> indexes;
	private String invalidMessage;
	//jayden added more indexs
	public static final int NAME_INDEX = 0;
	public static final int LOCATION_INDEX = 1;
	public static final int DESCRIPTION_INDEX = 2;
	public static final int NUM_STRING_FIELDS = 3;
	
	
	public MemoriCommand(MemoriCommandType commandType,Date start,Date end,String[] commandArgs){
		this.commandType = commandType;
		this.start = start;
		this.end = end;
		this.commandArgs = commandArgs;
	}
	public MemoriCommand(MemoriCommandType cmdType){
		commandType = cmdType;
	}
	public MemoriCommand(MemoriCommandType commandType,Date start
			,Date end,String[] commandArgs,int index,Boolean[] memoriFields){
		this.commandType = commandType;
		this.start = start;
		this.end = end;
		this.commandArgs = commandArgs;
		this.index = index;
		this.memoriFields = memoriFields;
	}
	public MemoriCommand(MemoriCommandType commandType,ArrayList<Integer> indexes){
		this.commandType = commandType;
		this.indexes = indexes;
	}
	public MemoriCommand(String invalidMessage){
		this.commandType = MemoriCommandType.INVALID;
		this.invalidMessage = invalidMessage;
	}
	//new change, for sort
	public MemoriCommand(MemoriCommandType commandType,Boolean[] memoriFields){
		this.commandType = commandType;
		this.memoriFields = memoriFields;
	}
	public MemoriCommandType getType(){
		return commandType;
	}
	
	public Date getStart(){
		return start;
	}
	
	public Date getEnd(){
		return end;
	}
	
	public String getName(){
		return commandArgs[NAME_INDEX];
	}
	
	public String getDescription(){
		return commandArgs[DESCRIPTION_INDEX];
	}
	public String getLocation(){
		return commandArgs[LOCATION_INDEX];
	}
	public int getIndex() {
		return index;
	}

	public ArrayList<Integer> getIndexes() {
		return indexes;
	}
	public Boolean[] getMemoriField(){
		return memoriFields;
	}
	public String getInvalidMessage(){
		return invalidMessage; 
	}


}