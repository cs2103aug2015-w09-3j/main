package memori;

import java.util.ArrayList;

public class MemoriCalendar {
	private static final String MESSAGE_ADD = "Added.\n";
	private static final String MESSAGE_DELETE = "Deleted.\n";
	private static final String MESSAGE_UPDATE = "Updated: \n";
	private static final String MESSAGE_READ = "Reading: \n"; 
	private static final String LINE_INDEX_DOES_NOT_EXISTS = "Line index does not exists.\n";
	private static final String MESSAGE_EMPTYFILE = "File is Empty.\n";
	
	private ArrayList<MemoriEvent>  memoriCalendar;
	private int maxId = 0;
	private boolean maxIdSet = false;
	//added location/description/priority
	private static final String HEADER ="No: Name of Event:    Start:    End:\n";
	
	
	public MemoriCalendar(){
		memoriCalendar = new ArrayList<MemoriEvent>();
	}
	private void findMaxId(){
		for(MemoriEvent e: memoriCalendar){
			int internalId = e.getInternalId();
			if(internalId > maxId){
				maxId = internalId;
			}
			
		}
		maxIdSet = true;
	}
	
	public String add(MemoriCommand command){
		if(maxIdSet = false)
			findMaxId();
		maxId++;
		MemoriEvent event = new MemoriEvent(command.getName(),command.getStart(),command.getEnd(),
									maxId);
		
		memoriCalendar.add(event);
		return MESSAGE_ADD;
	}
	//What about priority/location? What is the difference between internalId and externalId?
	
	public String display(){
		String output = HEADER;
		int i=1;
			for(MemoriEvent e: memoriCalendar){
				output+=i +" " +e.display() + "\n";
				i++;
			}
		return output;
	}
	public String execute(MemoriCommand command){
			switch(command.getType()){
			case ADD:
				return add(command);
			case UPDATE:
				return update(command);
			case DELETE:
				return delete(command);
			case READ:
				return read(command);
			default:
				return "invalid";
			}
		
	}
	private String read(MemoriCommand command){
		MemoriEvent displayText;
			if(memoriCalendar.isEmpty()){
				return MESSAGE_EMPTYFILE;
			}else{
				int index = Integer.parseInt("1");
			
				if(memoriCalendar.size() < index){
					return LINE_INDEX_DOES_NOT_EXISTS;
				}else{
					displayText = memoriCalendar.get(index - 1);
					return displayText.read();
				}
			}	
	}
	private String delete(MemoriCommand command){
		MemoriEvent deleteText;
			if(memoriCalendar.isEmpty()){
				return MESSAGE_EMPTYFILE;
			}else{
				int index = Integer.parseInt("1");
				//to implement getIndex in MemoriCommand that returns a String
				
				if(memoriCalendar.size() < index){
					return LINE_INDEX_DOES_NOT_EXISTS;	
				}else{
					deleteText = memoriCalendar.get(index - 1);
					memoriCalendar.remove(index - 1);
					return String.format(MESSAGE_DELETE, deleteText);	
				}
	
			}

		
	}
	private String update(MemoriCommand command){
		MemoriEvent originalEvent;
			if(memoriCalendar.isEmpty()){
				return MESSAGE_EMPTYFILE;
			}else{
				int index = Integer.parseInt("1");
				
				if(memoriCalendar.size() < index){
					return LINE_INDEX_DOES_NOT_EXISTS;
				}else{
					originalEvent = memoriCalendar.get(index - 1);
					originalEvent.update(command.getName(),
								command.getStart(), command.getEnd(), "");
					return String.format(MESSAGE_UPDATE, index);
				}
			}
		
	}
}