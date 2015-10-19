//test for parser
package memori;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class test {
	private static String[] FIELD_IDENTIFIERS = {"-n","-s","-e","-l","-d","-p"};
	protected static Field[] fields= new Field[FIELD_IDENTIFIERS.length];
	private static int START_INDEX = 1;
	private static int END_INDEX = 2;
	public static void main(String[] args){
		String toExtract = "";
		init();
		extractFields(toExtract);
		String[] nameAndDescription = extractStrings();
		printIndex(nameAndDescription);
		printFields();
		Date[] startEnd = extractDates();
		printDate(startEnd);
		printException(startEnd);
	}
	public static void printFields(){
		for(int i = 0; i < fields.length; i++){
			System.out.println(fields[i].getName()+fields[i].getContent());
		}
	}
	public static void printException(Date[] startEnd){
		boolean haveStart = false;
		boolean haveEnd = false;
		for(int i = 0; i < fields.length; i++){
			String name = fields[i].getName();
			if(name.equals("-s")){
				haveStart = true;
			}
			if(name.equals("-e")){
				haveEnd = true;
			}
		}
		if(((haveStart==true)&&startEnd[0]==null)){
			
		}
	}
	public static void printDate(Date[] startEnd){
		for(int i = 0; i < startEnd.length; i++){
			System.out.println(startEnd[i]);
		}
	}
	protected static Date[] extractDates(){
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
	protected static void init(){
		for(int i=0;i<fields.length;i++){
			fields[i] = new Field(FIELD_IDENTIFIERS[i]);
		}
	}
	protected static void printIndex(String[] s){
		System.out.println(s.length);
		for(int i=0;i<s.length;i++){
			System.out.println(s[i]);
		}		
	}
	protected static void extractFields(String toExtract){
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
	
	protected static String[] extractStrings(){
		String[] strFields = new String[6];
		for(Field f: fields){
			if(f.getName().equals(FIELD_IDENTIFIERS[0])){
				strFields[MemoriCommand.NAME_INDEX] = f.getContent();
			}
			else if(f.getName().equals(FIELD_IDENTIFIERS[4])){
				strFields[5] = f.getContent();
			}
		}
		return strFields;
	}
	
}
