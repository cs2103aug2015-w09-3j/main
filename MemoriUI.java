package memori;

import java.util.Scanner;

public class MemoriUI {
	private static final int CUT_OFF_LENGTH = 91;
	private static final char NEW_LINE = '\n';
	private Scanner sc = new Scanner(System.in);
	
	
	public void displayToUser(String msg){
		String output = "";
		int numCharInLine = 0;
		for(int i = 0;i < msg.length();i++, numCharInLine++){
			if( msg.charAt(i) == NEW_LINE){
				numCharInLine = 0;
			}
			if(numCharInLine == CUT_OFF_LENGTH){
				output += NEW_LINE;
			}
			output += msg.charAt(i);
		}
		System.out.print(output);
	}

	public String takeInput() {
		return sc.nextLine();
	}
	
}
