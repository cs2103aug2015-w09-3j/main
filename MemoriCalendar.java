package memori;

import java.util.ArrayList;

public class MemoriCalendar {
	private ArrayList<MemoriEvent>  memoriCalendar;
	private static final String HEADER ="Name of Event:       Start:       End:\n";
	
	
	public MemoriCalendar(){
		memoriCalendar = new ArrayList<MemoriEvent>();
	}
	public void add(MemoriCommand command){
		MemoriEvent event = new MemoriEvent(command.getName(),command.getStart(),command.getEnd());
		System.out.println("adding");
		memoriCalendar.add(event);
	}
	
	public String display(){
		String output = HEADER;	
			for(MemoriEvent e: memoriCalendar){
				output+= e.display() + "\n";
			}
		return output;
	}
	public void execute(MemoriCommand command) {
			switch(command.getType()){
			case ADD:
				add(command);
				break;
			case UPDATE:
				update(command);
				break;
			case DELETE:
				delete(command);
				break;
			}
		
	}
	private void delete(MemoriCommand command) {
		// TODO Auto-generated method stub
		
	}
	private void update(MemoriCommand command) {
		// TODO Auto-generated method stub
		
	}
}
