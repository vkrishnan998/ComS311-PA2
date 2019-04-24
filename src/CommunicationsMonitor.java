import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CommunicationsMonitor {

	HashMap<Integer, List<ComputerNode>> computerMapping;
	ArrayList<Triple> triplesList;
	ArrayList<ComputerNode> transmissionSeq;
	Boolean infected;

	public CommunicationsMonitor() {
		computerMapping = new HashMap<Integer, List<ComputerNode>>();
		triplesList = new ArrayList<Triple>();
		transmissionSeq = new ArrayList<ComputerNode>();
		infected = false;
	}

	public void addCommunication(int c1, int c2, int timestamp) {
		triplesList.add(new Triple(new ComputerNode(c1, timestamp), new ComputerNode(c2, timestamp), timestamp));
	}

	public void createGraph() {

		// copy arrayList into array for mergeSort
		Triple[] tripleArr = new Triple[triplesList.size()];
		for (int i = 0; i < triplesList.size(); i++) {
			tripleArr[i] = triplesList.get(i);
		}
		mergeSortTriples(tripleArr, 0, tripleArr.length - 1);

		// Loop through each triple
		for (int i = 0; i < tripleArr.length; i++) {

			// Create two nodes for every triple and add the references into their list in
			// the hashmap
			int c1 = tripleArr[i].ci.id;
			int c2 = tripleArr[i].cj.id;
			int timestamp = tripleArr[i].timestamp;

			// Add directed edge from ci to cj and cj to ci to each other neighbor lists
			tripleArr[i].ci.neighbors.add(tripleArr[i].cj);
			tripleArr[i].cj.neighbors.add(tripleArr[i].ci);

			if (!computerMapping.containsKey(c1)) {
				ArrayList<ComputerNode> list = new ArrayList<ComputerNode>();
				list.add(tripleArr[i].ci);
				computerMapping.put(c1, list);
			} else if (computerMapping.containsKey(c1) && checkDuplicate(tripleArr[i].ci, tripleArr[i].cj)) {
				int size = computerMapping.get(c1).size();
				computerMapping.get(c1).get(size - 1).neighbors.add(tripleArr[i].ci);
				computerMapping.get(c1).add(tripleArr[i].ci);
			}

			if (!computerMapping.containsKey(c2)) {
				ArrayList<ComputerNode> list = new ArrayList<ComputerNode>();
				list.add(tripleArr[i].cj);
				computerMapping.put(c2, list);
			} else if (computerMapping.containsKey(c2) && checkDuplicate(tripleArr[i].cj, tripleArr[i].ci)) {
				int size = computerMapping.get(c2).size();
				computerMapping.get(c2).get(size - 1).neighbors.add(tripleArr[i].cj);
				computerMapping.get(c2).add(tripleArr[i].cj);
			}

		}

	}

	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y) {
		infected = false;
		ComputerNode cb = new ComputerNode(c2, y);
		for (int i = 0; i < computerMapping.get(c1).size(); i++) {
			if (computerMapping.get(c1).get(i).timestamp >= x) {
				DFS(computerMapping.get(c1).get(i), cb, y);
				break;
			}
		}
		if (infected) {
			return transmissionSeq;
		}
		return null;
	}

	public void DFS(ComputerNode ca, ComputerNode cb, int y) {

		HashMap<ComputerNode, Boolean> visited = new HashMap<ComputerNode, Boolean>();

		DFSVisit(ca, cb, y, visited);
	}

	public void DFSVisit(ComputerNode ca, ComputerNode cb, int y, HashMap<ComputerNode, Boolean> visited) {
		if(visited.containsKey(cb)) {
			return;
		}
		if (!visited.containsKey(ca)) {
			visited.put(ca, true);
			transmissionSeq.add(ca);
		}
		Iterator<ComputerNode> i = ca.neighbors.listIterator();
		while (i.hasNext()) {
			ComputerNode n = i.next();
			if (n.id == cb.id && cb.timestamp <= y && !visited.containsKey(cb)) {
				transmissionSeq.add(cb);
				visited.put(cb, true);
				infected = true;
				return;
			} else if (!visited.containsKey(n)) {
				DFSVisit(n, cb, y, visited);
			}
		}
	}

	public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
		return computerMapping;
	}

	public List<ComputerNode> getComputerMapping(int c) {
		return computerMapping.get(c);
	}

	public boolean checkDuplicate(ComputerNode ci, ComputerNode cj) {
		for (int i = 0; i < computerMapping.get(ci.id).size(); i++) {
			if (ci.timestamp == computerMapping.get(ci.id).get(i).timestamp) {
				computerMapping.get(ci.id).get(i).neighbors.add(cj);
				return false;
			}
		}
		return true;
	}

	public void mergeSortTriples(Triple arr[], int l, int r) {
		if (l < r) {
			// Same as (l+r)/2, but avoids overflow for
			// large l and h
			int m = l + (r - l) / 2;

			// Sort first and second halves
			mergeSortTriples(arr, l, m);
			mergeSortTriples(arr, m + 1, r);

			merge(arr, l, m, r);
		}
	}

	public void merge(Triple arr[], int l, int m, int r) {
		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		/* Create temp arrays */
		Triple L[] = new Triple[n1];
		Triple R[] = new Triple[n2];

		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarry array
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i].timestamp <= R[j].timestamp) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		/* Copy remaining elements of R[] if any */
		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	public static void main(String[] args) {
		CommunicationsMonitor monitor = new CommunicationsMonitor();
		/*
		 * for(int i=0; i<10; i++) { for(int j=1; j<11; j++) { if(i<j) continue; for(int
		 * t=1; t<10; t++) { monitor.addCommunication(i, j, t); } } }
		 */

		monitor.addCommunication(1, 4, 12);
		monitor.addCommunication(1, 2, 4);
		monitor.addCommunication(2, 4, 8);
		monitor.addCommunication(3, 4, 8);

		monitor.createGraph();
		monitor.queryInfection(1, 3, 4, 8);
		
		for (ComputerNode i: monitor.transmissionSeq) {
			System.out.println(i.id + " " + i.timestamp);
		}
		
		
		//System.out.println(visited.containsKey(a));

	}

}
