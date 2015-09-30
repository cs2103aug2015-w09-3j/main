package memori;

import java.util.Scanner;

public class MemoriUI {
	Scanner sc = new Scanner(System.in);
	
	public void displayToUser(String msg){
		System.out.println(msg);
	}
	
	public String takeInput(){
		return sc.nextLine();
	}
}
