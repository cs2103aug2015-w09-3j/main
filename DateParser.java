package memori;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {
	private static boolean invertMonth = MemoriSettings.getInstance().getInvertMonth();
	public static final String[] regex = { "/", "-" };
	private static int STANDARD_HOUR = 9;
	private static int STANDARD_MIN = 0;
	private static int STANDARD_SEC = 0;
	private static String EXPLICIT_TIME = "EXPLICIT_TIME";
	private static String RELATIVE_TIME = "RELATIVE_TIME";

	public static void setInvert(boolean status) {
		invertMonth = status;
	}

	public static Date parseDate(String dateToParse) {
		if (invertMonth) {
			dateToParse = reverseMonth(dateToParse);
		}
		ErrorSuppressor.supress();
		Parser parser = new Parser();
		ErrorSuppressor.unsupress();
		List<Date> dateList = new ArrayList<Date>();
		List<DateGroup> groups = parser.parse(dateToParse);
		if (!groups.isEmpty()) {

			DateGroup dg = groups.get(0);
			String syntaxTree = dg.getSyntaxTree().toStringTree();
			dateList.addAll(dg.getDates());
			if ((!syntaxTree.contains(EXPLICIT_TIME)) && (!syntaxTree.contains(RELATIVE_TIME))) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateList.get(0));
				calendar.set(Calendar.HOUR_OF_DAY, STANDARD_HOUR);
				calendar.set(Calendar.MINUTE, STANDARD_MIN);
				calendar.set(Calendar.SECOND, STANDARD_SEC);
				return calendar.getTime();
			}

			return dateList.get(0);
		}
		return null;
	}

	private static String reverseMonth(String dateToParse) {
		String[] splitted = {};
		StringBuilder inverted = new StringBuilder();
		for (String rx : regex) {
			splitted = dateToParse.split(rx);
			if (splitted.length == 2) {
				inverted.append(splitted[1]);
				inverted.append(rx);
				inverted.append(splitted[0]);
				return inverted.toString();
			} else if (splitted.length == 3) {
				try {
					if (Integer.parseInt(splitted[0]) <= 31) {
						inverted.append(splitted[1]);
						inverted.append(rx);
						inverted.append(splitted[0]);
						inverted.append(rx);
						inverted.append(splitted[2]);
						return inverted.toString();
					}
				} catch (NumberFormatException e) {
					return dateToParse;
				}
			}

		}

		return dateToParse;
	}
}
