package memori;

import java.util.Date;

public class SortParser extends FieldsParser{
	public boolean legitField = false;
	private String INVALID_MESSAGE = "Oops,invalid sort format.Please try again."+"\n";
	public SortParser(){
		init();
	}
	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		extractFields(cmdFields);
		UpdateFilledFields();
		updateLegitField();
		if(legitField == false){
			return new MemoriCommand(INVALID_MESSAGE);
		}
		return new MemoriCommand(cmdType,FilledFields);
	}
	public void updateLegitField(){
		for(int i = 0;i < FilledFields.length; i++){
			if(FilledFields[i]==true){
				legitField = true; 
			}
		}
		
	}
}
