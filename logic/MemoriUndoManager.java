//@@author A0098038W
package memori.logic;

import java.util.ArrayList;
import java.util.Stack;

public class MemoriUndoManager {
	private Stack<ArrayList<MemoriEvent>> undoStack;

	public MemoriUndoManager() {
		undoStack = new Stack<ArrayList<MemoriEvent>>();
	}
	

	public ArrayList<MemoriEvent> undo(){
		if(!undoStack.empty()){
			ArrayList<MemoriEvent> temp = undoStack.pop();
			return temp;
		}
		else
			return null;
	}
	
	
	
	public void addToUndo(ArrayList<MemoriEvent> events){
		ArrayList<MemoriEvent> copy = copyEventsArr(events);
		undoStack.add(copy);
	
	}
	

	
	private ArrayList<MemoriEvent> copyEventsArr(ArrayList<MemoriEvent> toCopy){
		ArrayList<MemoriEvent> theCopy = new ArrayList<MemoriEvent>();
		for(int i=0;i<toCopy.size();i++){
			MemoriEvent current = toCopy.get(i);
			MemoriEvent toAdd = new MemoriEvent();
			toAdd.replace(current);
			toAdd.setInternalCalId(current.getInternalId());
			toAdd.setComplete(current.getComplete());
			theCopy.add(toAdd);
		}
		return theCopy;
	}

}
