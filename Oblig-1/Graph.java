import java.util.List;

import java.util.ArrayList;
import java.util.LinkedList;

class Node {
	private int label;
	private boolean visited = false;
	private List<Node> neighbors = new LinkedList<Node>();

	public Node(int label) {
		this.label = label;
	}

	public int getLabel() {
		return label;
	}

	public List<Node> getNeighbors() {
		return neighbors;
	}

	public boolean isVisited() {
		return visited;
	}

	public void visit() {
		visited = true;
	}

	public void addNeighbor(Node n) {
		// legger til en uretta kant fra this til n
		if (!neighbors.contains(n)) {
			neighbors.add(n);
			n.addNeighbor(this);
		}
	}

	public void addSuccessor(Node n) {
		// legger til en retta kant fra this til n
		if (!neighbors.contains(n)) {
			neighbors.add(n);
		}
	}

	public String toString() {
		return Integer.toString(label);
	}
}

class Graph {
	private Node[] nodes;

	public Graph(Node[] nodes) {
		this.nodes = nodes;
	}

	public void printNeighbors() {
		for (Node n1 : nodes) {
			String s = n1.toString() + ": ";
			for (Node n2 : n1.getNeighbors()) {
				s += n2.toString() + " ";
			}
			System.out.println(s.substring(0, s.length() - 1));
		}
	}

	private static Graph buildExampleGraph() {
		// ukeoppgave
		Node[] nodes = new Node[7];
		for (int i = 0; i < 7; i++) {
			nodes[i] = new Node(i);
		}
		nodes[0].addNeighbor(nodes[1]);
		nodes[0].addNeighbor(nodes[2]);
		nodes[1].addNeighbor(nodes[2]);
		nodes[2].addNeighbor(nodes[0]);
		nodes[2].addNeighbor(nodes[1]);
		nodes[3].addNeighbor(nodes[4]);
		nodes[4].addNeighbor(nodes[5]);
		nodes[5].addNeighbor(nodes[6]);
		return new Graph(nodes);
	}

	private static Graph buildRandomSparseGraph(int numberofV, long seed) {
		// seed brukes av java.util.Random for å generere samme sekvens for samme frø
		// (seed) og numberofV
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	private static Graph buildRandomDenseGraph(int numberofV, long seed) {
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < numberofV * numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	private static Graph buildRandomDirGraph(int numberofV, long seed) {
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < 2 * numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addSuccessor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	// --- 1A ---
	public void DFS(Node s) {
		s.visit(); 
		for (Node n : s.getNeighbors()) { 
			if (!n.isVisited()) { 
				this.DFS(n); 
			}
		}
	}

	public void DFSFull() {
		for (Node n : nodes) {
			if (!n.isVisited()) {
				this.DFS(n);
			}
		}
	}

	public int numberOfComponents() {
		int components = 0;
		for (Node n : this.nodes) {
			if (!n.isVisited()) {
				DFSFull();
				components++;
			}
		}
		return components;
	}
	// --- 1B ---
	public void DFSTransform(Node s) {
		s.visit();
		for (Node n : s.getNeighbors()) {
			n.addNeighbor(s);
			if (s.isVisited() == false) {
				this.DFSTransform(n);
			}
		}
	}
	public Graph transformDirToUndir() {
		Node list[] = nodes;
		for (Node n : nodes) {
			if (!n.isVisited()) {
				DFSTransform(n);
			}
		}
		return new Graph(list);
	}

	// --- 1C ---
	public boolean isConnected() {
		for (Node n : nodes) {
			if (!n.isVisited()) {
				DFSTransform(n);
			}
		}
		if (this.numberOfComponents() > 1) {
			return true;
		} else {
			return false;

		}
	}
	// --- 1D ---
	public void DFSBiggest(Node s , ArrayList<Node> list) {
		s.visit();
		list.add(s);
		for (Node n : s.getNeighbors()) {
			if (n.isVisited() == false) {			
				this.DFSBiggest(n, list);
			}
		}
	}

	public Graph biggestComponent() {		    
		ArrayList<ArrayList<Node>> components = new ArrayList<ArrayList<Node>>();

		for(Node n : nodes){
			if(!n.isVisited()){
				ArrayList<Node> noder = new ArrayList<Node>();
				DFSBiggest(n, noder);
				components.add(noder);
			}
		}
		int biggestcomponent = 0;
		for(int i = 0 ; i < components.size(); i++){
			if( components.get(i).size() >= components.get(biggestcomponent).size()){
				biggestcomponent = i;
			}	
		}
        
		return new Graph(components.get(biggestcomponent).toArray(new Node[0]));
	}

	// --- 1E ---

	public int[][] buildAdjacencyMatrix() {
		int[][] adjMatrix = new int[nodes.length][nodes.length];
		for(Node n : nodes){
			for(Node s : n.getNeighbors()){
				adjMatrix[n.getLabel()][s.getLabel()] = 1;
			}
		}

		System.out.println("Graph: Adjacency Matrix");
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length ; j++) {
                System.out.print(adjMatrix[i][j]+ " ");
            }
            System.out.println();
        }

		return adjMatrix; 
	}

	public static void main(String[] args) {
		// --- 1 A ---
		Graph graph = buildExampleGraph();
		Graph g1 = buildRandomSparseGraph(11, 201909202359L);
		// graph.printNeighbors();
		// System.out.println("Antall komponenter: " + graph.numberOfComponents());

		// --- 1B ---
		//Graph graph = buildRandomDirGraph(7, 201909202359L);
		//System.out.println("---- Retta ----");
		//graph.printNeighbors();
		//System.out.println("---- Uretta ----");
		//graph.transformDirToUndir().printNeighbors();
		System.out.println("");
		// --- 1C ---
		// System.out.println("Svak graf : " + graph.isConnected());
		// --- 1D ---
		// g1.printNeighbors();
		// System.out.println("Antall komponenter: " + g1.numberOfComponents());
		// System.out.println("Storrste komponeneten: " + g1.biggestComponent());

		// --- 1E ---
		// System.out.println("AdjMatrix : " + graph.buildAdjacencyMatrix());
		graph.biggestComponent().printNeighbors();
		graph.buildAdjacencyMatrix();
		g1.printNeighbors();
		System.out.println(g1.numberOfComponents());
	}
}