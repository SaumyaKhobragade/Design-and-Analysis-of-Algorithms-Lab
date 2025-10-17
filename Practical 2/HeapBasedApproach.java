import java.util.PriorityQueue;

public class HeapBasedApproach {
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

    public static void primsAlgorithm(int[][] graph) {
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

        printMST(parent, graph);
    }

    public static void primsAlgorithmArray(int[][] graph) {
        int V = graph.length;
        int[] key = new int[V];
        int[] parent = new int[V];
        boolean[] mstSet = new boolean[V];

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
            parent[i] = -1;
        }
        key[0] = 0;

        for (int count = 0; count < V - 1; count++) {
            int min = Integer.MAX_VALUE;
            int u = -1;
            for (int v = 0; v < V; v++) {
                if (!mstSet[v] && key[v] < min) {
                    min = key[v];
                    u = v;
                }
            }
            mstSet[u] = true;
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    key[v] = graph[u][v];
                    parent[v] = u;
                }
            }
        }
        printMST(parent, graph);
    }

    public static void printMST(int[] parent, int[][] graph) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < parent.length; i++) {
            System.out.println((char)(parent[i] + 'a') + " - " + (char)(i + 'a') + "\t" + graph[i][parent[i]]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 2, 0, 6, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 3, 8, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 7, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {6, 8, 0, 0, 9, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 5, 7, 9, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 8, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 8, 0, 2, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0},
                {0, 0, 4, 0, 0, 0, 2, 0, 5, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0},
                {7, 0, 0, 0, 0, 0, 0, 5, 0, 3, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 10, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 7, 0, 11, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 11, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 5, 0, 12, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 6, 0, 0, 13},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 7, 14, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 8, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 14, 8, 0, 4},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 4, 0}
        };

        System.out.println("Heap-based Prim's Algorithm:");
        long heapStart = System.nanoTime();
        primsAlgorithm(graph);
        long heapEnd = System.nanoTime();
        System.out.println("Execution time (Heap-based): " + (heapEnd - heapStart) + " ns\n");

        System.out.println("Array-based Prim's Algorithm:");
        long arrayStart = System.nanoTime();
        primsAlgorithmArray(graph);
        long arrayEnd = System.nanoTime();
        System.out.println("Execution time (Array-based): " + (arrayEnd - arrayStart) + " ns");
    }
}
