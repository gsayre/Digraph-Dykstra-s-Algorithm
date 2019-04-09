package DiGraph_A5;

import java.util.HashMap;

public class Vertex {
	

	HashMap<String, Edge> sLabelEdges = new HashMap<String, Edge>();
	HashMap<String, Edge> dLabelEdges = new HashMap<String, Edge>();
	
	long numID;
	String label;
	
	public Vertex(long numID, String label) {
		this.numID = numID;
		this.label = label;
	}
	

	public Long getID() {
		return numID;
	}

	public String getLabel() {
		return label;
	}

	public HashMap<String, Edge> getSEHmap() {
		return sLabelEdges;
	}

	public HashMap<String, Edge> getDEHmap() {
		return dLabelEdges;
	}
	

}
