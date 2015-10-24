package memori;

import java.util.Scanner;

public class MemoriUI {
	private static final int CUT_OFF_LENGTH = 91;
	private static final char NEW_LINE = '\n';
	private static String WELCOME_MSG = " ____    ____  ________  ____    ____   ___   _______     _____  " +
										"\n|_   \\  /   _||_   __  ||_   \\  /   _|.'   `.|_   __ \\   |_   _|"+
										"\n  |   \\/   |    | |_ \\_|  |   \\/   | /  .-.  \\ | |__) |    | |"+   
										"\n  | |\\  /| |    |  _| _   | |\\  /| | | |   | | |  __ /     | |"+   
										"\n  | |_\\/_| |_  _| |__/ | _| |_\\/_| |_\\  `-'  /_| |  \\ \\_  _| |_"+  
										"\n|_____||_____||________||_____||_____|`.___.'|____| |___||_____| ";
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
		System.out.println(output);
	}

	public String takeInput() {
		return sc.nextLine();
	}
	
	public static void main(String[] args){
		System.out.println(WELCOME_MSG);
	}
}
