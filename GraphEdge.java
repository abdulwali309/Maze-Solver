public class GraphEdge {
	//initialize variables
	private GraphNode u;
	private GraphNode v;
	private int type;
	private String label;

	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.u = u; //first endpoint of edge
		this.v = v; //second endpoint of edge
		this.type = type; //sets type of edge
		this.label = label; //sets label of edge
	}

	public GraphNode firstEndpoint() {
		return u; //returns first endpoint
	}

	public GraphNode secondEndpoint() {
		return v; //returns second endpoint
	}

	public int getType() {
		return type; //gets type
	}

	public void setType(int newType) {
		type = newType; //sets type
	}

	public String getLabel() {
		return label; //returns label
	}

	public void setLabel(String newLabel) {
		label = newLabel; //sets label
	}
}