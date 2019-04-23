import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommunicationsMonitor {

	HashMap<Integer, List<ComputerNode>> computerMapping;
	
	
	public CommunicationsMonitor() {
		computerMapping = new HashMap<Integer, List<ComputerNode>>();
	}
	
	public void addCommunication(int c1, int c2, int timestamp) {
		
		if (!computerMapping.containsKey(c1)) {
			ArrayList<ComputerNode> list = new ArrayList<ComputerNode>();
			list.add(new ComputerNode(c1, timestamp));
			computerMapping.put(c1, list);
		}
		else {
			computerMapping.get(c1).add(new ComputerNode(c1, timestamp));
		}
		
		if (!computerMapping.containsKey(c2)) {
			ArrayList<ComputerNode> list = new ArrayList<ComputerNode>();
			list.add(new ComputerNode(c1, timestamp));
			computerMapping.put(c2, list);
		}
		else {
			computerMapping.get(c2).add(new ComputerNode(c2, timestamp));
		}
		
	}	
	
	public void createGraph() {
		
	}
	
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y) {
		return null;
	}
	
	public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
		return computerMapping;
	}
	
	public List<ComputerNode> getComputerMapping(int c) {
		return computerMapping.get(c);
	}
	
	
	
}
