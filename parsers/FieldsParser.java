//@@author A0108454H
package memori.parsers;

import java.util.Arrays;
import java.util.Date;

public abstract class FieldsParser {
	//** change from private to protected
	protected static final String[] FIELD_IDENTIFIERS = {"-n","-s","-e","-l","-d"};
	public static final int NAME_INDEX = 0;
	public static final int START_INDEX = 1;
	public static final int END_INDEX = 2;
	public static final int LOCATION_INDEX = 3;
	public static final int DESCRIPTION_INDEX = 4;
	
	
	protected Boolean[] FilledFields = new Boolean[FIELD_IDENTIFIERS.length];
	protected Field[] fields= new Field[FIELD_IDENTIFIERS.length];
	public abstract MemoriCommand parse(MemoriCommandType cmdType, String fields);
	
	protected void init(){
		for(int i=0;i<fields.length;i++){
			FilledFields[i] = false;
			fields[i] = new Field(FIELD_IDENTIFIERS[i]);
		}
	}
	/**
	 * This method first check the which field is stored in the Field array
	 * by comparing with the FieldIdentifiers,
	 * the content will be stored in string fields
	 * @return
	 */
	protected String[] extractStrings(){
		String[] strFields = new String[MemoriCommand.NUM_STRING_FIELDS];
		
		for(Field f: fields){
			if(f.getName().equals(FIELD_IDENTIFIERS[NAME_INDEX])){
				strFields[MemoriCommand.NAME_INDEX] = f.getContent();
			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[LOCATION_INDEX])){
				strFields[MemoriCommand.LOCATION_INDEX] = f.getContent();

			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[DESCRIPTION_INDEX])){
				strFields[MemoriCommand.DESCRIPTION_INDEX] = f.getContent();

			}
		
		}
		return strFields;
	}
	/**
	 * This method will first indicate which is the start date and the 
	 * end date. It will then parse the date to DateParser to get an date object 
	 * @return
	 */
	protected Date[] extractDates(){
		Date[] startEnd = new Date[2];
		for(Field f: fields){
			if(f.getName().equals(FIELD_IDENTIFIERS[START_INDEX])){
				startEnd[0] = DateParser.parseDate(f.getContent());
				
			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[END_INDEX])){
				startEnd[1] = DateParser.parseDate(f.getContent());
			
			}
			
		}
		if(startEnd[0]!= null && startEnd[1] != null ){
			Arrays.sort(startEnd);
		}
		return  startEnd;
	}
	/**
	 * this method find the start and the end of the search dates
	 * pass it to dateparser and process
	 * @return
	 */
	protected Date[] extractSearchDates(){
		
		String[] startEnd = new String[2];
		
		for(Field f: fields){
			if(f.getName().equals(FIELD_IDENTIFIERS[START_INDEX])){
				startEnd[0] = f.getContent();
			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[END_INDEX])){
				startEnd[1] = f.getContent();
			}
			
		}
	
		return DateParser.parseSearchDate(startEnd);
	}
	/**
	 *This has method will extract all the indexes of the position
	 *of the indicated fields in the userInput which memori allows 
	 *Then it will get all the information of the respective fields
	 *
	 * @param toExtract
	 */
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
	/**
	 *update which are the Fields the user has indicated
	 * 
	 */
	public void UpdateFilledFields(){
		
		for(int i = 0;i < fields.length;i++){
			
			if(fields[i].getIndexInString()!=-1){
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[NAME_INDEX])){
					FilledFields[NAME_INDEX]=true;
				}
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[START_INDEX])){
					FilledFields[START_INDEX]=true;
				}
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[END_INDEX])){
					FilledFields[END_INDEX] = true;
				}
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[LOCATION_INDEX])){
					FilledFields[LOCATION_INDEX] = true;
				}
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[DESCRIPTION_INDEX])){
					FilledFields[DESCRIPTION_INDEX] = true;
				}
			}
		}
	}
	
	//for testing
	public Boolean[] returnFilledFields(){
		return FilledFields;
	}
	public Field[] fields(){
		return fields;
	}
}

