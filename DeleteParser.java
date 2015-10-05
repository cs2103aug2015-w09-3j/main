package memori;

public class DeleteParser extends FieldsParser {

	public DeleteParser() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		try{
			int index = Integer.parseInt(fields);
			return new MemoriCommand(cmdType,index);
		}catch(NumberFormatException e){
			return null;
		}
		
	}

}
