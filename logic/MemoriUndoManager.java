package memori.logic;

import java.util.Stack;

public class MemoriUndoManager {
	private Stack<MemoriCalendar> undoStack;
	private Stack<MemoriCalendar> redoStack;
	
	public MemoriUndoManager() {
		undoStack = new Stack<MemoriCalendar>();
		redoStack = new Stack<MemoriCalendar>();
	}
	
	public MemoriCalendar undo(){
		if(!undoStack.isEmpty())
			return undoStack.pop();
		else
			return null;
	}
	
	public MemoriCalendar redo(){
		if(!redoStack.isEmpty())
			return redoStack.pop();
		else
			return null;
	}
	
	public void addToUndo(MemoriCalendar c){
		undoStack.add(c);
		redoStack = new Stack<MemoriCalendar>();
	}


}
