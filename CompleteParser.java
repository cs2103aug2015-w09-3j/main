package memori;

import java.util.ArrayList;

public class CompleteParser extends FieldsParser{
	private ArrayList<Integer>completeIndex;
	private String INVALID_MESSAGE = "Oops, indexs are not available,please try again";
	@Override
	
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		//handle delete 1 2 3 case
		completeIndex = new ArrayList<Integer>();
		try{
			if(!cmdFields.contains("-")){
				String[] index = cmdFields.split(" ");
				for(int i = 0; i<index.length;i++){
					int toBeAdded = Integer.parseInt(index[i]);
					//check for duplicate
					if(!completeIndex.contains(toBeAdded)){
						completeIndex.add(toBeAdded);
					}	
				}
				
				return new MemoriCommand(cmdType,completeIndex);
			}
		//handle delete 1-12 case;
			if(cmdFields.contains("-")){
				String[] ranges = cmdFields.split(" ");
				for(int i = 0; i<ranges.length;i++){
					String[] lowerUpper = ranges[i].split("-");
					if(lowerUpper.length>1){
						int index1 = Integer.parseInt(lowerUpper[0]);
						int index2 = Integer.parseInt(lowerUpper[1]);
					//determine the upper bound of each range
						if(index1<index2){
							insertIndex(index2,index1);
						}else if(index1>index2){
							insertIndex(index1,index2);
						}else{
							completeIndex.add(index1,index2);
						}
					}else{
						int toBeAdded = Integer.parseInt(lowerUpper[0]);
						if(!completeIndex.contains(toBeAdded)){
							completeIndex.add(toBeAdded);
						}
					}
				}
			}
			
			return new MemoriCommand(cmdType,completeIndex);
			
		}catch(NumberFormatException e){
			return new MemoriCommand(INVALID_MESSAGE);
		}
	}
	public void insertIndex(int upper,int lower){
		for(int i = lower; i<upper+1;i++){
			
			//check for duplicate
			if(!completeIndex.contains(i)){
				completeIndex.add(i);
			}	
		}
	}
	public void print(){
		for(int i = 0;i<completeIndex.size();i++){
			System.out.println("index"+completeIndex.get(i));
		}
		
	}
}