//@@author A0108454H
package memori.parsers;


public class SortParser extends FieldsParser{
	public boolean legitField = false;
	public int numberOfSorts = 0;
	private String INVALID_MESSAGE = "Oops,invalid sort format.Please try again."+"\n";
	public SortParser(){
		init();
	}
	@Override
	//change does not allow user to type in more than one fields
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		extractFields(cmdFields);
		UpdateFilledFields();
		updateLegitField();
		if((legitField == false)||(numberOfSorts > 1)){
			return new MemoriCommand(INVALID_MESSAGE);
		}
		return new MemoriCommand(cmdType,FilledFields);
	}
	public void updateLegitField(){
		for(int i = 0;i < FilledFields.length; i++){
			if(FilledFields[i]==true){
				legitField = true; 
				numberOfSorts++;
			}
		}
		
	}
}
