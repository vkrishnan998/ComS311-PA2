import java.util.List;

public class ComputerNode {
	
	int id;
	int timestamp;
	List<ComputerNode> neighbors;
	
	public ComputerNode(int id, int timestamp) {
		this.id = id;
		this.timestamp = timestamp;
	}
	
	public int getID() {
		return id;
	}
	
	public int getTimestamp() {
		return timestamp;
	}
	
	public List<ComputerNode> getOurNeighbors() {
		return neighbors;
	}
	
	
	
}




