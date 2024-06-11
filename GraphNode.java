public class GraphNode {
	private int name;
	private boolean marked;

	public GraphNode(int name) {
		this.name = name; //sets name of node
		this.marked = false; //initally node isnt marked
	}

	public void mark(boolean mark) {
		this.marked = mark; //updates marked status
	}

	public boolean isMarked() {
		return this.marked; //returns marked status
	}

	public int getName() {
		return this.name; //returns name of node
	}
}