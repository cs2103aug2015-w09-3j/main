//@@author A0108454H
package memori.parsers;

import java.util.ArrayList;


public class IndexesParser extends FieldsParser {
	public String INVALID_MESSAGE = "Oops, index ares not available,please try again"+"\n";
    public String RANGE_SPLITTER = "-";
    public ArrayList<Integer> indexes = new ArrayList<Integer>();
    public IndexesParser() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		indexes = new ArrayList<Integer>();
		try{
			if(!cmdFields.contains(RANGE_SPLITTER)){
				splitIndividualIndex(cmdFields);
				return new MemoriCommand(cmdType,indexes);
			}else{
				addRange(cmdFields);
				return new MemoriCommand(cmdType,indexes);
			}
			
		}catch(NumberFormatException e){
			return new MemoriCommand(INVALID_MESSAGE);
		}catch(wrongIndexFormat e){
			return new MemoriCommand(INVALID_MESSAGE);
		}
	}	
	public void splitIndividualIndex(String cmdFields){
		
		for(int index = 0; index <cmdFields.length();index++){
			if(cmdFields.charAt(index)!=' '){
				int nextSpace = findNextSpace(index,cmdFields);
				int indexToDelete = Integer.parseInt(cmdFields.substring(index,nextSpace).replaceAll(" ",""));
				if(!indexes.contains(indexToDelete)){
					indexes.add(indexToDelete);
				}
				index = nextSpace;
			}
		}
	}
	public int findNextSpace(int index,String cmdFields){
		for(;index<cmdFields.length();index++){
			if(cmdFields.charAt(index)==' '){
				return index;
			}
		}
		return index;
	}
	public void addRange(String cmdFields) throws wrongIndexFormat{
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
			addToindexes(rangeIndex1,rangeIndex2);
		}
	}

	public void addToindexes(int rangeIndex1,int rangeIndex2){
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
			indexes.add(lower);
		}
	}
	
}
