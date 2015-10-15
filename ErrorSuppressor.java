package memori;

import java.io.OutputStream;
import java.io.PrintStream;

public class ErrorSuppressor {
	public static PrintStream originalErr = System.err;
	
	public static void supress(){
		System.setErr(new PrintStream(new OutputStream() {
		    public void write(int b) {
		    }
		}));
	}
	
	public static void unsupress(){
		System.setErr(originalErr);
	}
}
