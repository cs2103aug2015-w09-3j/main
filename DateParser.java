package memori;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {
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
		List<Date> dateList = new ArrayList<Date>();
		List<DateGroup> groups = parser.parse(dateToParse);
		if (!groups.isEmpty()) {
			DateGroup dg = groups.get(0);
			dateList.addAll(dg.getDates());
			return dateList.get(0);
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
					if (splitted[2].compareTo(splitted[0])< 0) {
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

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String dateToParse = sc.nextLine();
			System.out.println(parseDate(dateToParse));
		}
	}
}
