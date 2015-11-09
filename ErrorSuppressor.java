//@@author A0098038W
package memori;

import java.io.OutputStream;
import java.io.PrintStream;

public class ErrorSuppressor {
	public static PrintStream originalErr = System.err;
	
	/**Redirects the System.err to another stream */
	public static void supress(){
		System.setErr(new PrintStream(new OutputStream() {
		    public void write(int b) {
		    }
		}));
	}
	/**Redirects back the System.err */
	public static void unsupress(){
		System.setErr(originalErr);
	}
}
