//@@author A0098038W
package memori.googleSync;

import java.util.LinkedList;
import java.util.Queue;

/** Just a wrapper class to facilitate GSON library when converting to JSON */
public class SyncObjectQueue {
	public Queue<SyncObject> theQueue;

	public SyncObjectQueue() {
		theQueue = new LinkedList<SyncObject>();
	}

}
