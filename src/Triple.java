
public class Triple {

	ComputerNode ci;
	ComputerNode cj;
	int timestamp;

	public Triple(ComputerNode ci, ComputerNode cj, int timestamp) {
		this.ci = ci;
		this.cj = cj;
		this.timestamp = timestamp;
	}

	public int getTimestamp() {
		return timestamp;
	}

}
