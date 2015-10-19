package memori;

public class DeleteParser extends FieldsParser {

	public DeleteParser() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		try{
			int index = Integer.parseInt(cmdFields);
			return new MemoriCommand(cmdType,index);
		}catch(NumberFormatException e){
			
			return new MemoriCommand();
		}
		
	}

}
