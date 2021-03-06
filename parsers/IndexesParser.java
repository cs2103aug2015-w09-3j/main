//@@author A0108454H
package memori.parsers;

import java.util.ArrayList;


public class IndexesParser extends FieldsParser {
	public static final String INVALID_MESSAGE = "Oops, index ares not available,please try again"+"\n";
    private static final String RANGE_SPLITTER = "-";
    private static ArrayList<Integer> INDEXES = new ArrayList<Integer>();
    public IndexesParser() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		INDEXES = new ArrayList<Integer>();
		try{
			if(!cmdFields.contains(RANGE_SPLITTER)){
				splitIndividualIndex(cmdFields);
				return new MemoriCommand(cmdType,INDEXES);
			}else{
				addRange(cmdFields);
				return new MemoriCommand(cmdType,INDEXES);
			}
			
		}catch(NumberFormatException e){
			return new MemoriCommand(INVALID_MESSAGE);
		}catch(wrongIndexFormat e){
			return new MemoriCommand(INVALID_MESSAGE);
		}
	}	
	private void splitIndividualIndex(String cmdFields){
		
		for(int index = 0; index <cmdFields.length();index++){
			if(cmdFields.charAt(index)!=' '){
				int nextSpace = findNextSpace(index,cmdFields);
				int indexToDelete = Integer.parseInt(cmdFields.substring(index,nextSpace).replaceAll(" ",""));
				if(!INDEXES.contains(indexToDelete)){
					INDEXES.add(indexToDelete);
				}
				index = nextSpace;
			}
		}
	}
	private int findNextSpace(int index,String cmdFields){
		for(;index<cmdFields.length();index++){
			if(cmdFields.charAt(index)==' '){
				return index;
			}
		}
		return index;
	}
	private void addRange(String cmdFields) throws wrongIndexFormat{
		int rangeIndex1;
		int rangeIndex2;
		int ExpectedNoOfNumbers = 2;
		String[] split = cmdFields.split(RANGE_SPLITTER);
		if((cmdFields.length() - cmdFields.replaceAll(RANGE_SPLITTER,"").length())>1){
			throw new wrongIndexFormat();
		}else if(split.length!=ExpectedNoOfNumbers){
			throw new wrongIndexFormat();
		}else{
			rangeIndex1 = Integer.parseInt(split[0].trim());
			rangeIndex2 = Integer.parseInt(split[1].trim());
			addToIndexes(rangeIndex1,rangeIndex2);
		}
	}

	private void addToIndexes(int rangeIndex1,int rangeIndex2){
		int upper;
		int lower;
		if(rangeIndex1 > rangeIndex2){
			upper = rangeIndex1;
			lower = rangeIndex2;
			
		}else{
			upper = rangeIndex2;
			lower = rangeIndex1;
		
		}
		for(;lower<upper+1;lower++){
			INDEXES.add(lower);
		}
	}
	
}
