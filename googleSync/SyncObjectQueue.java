package memori.googleSync;

import java.util.LinkedList;
import java.util.Queue;
//wrapper class;
public class SyncObjectQueue {
	public Queue<SyncObject> theQueue;
	
	public SyncObjectQueue() {
		theQueue = new LinkedList<SyncObject>();
	}

}
