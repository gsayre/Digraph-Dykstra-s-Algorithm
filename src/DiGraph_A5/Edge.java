package DiGraph_A5;

public class Edge {
	
	Long numID;
	String sLabel;
	String dLabel;
	long weight;
	String eLabel;
	
	public Edge(long numID, String sLabel, String dLabel, long weight, String eLabel) {
		this.numID = numID;
		this.sLabel = sLabel;
		this.dLabel = dLabel;
		this.weight = weight;
		this.eLabel = eLabel;	
	}
	public Long getID() {
		return numID;
	}
	
	public String getsLabel() {
		return sLabel;
	}
	
	public String getdLabel() {
		return dLabel;
	}
	
	public String geteLabel() {
		return eLabel;
	}
	
	public long getWeight() {
		return weight;
	}
}

