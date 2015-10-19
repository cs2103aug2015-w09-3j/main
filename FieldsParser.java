package memori;

import java.util.Arrays;
import java.util.Date;

public abstract class FieldsParser {
	//** change from private to protected
	protected static final String[] FIELD_IDENTIFIERS = {"-n","-s","-e","-l","-d","-p"};
	protected static final int NAME_INDEX = 0;
	protected static final int START_INDEX = 1;
	protected static final int END_INDEX = 2;
	protected static final int LOCATION_INDEX = 3;
	protected static final int DESCRIPTION_INDEX = 4;
	protected static final int PRIORITY_INDEX = 5;
	
	protected Boolean[] FilledFields = new Boolean[FIELD_IDENTIFIERS.length];
	protected Field[] fields= new Field[FIELD_IDENTIFIERS.length];
	public abstract MemoriCommand parse(MemoriCommandType cmdType, String fields);
	
	protected void init(){
		for(int i=0;i<fields.length;i++){
			FilledFields[i] = false;
			fields[i] = new Field(FIELD_IDENTIFIERS[i]);
		}
	}
	
	public void showFields(){
		for(Field f: fields){
			System.out.println(f);
		}
	}
	//able to extract location and priority
	protected String[] extractStrings(){
		String[] strFields = new String[MemoriCommand.NUM_STRING_FIELDS];
		
		for(Field f: fields){
			if(f.getName().equals(FIELD_IDENTIFIERS[NAME_INDEX])){
				strFields[MemoriCommand.NAME_INDEX] = f.getContent();
				FilledFields[NAME_INDEX] = true;
			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[LOCATION_INDEX])){
				strFields[MemoriCommand.LOCATION_INDEX] = f.getContent();
				FilledFields[LOCATION_INDEX] = true; 
			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[DESCRIPTION_INDEX])){
				strFields[MemoriCommand.DESCRIPTION_INDEX] = f.getContent();
				FilledFields[DESCRIPTION_INDEX] = true;
			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[PRIORITY_INDEX])){
				strFields[MemoriCommand.PRIORITY_INDEX] = f.getContent();
				FilledFields[PRIORITY_INDEX] = true;
			}
			
		}
		return strFields;
	}
	
	protected Date[] extractDates(){
		Date[] startEnd = new Date[2];
		for(Field f: fields){
			if(f.getName().equals(FIELD_IDENTIFIERS[START_INDEX])){
				startEnd[0] = DateParser.parseDate(f.getContent());
				FilledFields[START_INDEX] = true;
			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[END_INDEX])){
				startEnd[1] = DateParser.parseDate(f.getContent());
				FilledFields[END_INDEX] = true;
			}
			
		}
		if(startEnd[0]!= null && startEnd[1] != null ){
			Arrays.sort(startEnd);
		}
		return  startEnd;
	}
	
	protected void extractFields(String toExtract){
		int i =0;
		for(; i < fields.length ; i++ ){
			fields[i].setIndexInString(toExtract.indexOf(FIELD_IDENTIFIERS[i]));
		}
		Arrays.sort(fields);
		for(i=0; i < fields.length ; i++ ){
			if(fields[i].getIndexInString() < 0)
				continue;
			int start = fields[i].getIndexInString() + fields[i].getName().length();
			int end;
			if(i< fields.length - 1){
				end = fields[i+1].getIndexInString();
			}
			else{
				end = toExtract.length();
			}
			fields[i].setContent(toExtract.substring(start,end).trim());
		}
	}

}
