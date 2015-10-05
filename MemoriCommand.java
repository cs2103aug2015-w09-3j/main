package memori;

import java.util.Date;

public class MemoriCommand {
	private MemoriCommandType commandType;
	private String[] commandArgs;
	private Date start;
	private Date end;
	private int index = 0;
	private int[] indexes;
	
	public static final int NAME_INDEX = 0;
	public static final int DESCRIPTION_INDEX = 1;
	public static final int NUM_STRING_FIELDS = 2;
	
	
	public MemoriCommand(MemoriCommandType commandType,Date start,Date end,String[] commandArgs){
		this.commandType = commandType;
		this.start = start;
		this.end = end;
		this.commandArgs = commandArgs;
	}
	
	public MemoriCommand(MemoriCommandType commandType,int index){
		this.commandType = commandType;
		this.index = index;
	}
	
	
	public MemoriCommand(MemoriCommandType commandType,Date start,Date end,String[] commandArgs,int index){
		this.commandType = commandType;
		this.start = start;
		this.end = end;
		this.commandArgs = commandArgs;
		this.index = index;
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

	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}

	public int[] getIndexes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}