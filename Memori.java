package memori;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Memori {
	private MemoriUI  ui = new MemoriUI();
	private Storage st = new Storage();
	MemoriCalendar memoriCalendar = new MemoriCalendar();
	MemoriParser  memoriParser = new MemoriParser();
	
	private static final String WELCOME_MSG = "Welcome to Memori";
	private static final String COMMAND_PROMPT ="command:";
	
	public void run(){
		setup();
		executeCommands();
	}
	
	public void executeCommands(){
		while(true){
			ui.displayToUser(COMMAND_PROMPT);
			String userInput = ui.takeInput();			
			MemoriCommand command = memoriParser.parse(userInput);
			memoriCalendar.execute(command);
			ui.displayToUser(memoriCalendar.display());
			st.saveCalendar(memoriCalendar);
		}
	}
	
	public void setup(){
		memoriCalendar = st.loadCalendar();
		ui.displayToUser(WELCOME_MSG);
		ui.displayToUser(memoriCalendar.display());
	}
	
	
	public static void main(String[] args){
		Memori memori = new Memori();
		memori.run();
	}
}
