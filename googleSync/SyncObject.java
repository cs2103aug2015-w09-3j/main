//@@author A0098038W
package memori.googleSync;

import memori.logic.MemoriEvent;
import memori.parsers.MemoriCommand;

public class SyncObject {
	private MemoriCommand cmd;
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
