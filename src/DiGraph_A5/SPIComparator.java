package DiGraph_A5;

import java.util.Comparator;

class SPIComparator implements Comparator<ShortestPathInfo>{

	@Override
	public int compare(ShortestPathInfo input1, ShortestPathInfo input2) {
		if (input1.getTotalWeight() < input2.getTotalWeight()) {
			return -1;
		} else if (input1.getTotalWeight() > input2.getTotalWeight()) {
			return 1;
		} else {
			return 0;
		}
	} 
	
}

