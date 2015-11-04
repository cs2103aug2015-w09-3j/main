//@@author A0108454H
package memori.parsers;

public class SystemParser extends FieldsParser {
    public SystemParser(){
    	init();
    }
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String fields) {
		// TODO Auto-generated method stub
		return new MemoriCommand(cmdType);
	}

}
