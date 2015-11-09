//@@author A0098038W
package memori.googleSync;

import memori.logic.MemoriEvent;
import memori.parsers.MemoriCommand;

/**Just a wrapper class to wrap MemoriCommand and object together*/
public class SyncObject {
	/**The command that needs to be performed to the object*/
	private MemoriCommand cmd;
	/**The command that needs to be performed to the object*/
	private MemoriEvent event;
	
	
	public SyncObject(MemoriCommand cmd, MemoriEvent event) {
		this.cmd = cmd;
		this.event = event;
	}
	
	public MemoriCommand getCommand(){
		return this.cmd;
	}
	
	public MemoriEvent getEvent(){
		return this.event;
	}
}
