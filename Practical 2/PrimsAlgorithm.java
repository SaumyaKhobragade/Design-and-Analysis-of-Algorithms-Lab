import java.util.PriorityQueue;

public class PrimsAlgorithm {
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

    public static void printMST(int[] parent, int[][] graph) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < parent.length; i++) {
            System.out.println((char)(parent[i] + 'a') + " - " + (char)(i + 'a') + "\t" + graph[i][parent[i]]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 6, 5, 0, 0, 0, 0, 0},
                {1, 0, 6, 0, 0, 0, 0, 0, 0},
                {6, 6, 0, 0, 7, 3, 0, 0, 0},
                {5, 0, 0, 0, 0, 2, 10, 0, 0},
                {0, 0, 7, 0, 0, 0, 0, 12, 0},
                {0, 0, 3, 2, 0, 0, 0, 8, 0},
                {0, 0, 0, 10, 0, 0, 0, 11, 3},
                {0, 0, 0, 0, 12, 8, 0, 0, 8},
                {0, 0, 0, 0, 0, 0, 3, 8, 0}
        };

        primsAlgorithm(graph);
    }
}

class Node implements Comparable<Node> {
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
