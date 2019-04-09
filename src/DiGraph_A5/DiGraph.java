package DiGraph_A5;
import java.util.*;
public class DiGraph implements DiGraphInterface {
	
	int numVert = 0;
	int numEdg = 0;
	
	HashMap<String, Vertex> labeltovertex = new HashMap<String, Vertex>();
	HashMap<Long, String> idnumtolabel = new HashMap<Long, String>();
	HashMap<String, Edge> labeltoedge = new HashMap<String, Edge>();
	HashMap<Long, String> idnumtoedgelabel = new HashMap<Long, String>();

	

	public DiGraph() { 
		
	}

	@Override
	public boolean addNode(long idNum, String label) {
		if (label == null || labeltovertex.containsKey(label)) {
			return false;
		} else if (idnumtolabel.containsKey(idNum) || idNum < 0) {
			return false;
		} else {
			Vertex vertex = new Vertex(idNum, label);
			idnumtolabel.put(idNum, label);
			labeltovertex.put(label, vertex);
			numVert++;
			return true;
		}
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		Edge path = new Edge(idNum, sLabel, dLabel, weight, eLabel);
		if (idNum < 0 || sLabel == null || dLabel == null) {
			return false;
		} else if (idnumtoedgelabel.containsKey(idNum)) {
			return false; 
		} else if (!labeltovertex.containsKey(sLabel) || !labeltovertex.containsKey(dLabel)) {
			return false;
		} else if (labeltoedge.containsKey(sLabel + dLabel)) {
			return false;
		} else {
			HashMap<String, Edge> SEHmap = labeltovertex.get(sLabel).getSEHmap();
			HashMap<String, Edge> DEHmap = labeltovertex.get(dLabel).getDEHmap();
			
			idnumtoedgelabel.put(idNum, sLabel + dLabel);
			labeltoedge.put(sLabel + dLabel, path);
			
			SEHmap.put(sLabel + dLabel,path);
			DEHmap.put(sLabel + dLabel,path);
			numEdg++;
			return true;
		}
	}

	@Override
	public boolean delNode(String label) {
		if (label == null) {
			return false;
		} else if (!labeltovertex.containsKey(label)) {
			return false;
		} else {
			for (HashMap.Entry<String,Edge> edge : labeltovertex.get(label).getSEHmap().entrySet()) {
				HashMap<String,Edge> DEHmap = labeltovertex.get(edge.getValue().getsLabel()).getDEHmap();
				HashMap<String,Edge> SEHmap = labeltovertex.get(label).getSEHmap();

				idnumtoedgelabel.remove(edge.getValue().getID());
				labeltoedge.remove(edge.getKey());

				DEHmap.remove(edge.getKey());
				SEHmap.remove(edge.getKey());
				numEdg--;
			}
			for (HashMap.Entry<String,Edge> edge : labeltovertex.get(label).getDEHmap().entrySet()) {
				HashMap<String,Edge> SEHmap = labeltovertex.get(edge.getValue().getsLabel()).getSEHmap();
				HashMap<String,Edge> DEHmap = labeltovertex.get(label).getDEHmap();

				idnumtoedgelabel.remove(edge.getValue().getID());
				labeltoedge.remove(edge.getKey());

				SEHmap.remove(edge.getKey());
				DEHmap.remove(edge.getKey());
				numEdg--;
			}
			idnumtolabel.remove(labeltovertex.get(label).getID());
			labeltovertex.remove(label);
			numVert--;
			return true;
		}
	}


	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		if (sLabel == null || dLabel == null) {
			return false;
		} else if (!labeltoedge.containsKey(sLabel + dLabel)) {
			return false;
		} else {
			HashMap<String,Edge> SEHmap = labeltovertex.get(sLabel).getSEHmap();
			HashMap<String,Edge> DEHmap = labeltovertex.get(dLabel).getDEHmap();
			
			idnumtoedgelabel.remove(labeltoedge.get(sLabel + dLabel).getID());
			labeltoedge.remove(sLabel + dLabel);
			
			SEHmap.remove(sLabel + dLabel);
			DEHmap.remove(sLabel + dLabel);
			numEdg--;
			return true;
		}
	}

	@Override
	public long numNodes() {
		return numVert;
	}

	@Override
	public long numEdges() {
		return numEdg;
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		ShortestPathInfo source = new ShortestPathInfo(label, 0);
		
		HashMap<String, ShortestPathInfo> visited = new HashMap<String, ShortestPathInfo>();
		HashMap<String, ShortestPathInfo> todo = new HashMap<String, ShortestPathInfo>();
		PriorityQueue<ShortestPathInfo> pqueue = new PriorityQueue<ShortestPathInfo>(20, new SPIComparator());
			
		pqueue.add(source);
		todo.put(label, source);
		while (pqueue.size() > 0) {
			ShortestPathInfo current = pqueue.poll();
			String currentKey = current.getDest();
			long currentweight = current.getTotalWeight();
			
			todo.remove(currentKey);
			
			if (visited.containsKey(currentKey)) {
				if (visited.get(currentKey).getTotalWeight() > current.getTotalWeight()) {
					visited.put(currentKey, current);
				} else {
					//do nothing
				}
			} else {
				visited.put(currentKey, current);
			}
			
			//for loop iterator to go through adjacent cells
			for (HashMap.Entry<String, Edge> edge : labeltovertex.get(currentKey).getSEHmap().entrySet()) {
				ShortestPathInfo destPath = new ShortestPathInfo(edge.getValue().getdLabel(), edge.getValue().getWeight() + currentweight);
				
				if (todo.containsKey(destPath.getDest())) {
					if (destPath.getTotalWeight() < todo.get(destPath.getDest()).getTotalWeight()) {
						//if the current path being explored is less than the previously stored one, then store the new one
						todo.put(destPath.getDest(), destPath);
						pqueue.add(destPath);
					} else {
						//do nothing
					}
				} else if (!visited.containsKey(destPath.getDest())){
					//if a path does not exist to this adjacent cell create the hash for it to check it's paths
					todo.put(destPath.getDest(), destPath);
					pqueue.add(destPath);
				} else {
					//do nothing
				}
			}
		}
		
		ShortestPathInfo[] pathArray = new ShortestPathInfo[numVert];
		int i = 0;
		for (Map.Entry<String, Vertex> entry : labeltovertex.entrySet()) {
			if (visited.containsKey(entry.getKey())) {
				pathArray[i] = visited.get(entry.getKey());
			} else {
				pathArray[i] = new ShortestPathInfo(entry.getKey(), -1);
			}
			i++;
		}
		return pathArray;
	}
}



