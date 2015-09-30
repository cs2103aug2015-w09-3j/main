package memori;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemoriEvent {
	
	private static final int NAMECUTOFF = 20;
	private static final String[] ATTRIBUTESNAMES = {"name","start","end","internalId","description","externalCalId",};
	
	private String name;
	private String description;
	private String externalCalId;
	private LocalDate start;
	private LocalDate end;
	private int internalId;
	
	//blank constructor
	public MemoriEvent(String name, String startString, String endString){
		this.name = name;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.start = LocalDate.parse(startString,formatter);
		this.end = LocalDate.parse(endString,formatter);
	}
	
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getExternalCalId(){
		return externalCalId;
	}
	
	public int getInternalId(){
		return internalId;
	}
	
	public LocalDate getStart(){
		return start;
	}
	
	public LocalDate getEnd(){
		return end;
	}
	public MemoriEvent(String name,LocalDate start,LocalDate end,int internalId,String externalCalId, String description){
		this.name = name;
		this.start = start;
		this.end = end;
		this.internalId = internalId;
		this.externalCalId = externalCalId;
		this.description = description;
	}
	
	public void update(String name,LocalDate start,LocalDate end,String description){
		if(name != null)
			this.name = name;
		if(start !=null)
			this.start = start;
		if(end != null)
			this.end = end;
		if(description != null)
			this.description= description;
	}
	
	public String display(){
		StringBuilder output = new StringBuilder();
		if(this.name.length() > NAMECUTOFF){
			output.append(this.name.substring(0,NAMECUTOFF -1));
		}
		
		else{ 
			output.append(this.name);
			while(output.length()< 20)
				output.append(" ");
		}
		output.append(start);
		output.append(" ");
		output.append(end);
		return output.toString();
	}
	
	public String toJson(){
		  GsonBuilder builder = new GsonBuilder();
	      Gson gson = builder.create();
	      return gson.toJson(this);
	}
	
	public static MemoriEvent fromJSON(String json){
		MemoriEvent event = new Gson().fromJson(json, MemoriEvent.class);
		return event;
	}
	
	public boolean equals(Object obj){
		 if (obj == null) {
		        return false;
		    }
		 else if(!(obj instanceof MemoriEvent)){
			return false;
		 }
		 else{
			MemoriEvent other = (MemoriEvent) obj;
			if(this.internalId != other.getInternalId())
				return false;
			else if(!this.name.equals(other.getName()))
				return false;
			else if(!this.description.equals(other.getDescription()))
				return false;
			else if(!this.start.equals(other.start))
				return false;
			else if(!this.end.equals(other.end))
				return false;
			else
				return true;
		 }
	}
	
}
