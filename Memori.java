package memori;

import memori.Storage.MemoriSettings;
import memori.Storage.Storage;
import memori.googleSync.GoogleSync;
import memori.logic.MemoriCalendar;
import memori.logic.MemoriLock;
import memori.parsers.MemoriCommand;
import memori.parsers.MemoriCommandType;
import memori.parsers.MemoriParser;
import memori.ui.MemoriUI;

public class Memori {
	private MemoriUI ui = new MemoriUI();
	private Storage st = Storage.getInstance();
	private MemoriCalendar memoriCalendar;
	private MemoriParser memoriParser = new MemoriParser();
	private MemoriSettings memoriSettings = MemoriSettings.getInstance();
	private GoogleSync googleSync = new GoogleSync();

	private static String WELCOME_MSG = " ____    ____  ________  ____    ____   ___   _______     _____  "
			+ "\n|_   \\  /   _||_   __  ||_   \\  /   _|.'   `.|_   __ \\   |_   _|"
			+ "\n  |   \\/   |    | |_ \\_|  |   \\/   | /  .-.  \\ | |__) |    | |"
			+ "\n  | |\\  /| |    |  _| _   | |\\  /| | | |   | | |  __ /     | |"
			+ "\n  | |_\\/_| |_  _| |__/ | _| |_\\/_| |_\\  `-'  /_| |  \\ \\_  _| |_"
			+ "\n|_____||_____||________||_____||_____|`.___.'|____| |___||_____| \n\n";

	private static final String COMMAND_PROMPT = "Command:";

	public void run() {
		setup();
		executeCommands();
	}

	public void executeCommands() {
		while (true) {
			ui.displayToUser(COMMAND_PROMPT);
			String userInput = ui.takeInput();
			if (userInput.equals("exit"))
				System.exit(0);
			MemoriCommand command = memoriParser.parse(userInput);
			String ack = memoriCalendar.execute(command, googleSync);
			ui.displayToUser(memoriCalendar.display());
			ui.displayToUser(ack);
			st.saveCalendar(memoriCalendar);
		}
	}

	public void setup() {
		Thread memoriLockThread = new Thread(new MemoriLock());
		// memoriLockThread.start();
		memoriSettings = st.loadSettings();
		memoriCalendar = st.loadCalendar();
		if (memoriCalendar == null) {
			memoriCalendar = new MemoriCalendar();
		}
		memoriCalendar.initialize();
		googleSync.SetUp(ui, memoriCalendar);
		st.saveCalendar(memoriCalendar);
		ui.displayToUser(WELCOME_MSG);
		ui.displayToUser(memoriCalendar.display());
	}

	private boolean isCalendarCommand(MemoriCommand cmd) {
		MemoriCommandType type = cmd.getType();
		switch (type) {
			case ADD:
			case UPDATE:
			case DELETE:
			case SEARCH:
			case SORT:
			case COMPLETE:
			case READ:
				return true;
			default:
				return false;
		}
	}

	public static void main(String[] args) {
		Memori memori = new Memori();
		memori.run();
	}
}