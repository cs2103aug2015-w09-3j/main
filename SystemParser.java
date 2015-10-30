package memori;

public class SystemParser extends FieldsParser {
    public SystemParser(){
    	init();
    }
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		// TODO Auto-generated method stub
		System.out.println("from systemparser"+cmdType);
		return new MemoriCommand(cmdType);
	}

}
