//@@author A0121262X
package memori.logic;

import java.net.*;

public class MemoriLock implements Runnable{
	public MemoriLock() {
		//testAndCreateLock();
	}


	public void testAndCreateLock() {
		try {
			ServerSocket ss = new ServerSocket();
		  ss.bind(new InetSocketAddress(12345));
		  System.out.println("Application started");
		  Thread.sleep(1000000000);
		  ss.close();
		} catch (SocketException e) {
		  System.out.println("Application already running");
		  System.exit(1);
		} catch(Exception e) {
		  System.out.println("Application encountered some problem.");
		  System.exit(1);
		}
	}


	@Override
	public void run() {
		testAndCreateLock();
	}
}