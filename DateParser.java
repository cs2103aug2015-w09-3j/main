package memori;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {
	

	public static void main(String[] args) {

		Parser parser = new Parser();

		Scanner sc = new Scanner(System.in);
		while (true) {
			String dateToParse = sc.nextLine();
			List<Date> dateList = new ArrayList<Date>();
			List<DateGroup> groups = parser.parse(dateToParse);
			for (DateGroup group : groups) {
				List<Date> dates = group.getDates();
				int line = group.getLine();
				int column = group.getPosition();
				String matchingValue = group.getText();
				String syntaxTree = group.getSyntaxTree().toStringTree();
				Map parseMap = group.getParseLocations();
				boolean isRecurreing = group.isRecurring();
				Date recursUntil = group.getRecursUntil();

				/*
				 * if any Dates are present in current group then add them to
				 * dateList
				 */
				if (group.getDates() != null) {
					dateList.addAll(group.getDates());
				}

			}
			System.out.println(dateList.get(0));
		}
	}
}
