import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MinimumSpanningForest {
    static class Node implements Comparable<Node> {
        int vertex;
        int key;

        Node(int vertex, int key) {
            this.vertex = vertex;
            this.key = key;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.key, o.key);
        }
    }

    public static void primsAlgorithm(int[][] graph, List<Integer> originalVertices) {
        int vertices = graph.length;
        boolean[] visited = new boolean[vertices];
        int[] parent = new int[vertices];
        int[] key = new int[vertices];
        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (int i = 0; i < vertices; i++) {
            key[i] = Integer.MAX_VALUE;
            parent[i] = -1;
            visited[i] = false;
        }

        key[0] = 0;
        queue.add(new Node(0, key[0]));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int u = currentNode.vertex;

            if (!visited[u]) {
                visited[u] = true;

                for (int v = 0; v < vertices; v++) {
                    if (graph[u][v] != 0 && !visited[v] && graph[u][v] < key[v]) {
                        key[v] = graph[u][v];
                        parent[v] = u;
                        queue.add(new Node(v, key[v]));
                    }
                }
            }
        }

        System.out.println("Edge\tWeight");
        for (int i = 1; i < vertices; i++) {
            int origU = originalVertices.get(parent[i]);
            int origV = originalVertices.get(i);
            System.out.println(origU + " - " + origV + "\t" + graph[i][parent[i]]);
        }
    }

    public static int[][] readMatrixFromFile(String filename, int size) throws IOException {
        int[][] graph = new int[size][size];
        BufferedReader br = new BufferedReader(new FileReader(filename));
        for (int i = 0; i < size; i++) {
            String[] line = br.readLine().trim().split("\\s+");
            for (int j = 0; j < size; j++) {
                graph[i][j] = Integer.parseInt(line[j]);
            }
        }
        br.close();
        return graph;
    }

    public static List<List<Integer>> findConnectedComponents(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        List<List<Integer>> components = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> comp = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i);
                visited[i] = true;

                while (!queue.isEmpty()) {
                    int u = queue.poll();
                    comp.add(u);
                    for (int v = 0; v < n; v++) {
                        if (graph[u][v] != 0 && !visited[v]) {
                            visited[v] = true;
                            queue.add(v);
                        }
                    }
                }
                components.add(comp);
            }
        }
        return components;
    }

    public static int[][] extractSubgraph(int[][] graph, List<Integer> comp) {
        int size = comp.size();
        int[][] subgraph = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                subgraph[i][j] = graph[comp.get(i)][comp.get(j)];
            }
        }
        return subgraph;
    }

    public static void main(String[] args) throws IOException {
        int[][] graph = readMatrixFromFile("graph20.txt", 20);

        List<List<Integer>> components = findConnectedComponents(graph);
        System.out.println("Number of connected components: " + components.size());
        for (int i = 0; i < components.size(); i++) {
            System.out.println("Component " + (i + 1) + ": " + components.get(i));
        }

        for (int i = 0; i < components.size(); i++) {
            System.out.println("\nMST for Component " + (i + 1) + ":");
            int[][] subgraph = extractSubgraph(graph, components.get(i));
            primsAlgorithm(subgraph, components.get(i));
        }
    }
}
