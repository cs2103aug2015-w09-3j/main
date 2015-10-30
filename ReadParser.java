package memori;

import java.util.ArrayList;

public class ReadParser extends FieldsParser {
	private String INVALID_MESSAGE = "Oops,  index of the line you want to read is not found."
			+ "Please try again";
	private ArrayList<Integer> readIndex;

	public ReadParser() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		// handle read 1 2 3 case
		readIndex = new ArrayList<Integer>();
		try {

			// handle read 1-12 case;

			String[] ranges = cmdFields.split(" ");
			for (int i = 0; i < ranges.length; i++) {
				String[] lowerUpper = ranges[i].split("-");
				if (lowerUpper.length > 1) {
					int index1 = Integer.parseInt(lowerUpper[0]);
					int index2 = Integer.parseInt(lowerUpper[1]);
					// determine the upper bound of each range
					if (index1 < index2) {
						insertIndex(index2, index1);
					} else if (index1 > index2) {
						insertIndex(index1, index2);
					} else {
						readIndex.add(index1, index2);
					}
				} else {
					int toBeAdded = Integer.parseInt(lowerUpper[0]);
					if (!readIndex.contains(toBeAdded)) {
						readIndex.add(toBeAdded);
					}
				}
			}

			print();
			return new MemoriCommand(cmdType, readIndex);

		} catch (NumberFormatException e) {
			return new MemoriCommand(INVALID_MESSAGE);
		}
	}

	public void insertIndex(int upper, int lower) {
		for (int i = lower; i < upper + 1; i++) {

			// check for duplicate
			if (!readIndex.contains(i)) {
				readIndex.add(i);
			}
		}
	}

	public void print() {
		for (int i = 0; i < readIndex.size(); i++) {
			System.out.println("index" + readIndex.get(i));
		}

	}
}
