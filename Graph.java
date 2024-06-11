import java.util.*;

public class Graph implements GraphADT {
	private int numNodes; // Total number of nodes in the graph.
	private Map<GraphNode, List<GraphEdge>> adjacencyList; // Adjacency list to store graph connections.

	public Graph(int n) {
		numNodes = n;
		adjacencyList = new HashMap<>();
		// Initialize the adjacency list with empty lists for each node.
		for (int i = 0; i < n; i++) {
			adjacencyList.put(new GraphNode(i), new ArrayList<>());
		}
	}

	public void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException {
		// Check if nodes exist in the graph.
		if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)) {
			throw new GraphException("Node not found");
		}

		// Add the new edge to the adjacency lists of both nodes.
		GraphEdge edge = new GraphEdge(u, v, edgeType, label);
		adjacencyList.get(u).add(edge);
		adjacencyList.get(v).add(edge);
	}

	public GraphNode getNode(int name) throws GraphException {
		// Search for the node with the given name.
		for (GraphNode node : adjacencyList.keySet()) {
			if (node.getName() == name) {
				return node;
			}
		}
		throw new GraphException("Node not found");
	}

	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		// Check if the node exists.
		if (!adjacencyList.containsKey(u)) {
			throw new GraphException("Node not found");
		}
		return adjacencyList.get(u).iterator();
	}

	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		// Search for the edge in the adjacency list of one of the nodes.
		for (GraphEdge edge : adjacencyList.get(u)) {
			if (edge.firstEndpoint() == v || edge.secondEndpoint() == v) {
				return edge;
			}
		}
		throw new GraphException("Edge not found");
	}

	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		// Check for an edge between u and v.
		for (GraphEdge edge : adjacencyList.get(u)) {
			if (edge.firstEndpoint() == v || edge.secondEndpoint() == v) {
				return true;
			}
		}
		return false;
	}
}