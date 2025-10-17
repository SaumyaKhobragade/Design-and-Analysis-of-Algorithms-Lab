import java.util.*;
import java.util.concurrent.*;

public class MultiThreadedPrim {
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

    public static List<String> primsAlgorithm(int[][] graph, List<Integer> originalVertices) {
        List<String> mstEdges = new ArrayList<>();
        int vertices = graph.length;
        boolean[] visited = new boolean[vertices];
        int[] parent = new int[vertices];
        int[] key = new int[vertices];
        PriorityQueue<Node> queue = new PriorityQueue<>();

        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        key[0] = 0;
        queue.add(new Node(0, 0));

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

        for (int i = 1; i < vertices; i++) {
            int origU = originalVertices.get(parent[i]);
            int origV = originalVertices.get(i);
            mstEdges.add(origU + " - " + origV + " : " + graph[i][parent[i]]);
        }
        return mstEdges;
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

    public static int[][] generateRandomGraph(int vertices, double density, int maxWeight) {
        Random rand = new Random();
        int[][] graph = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (rand.nextDouble() < density) {
                    int weight = 1 + rand.nextInt(maxWeight);
                    graph[i][j] = weight;
                    graph[j][i] = weight;
                } else {
                    graph[i][j] = 0;
                    graph[j][i] = 0;
                }
            }
            graph[i][i] = 0;
        }
        return graph;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int vertices = 1000;
        double density = 0.01;
        int maxWeight = 1000;

        int[][] graph = generateRandomGraph(vertices, density, maxWeight);

        List<List<Integer>> components = findConnectedComponents(graph);
        System.out.println("Number of connected components: " + components.size());

        long seqStart = System.nanoTime();
        for (List<Integer> comp : components) {
            int[][] subgraph = extractSubgraph(graph, comp);
            List<String> mstEdges = primsAlgorithm(subgraph, comp);
        }
        long seqEnd = System.nanoTime();
        System.out.println("Sequential execution time: " + (seqEnd - seqStart) / 1e6 + " ms");

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Callable<List<String>>> tasks = new ArrayList<>();
        for (List<Integer> comp : components) {
            tasks.add(() -> {
                int[][] subgraph = extractSubgraph(graph, comp);
                return primsAlgorithm(subgraph, comp);
            });
        }

        long parStart = System.nanoTime();
        List<Future<List<String>>> futures = executor.invokeAll(tasks);
        for (Future<List<String>> future : futures) {
            List<String> mstEdges = future.get();
        }
        long parEnd = System.nanoTime();
        executor.shutdown();

        System.out.println("Parallel execution time with " + numThreads + " threads: " + (parEnd - parStart) / 1e6 + " ms");
    }
}
