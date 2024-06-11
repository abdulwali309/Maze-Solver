import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Maze {
	private Graph graph; //graph of maze
	private GraphNode startNode; //starting node of maze
	private GraphNode endNode; //end node of maze
	private int numCoins; //number of coins
	private List<GraphNode> pathSoFar; //path traveled so far
	private int width, length; //dimensions of maze

	//read maze from input file and initialize it
	public Maze(String inputFile) throws MazeException {
		pathSoFar = new ArrayList<>();
		try {
			BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
			readInput(inputReader);
		} catch (IOException e) {
			throw new MazeException("Error reading input file: " + e.getMessage());
		}
	}

	//returns graph for maze
	public Graph getGraph() {
		return graph;
	}

	//solve maze using dfs
	public Iterator<GraphNode> solve() throws GraphException {
		return DFS(numCoins, startNode).iterator();
	}

	//private helper method for DFS algorithm
	private List<GraphNode> DFS(int remainingCoins, GraphNode node) throws GraphException {
		//base case: reached the end node
		if (node.equals(endNode)) {
			ArrayList<GraphNode> path = new ArrayList<>();
			path.add(node);
			return path;
		}

		node.mark(true); //mark current node as visited
		Iterator<GraphEdge> edges = graph.incidentEdges(node); //iterate through connected edges
		while (edges != null && edges.hasNext()) {
			GraphEdge edge = edges.next();
			//decide the next node to visit
			GraphNode next = edge.firstEndpoint().equals(node) ? edge.secondEndpoint() : edge.firstEndpoint();

			//recurse if next node is unvisited and has sufficient coins
			if (!next.isMarked() && remainingCoins >= edge.getType()) {
				List<GraphNode> path = DFS(remainingCoins - edge.getType(), next);
				if (path != null) {
					path.add(0, node);
					return path;
				}
			}
		}
		node.mark(false); //unmark node, backtrack in DFS
		return null; //no path found from this node
	}
	//read and process input to construct maze
	private void readInput(BufferedReader inputReader) throws IOException, MazeException {
		try {
			int S = Integer.parseInt(inputReader.readLine());
			width = Integer.parseInt(inputReader.readLine());
			length = Integer.parseInt(inputReader.readLine());
			numCoins = Integer.parseInt(inputReader.readLine());

			graph = new Graph(width * length);
			startNode = null;
			endNode = null;

			for (int i = 0; i < 2 * length - 1; i++) {
				String line = inputReader.readLine();
				for (int j = 0; j < 2 * width - 1; j++) {
					char ch = line.charAt(j);
					if (i % 2 == 0 && j % 2 == 0) {
						int nodeIndex = i / 2 * width + j / 2;
						GraphNode node = graph.getNode(nodeIndex);
						if (ch == 's') {
							startNode = node;
						} else if (ch == 'x') {
							endNode = node;
						}
					} else if (ch == 'c' || Character.isDigit(ch)) {
						int nodeIndex1 = i / 2 * width + j / 2;
						int nodeIndex2 = (i % 2 == 0) ? nodeIndex1 + 1 : nodeIndex1 + width;
						int linkType = Character.isDigit(ch) ? Character.getNumericValue(ch) : 0;
						String label = (ch == 'c') ? "corridor" : "door";
						insertEdge(nodeIndex1, nodeIndex2, linkType, label);
					}
				}
			}
		} catch (NumberFormatException | GraphException e) {
			throw new MazeException("Error processing input file: " + e.getMessage());
		}
	}

	//insert edge between two nodes
	private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException, MazeException {
		try {
			graph.insertEdge(graph.getNode(node1), graph.getNode(node2), linkType, label);
		} catch (GraphException e) {
			throw new MazeException("Error inserting edge: " + e.getMessage());
		}
	}
}