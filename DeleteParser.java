package memori;

import java.util.ArrayList;

public class DeleteParser extends FieldsParser {
	private ArrayList<Integer> deleteIndex;
	public String INVALID_MESSAGE = "Oops, index ares not available,please try again";

	public DeleteParser() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public MemoriCommand parse(MemoriCommandType cmdType, String cmdFields) {
		// handle delete 1 2 3 case
		deleteIndex = new ArrayList<Integer>();
		try {

			// handle delete 1-12 case;

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
						deleteIndex.add(index1, index2);
					}
				} else {
					int toBeAdded = Integer.parseInt(lowerUpper[0]);
					if (!deleteIndex.contains(toBeAdded)) {
						deleteIndex.add(toBeAdded);
					}
				}
			}

			print();
			return new MemoriCommand(cmdType, deleteIndex);

		} catch (NumberFormatException e) {
			return new MemoriCommand(INVALID_MESSAGE);
		}
	}

	public void insertIndex(int upper, int lower) {
		for (int i = lower; i < upper + 1; i++) {

			// check for duplicate
			if (!deleteIndex.contains(i)) {
				deleteIndex.add(i);
			}
		}
	}

	public void print() {
		for (int i = 0; i < deleteIndex.size(); i++) {
			System.out.println("index" + deleteIndex.get(i));
		}

	}

}
