package memori;

public class Memori {
	private MemoriUI  ui = new MemoriUI();
	private Storage st = Storage.getInstance();
	private MemoriCalendar memoriCalendar;
	private MemoriParser  memoriParser = new MemoriParser();
	private MemoriSettings memoriSettings = MemoriSettings.getInstance();
	
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
			ui.displayToUser(memoriCalendar.execute(command));
			ui.displayToUser(memoriCalendar.display());
			st.saveCalendar(memoriCalendar);
		}
	}
	
	public void setup(){
		//memoriSettings.printSampleMessage();
		//st.printSampleMessage();
		memoriSettings = st.loadSettings();
		memoriCalendar = st.loadCalendar();
		if(memoriCalendar == null){
			memoriCalendar = new MemoriCalendar();
		}
		ui.displayToUser(WELCOME_MSG);
		ui.displayToUser(memoriCalendar.display());
	}
	
	
	public static void main(String[] args){
		Memori memori = new Memori();
		memori.run();
	}
}