package memori;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {
	

	public DateParser(){
		
	}
	public Date getDate(String dateToParse){
		
		Parser parser = new Parser();
		List<Date> dateList = new ArrayList<Date>(); 
		List<DateGroup> groups = parser.parse(dateToParse);
		for(DateGroup group : groups){
			List<Date> dates = group.getDates();
			int line = group.getLine();
			int column = group.getPosition();
			String matchingValue = group.getText();
			String syntaxTree = group.getSyntaxTree().toStringTree();
			Map parseMap = group.getParseLocations();
			boolean isRecurring = group.isRecurring();
			Date recursUntil = group.getRecursUntil();
		
			if(group.getDates() != null){
				dateList.addAll(group.getDates());
			}
		
		}
		return dateList.get(0);
	
	}

}