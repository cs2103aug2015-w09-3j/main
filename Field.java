package memori;

public class Field implements Comparable<Field>{
	private String name;
	private String content;
	private int indexInString;
	
	public String getName(){
		return name;
	}
	
	public String getContent(){
		return content;
	}
	
	public int getIndexInString(){
		return  indexInString;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void setIndexInString(int index){
		this.indexInString = index;
	}
	
	
	public Field(String name){
		this.name = name;
		content = "";
		indexInString = -1;
	}

	@Override
	public int compareTo(Field other) {
		return this.indexInString - other.getIndexInString();
	}
	
	@Override
	public String toString(){
		return name + content;
	}
}
