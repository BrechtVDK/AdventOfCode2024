package Day18;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class Dag18 {
	public static void main(String[] args) throws Exception {
		Dag18 d = new Dag18();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private List<Node> corruptedNodes = new ArrayList<>();
	private Set<Node> fallen = new HashSet<>();
	private Map<Node, List<Node>> graph = new HashMap<>();
	private final int RANGE = 70;
	private final int FIRST_X_BYTES = 1024;
	
	//KORTSTE PAD ONGEWOGEN GRAAF
	public Dag18() {
		readFile();
		for (int i = 0; i < FIRST_X_BYTES; i++) {
			fallen.add(corruptedNodes.get(i));
		}
		buildGraph();
		Map<Node, Integer> distMap = shortestPath();
		System.out.printf("Shortest path to end part 1: %d%n", distMap.get(new Node(RANGE, RANGE)));
		part2();
	}

	record Node(int col, int row) {
	}

	private void part2() {
		for (int i = FIRST_X_BYTES; i < corruptedNodes.size(); i++) {
			graph.clear();
			fallen.add(corruptedNodes.get(i));
			buildGraph();
			Map<Node, Integer> distMap = shortestPath();
			if (distMap.get(new Node(RANGE, RANGE)) == Integer.MAX_VALUE) {
				System.out.printf("No exit possible after %d bytes, this is byte %d,%d.", i,
						corruptedNodes.get(i).col, corruptedNodes.get(i).row);
				break;
			}

		}

	}

	private Map<Node, Integer> shortestPath() {
		Node start = new Node(0, 0);
		// dist map stores the distance of nodes from start
		Map<Node, Integer> distMap = new HashMap<>();
		graph.keySet().forEach(node -> distMap.put(node, Integer.MAX_VALUE));

		// Queue to store the nodes in the order they are
		// visited
		Queue<Node> q = new LinkedList<>();
		// Mark the distance of the source node as 0
		distMap.replace(start, 0);
		// Push the source node to the queue
		q.add(start);
		// Iterate until the queue is empty
		while (!q.isEmpty()) {
			// Pop the node at the front of the queue
			Node node = q.poll();
			// Explore all the neighbors of the current node
			for (Node neighbor : graph.get(node)) {
				// Check if the neighboring node is not
				// visited
				if (distMap.get(neighbor) == Integer.MAX_VALUE) {
					// Mark the distance of the neighboring
					// node as the distance of the current
					// node + 1
					distMap.replace(neighbor, distMap.get(node) + 1);
					// Insert the neighboring node to the
					// queue
					q.add(neighbor);
				}
			}
		}
		return distMap;

	}

	private void buildGraph() {
		// Bepaal alle knopen en verbindingen
		for (int row = 0; row <= RANGE; row++) {
			for (int col = 0; col <= RANGE; col++) {
				Node node = new Node(col, row);
				if (!fallen.contains(node)) {
					graph.putIfAbsent(node, new ArrayList<>());
					List<Node> neighbors = new ArrayList<>();
					// neighbors;
					if (row > 0) {
						neighbors.add(new Node(col, row - 1));
					}
					if (row < RANGE) {
						neighbors.add(new Node(col, row + 1));
					}
					if (col > 0) {
						neighbors.add(new Node(col - 1, row));
					}
					if (col < RANGE) {
						neighbors.add(new Node(col + 1, row));
					}
					for (Node neighbor : neighbors) {
						if (!fallen.contains(neighbor)) {
							graph.get(node).add(neighbor);
						}
					}
				}

			}
		}

	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				String[] split = line.split(",");
				corruptedNodes.add(new Node(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
