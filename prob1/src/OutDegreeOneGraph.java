import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class OutDegreeOneGraph {

	public static void main(String[] args) {
		
		// Set the size of the input or get from args
		int size = 10000000;
		
		// Create object of the class OutDegreeOneGraph
		OutDegreeOneGraph outDegree = new OutDegreeOneGraph();
		// Generate a random graph of size as the input
		// store it as vertex array
		Vertex[] graph = outDegree.generateRandomGraph(size);
		
		
		Algorithm algo = new Algorithm(graph);
		// Get the connected components separately
		HashMap<Integer, List<Vertex>> map = algo.getComponents();
		
		// Total number of Cyclces in the Graph
		System.out.println("Number of Cycles : " + map.keySet().size());
		System.out.println();
		
		// Loop through each cycle
		for(Entry<Integer, List<Vertex>> entry:map.entrySet()){
			// Get the list of vertices for a cycle
			List<Vertex> list = entry.getValue();
			// Get length of the cycle
			int length = algo.getLength(list.get(0).getId());

			System.out.println("Cycle : " + entry.getKey());
			System.out.println("Length of cycle : " + length);
			System.out.println("Weight of cycle : " + list.size());
			System.out.println();
		}
	}
	
	/**
	 * This method generates random graph
	 * @param size
	 * @return
	 */
	public Vertex[] generateRandomGraph(int size){
		// Create a vertex array to store the graph
		Vertex[] graph = new Vertex[size];

		for(int i = 0; i < size; i++){
			graph[i] = new Vertex();
			graph[i].setId(i);
			
			// For each vertex set a random outgoing edge
			Random rn = new Random();
			int outGoingEdge = rn.nextInt(size);

			graph[i].setOutGoingEdge(outGoingEdge);
		}

		return graph;
	}

}

class Algorithm{

	boolean[] discovered;
	Vertex[] graph;
	int[] components;
	int[] dfscounter;
	int component = 0;

	HashMap<Integer, List<Vertex>> map = new HashMap<>();

	public Algorithm(Vertex[] graph){
		this.graph = graph;
		dfscounter = new int[graph.length];
	}
	
	/**
	 * Get all the connected components of the graph
	 * @return
	 */
	public HashMap<Integer, List<Vertex>> getComponents(){
		// mark all vertices as undiscovered
		discovered = new boolean[graph.length];
		// component number for 
		components = new int[graph.length];

		for(int i = 0; i < graph.length; i++){
			dfsComponent(graph[i], i);
		}

		return map;
	}
	
	/**
	 * Run DFS from a node u with index i
	 * @param u
	 * @param i
	 */
	private void dfsComponent(Vertex u, int i){

		if(!discovered[i]){
			discovered[i] = true;

			int j = u.getOutGoingEdge();

			// If the outgoing node is not discovered, run DFS
			if(!discovered[j]){
				dfsComponent(graph[j], j);
			}
			
			int counter = components[j];
			if(counter == 0){
				counter = ++component;
			}

			components[i] = counter;
			List<Vertex> vertices = map.get(counter);

			if(vertices == null){
				vertices = new ArrayList<>();
			}

			vertices.add(graph[i]);
			map.put(counter, vertices);

		}
	}
	
	public int getLength(int v){
		int count = 1;
		
		while(dfscounter[v] == 0){
			dfscounter[v] = count++;
			int u = graph[v].getOutGoingEdge();

			if(dfscounter[u] != 0){
				return dfscounter[v] - dfscounter[u] + 1;
			}

			v = u;
		}

		return 0;
	}
}


class Vertex{
	private int id;
	private int outGoingEdge;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOutGoingEdge() {
		return outGoingEdge;
	}

	public void setOutGoingEdge(int outGoingEdge) {
		this.outGoingEdge = outGoingEdge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + "]";
	}
}