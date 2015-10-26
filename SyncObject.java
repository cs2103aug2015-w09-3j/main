package memori;

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
