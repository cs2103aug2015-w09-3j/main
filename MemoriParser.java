package memori;

import java.util.Arrays;

public class MemoriParser {
	
	// commandConfig Array indexes
	private static final int USER_CMD = 0;
	private static final int USER_ARGS = 1;
	
	private static final int NAME = MemoriCommand.NAME;
	private static final int START = MemoriCommand.START;
	private static final int END =  MemoriCommand.END;
	private String[] cmdArgs = new String[3];
	
	public MemoriCommand parse(String userInput) {
		String[] commandConfig = seperateCommand(userInput);
		seperateArguements(commandConfig[USER_ARGS]);
		return new MemoriCommand(commandConfig[USER_CMD],cmdArgs);
	}
	
	public String seperateArguements(String s){
		String[] args = s.split(":",2);
		if(args.length <= 1){
			return s;
		}
		else{
			String arg = seperateArguements(args[1]);
			String[] splitted = reverseSplit(args[0]);
			splitted[0] = splitted[0].toUpperCase();
		
			if(splitted[0].equals("NAME"))
				cmdArgs[NAME] = arg;
			else if(splitted[0].equals("START"))
				cmdArgs[START] = arg;
			else if(splitted[0].equals("END"))
				cmdArgs[END] = arg;
			
			if(splitted.length > 1){
				return splitted[1];
			}
			else{
				System.out.println(Arrays.toString(cmdArgs));
				return null;
			}
		}
	}
	
	private String[] reverseSplit(String s) {
		String reverse = new StringBuilder(s).reverse().toString();
		String[] splitted = reverse.split(" ",2);
		for(int i=0;i<splitted.length;i++){
			splitted[i] = new StringBuilder(splitted[i]).reverse().toString();
		}
		return splitted;
	}

	private static String[] seperateCommand(String userInput) {
		return userInput.split(" ",2);
	}
	
	public static void main(String[] args){
		MemoriParser parser = new  MemoriParser();
		String userinput = "name:abc start:tomorrow end:yesterday";
		parser.seperateArguements(userinput);
	}
	
}
