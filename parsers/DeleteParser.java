//@@author A0108454H
package memori.parsers;

import java.util.ArrayList;

public class DeleteParser extends FieldsParser {
	private ArrayList<Integer> deleteIndex;
	public String INVALID_MESSAGE = "Oops, index ares not available,please try again";

	public DeleteParser() {
		init();
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		deleteIndex = new ArrayList<Integer>();
		try {

			while (cmdFields.contains("-")) {
				int nextSpaceIndex = -1;
				int previousSpaceIndex = 0;
				int dashIndex = cmdFields.indexOf("-") + 1;
				if (dashIndex == cmdFields.length()){

					return new MemoriCommand(INVALID_MESSAGE);
				} else {
					for (int j = dashIndex - 2; j > 0; j--) {
						if (!Character.toString(cmdFields.charAt(j))
								.equals(" ")) {
							previousSpaceIndex = findPreviousSpace(cmdFields, j);
							break;
						}
					}
					for (int i = dashIndex; i < cmdFields.length(); i++) {
						if (!Character.toString(cmdFields.charAt(i))
								.equals(" ")) {
							nextSpaceIndex = findNextSpace(cmdFields, i);
							break;
						}
					}
					if ((nextSpaceIndex == -1) || (previousSpaceIndex == -1)) {
						return new MemoriCommand(INVALID_MESSAGE);
					}
					String range = cmdFields.substring(previousSpaceIndex,
							nextSpaceIndex);
					insertRangeDeleteIndex(range);
					cmdFields = cmdFields.substring(0, previousSpaceIndex)
							+ cmdFields.substring(nextSpaceIndex,
									cmdFields.length());
				}
			}
			insertSingleDeleteIndex(cmdFields);
			
			return new MemoriCommand(cmdType,deleteIndex);
		} catch (NumberFormatException e) {
			return new MemoriCommand(INVALID_MESSAGE);
		}

	}

	public static int findPreviousSpace(String cmdFields,int notSpace){
		int i;
		for(i=notSpace;i<0;i--){
			if(Character.toString(cmdFields.charAt(i)).equals(" ")){
				return i;
			}
		}
		return i;
	}
	public static int findNextSpace(String cmdFields,int notSpace){
		int i;
		for(i=notSpace;i<cmdFields.length();i++){
			if(Character.toString(cmdFields.charAt(i)).equals(" ")){
				return i;
			}
		}
		return i;
	}
	public void insertRangeDeleteIndex(String range){
		range.replaceAll(" ","");
		String[] split = range.split("-");
		if(split.length>2){
			throw new NumberFormatException();
		}
		int index1 = Integer.parseInt(split[0].replaceAll(" ",""));
		int index2 = Integer.parseInt(split[1].replaceAll(" ",""));
		if(index1>index2){
			insertRangeArr(index1, index2);
		}else if(index2>1){
			insertRangeArr(index2,index1);
		}else if((index1==index2)&&(!deleteIndex.contains(index1))){
			deleteIndex.add(index1);
		}
	}
	private void insertRangeArr(int index1, int index2) {
		for(int i = index2;i < index1+1 ;i++){
			if(!deleteIndex.contains(i)){
				deleteIndex.add(i);
			}
		}
	}
	public  void insertSingleDeleteIndex(String cmdFields){
		String[] indexes = cmdFields.split(" ");
		for(int i = 0;i<indexes.length;i++){
			if((!indexes[i].equals(" "))&&(!indexes[i].equals(""))){
				int indexToAdd = Integer.parseInt(indexes[i].replaceAll(" ",""));
				if(!deleteIndex.contains(indexToAdd)){
					deleteIndex.add(indexToAdd);
				}
			}
		}
	}
}
