import java.util.ArrayList;
import java.util.List;

public class ComputerNode {

	int id;
	int timestamp;
	List<ComputerNode> neighbors;

	public ComputerNode(int id, int timestamp) {
		this.id = id;
		this.timestamp = timestamp;
		neighbors = new ArrayList<ComputerNode>();
	}

	public int getID() {
		return id;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public List<ComputerNode> getOutNeighbors() {
		return neighbors;
	}

}
