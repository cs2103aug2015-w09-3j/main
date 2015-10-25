package memori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class test{
	protected static final String[] FIELD_IDENTIFIERS = {"-n","-s","-e","-l","-d","-p"};
	protected static final int NAME_INDEX = 0;
	protected static final int START_INDEX = 1;
	protected static final int END_INDEX = 2;
	protected static final int LOCATION_INDEX = 3;
	protected static final int DESCRIPTION_INDEX = 4;
	protected static final int PRIORITY_INDEX = 5;
	
	protected static Boolean[] FilledFields = new Boolean[FIELD_IDENTIFIERS.length];
	protected static Field[] fields= new Field[FIELD_IDENTIFIERS.length];
	
	private static boolean invertMonth = true;
	public static final String[] regex = { "/", "-" };

	public static void setInvert(boolean status) {
		invertMonth = status;
	}

	public static Date parseDate(String dateToParse) {
		if (invertMonth) {
			dateToParse = reverseMonth(dateToParse);
		}
		Parser parser = new Parser();
		List<DateGroup> groups = parser.parse(dateToParse);
		System.out.println(groups.size());
		for(DateGroup group:groups) {
		  List dates = group.getDates();
		  String matchingValue = group.getText();
		  String syntaxTree = group.getSyntaxTree().toStringTree();
		  System.out.println(syntaxTree);
		  System.out.println(dates.get(0));
		}
		return null;
	}

	private static String reverseMonth(String dateToParse) {
		String[] splitted = {};
		StringBuilder inverted = new StringBuilder();
		for (String rx : regex) {
			splitted = dateToParse.split(rx);
			if (splitted.length >= 2) {
				inverted.append(splitted[1]);
				inverted.append(rx);
				inverted.append(splitted[0]);
				if (splitted.length > 2) {
					if (splitted[2].compareTo(splitted[0]) < 0) {
						inverted.append(rx);
						inverted.append(splitted[2]);
					} else {
						inverted = new StringBuilder(dateToParse);

					}
				}

				System.out.println(inverted);
				return inverted.toString();
			}

		}

		return dateToParse;
	}
	protected static void init(){
		for(int i=0;i<fields.length;i++){
			FilledFields[i] = false;
			fields[i] = new Field(FIELD_IDENTIFIERS[i]);
		}
	}
	public static void extractFields(String toExtract){

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
	public static void updateFilledFields(){
		
		for(int i = 0;i < fields.length;i++){
			
			if(fields[i].getIndexInString()!=-1){
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[NAME_INDEX])){
					FilledFields[NAME_INDEX] = true;
					return;
				}
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[START_INDEX])){
					FilledFields[START_INDEX] = true;
					return;
				}
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[END_INDEX])){
					FilledFields[END_INDEX] = true;
					return;
				}
				if(fields[i].getName().equals(FIELD_IDENTIFIERS[LOCATION_INDEX])){
					FilledFields[LOCATION_INDEX] = true;
					return;
				}
				else{
					FilledFields[DESCRIPTION_INDEX] = true;
					return;
				}
			}
			
		}
	}
	public static void main(String[] args) {
		//Scanner sc = new Scanner(System.in);
			//explicit time dont change to 9 am
			System.out.println(parseDate("25/12/2015 12.25pm"));
			System.out.println(parseDate("25/12/2015"));
			System.out.println(parseDate("tomorrow"));
			System.out.println(parseDate("tomorrow 9am"));
			//case relative time dont change to 9 am
			System.out.println(parseDate("3 hours from now"));
		
		 	
	}
	
	
	
}
