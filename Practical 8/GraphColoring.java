import java.util.*;

public class GraphColoring {
    private static int V;

    private static boolean isSafe(int v, int[][] graph, int[] color, int c) {
        for (int i = 0; i < V; i++) {
            if (graph[v][i] == 1 && c == color[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean graphColorUtil(int[][] graph, int m, int[] color, int v) {
        if (v == V) return true;

        for (int c = 1; c <= m; c++) {
            if (isSafe(v, graph, color, c)) {
                color[v] = c;

                if (graphColorUtil(graph, m, color, v + 1))
                    return true;

                color[v] = 0;
            }
        }
        return false;
    }

    public static void graphColoring(int[][] graph, int m) {
        V = graph.length;
        int[] color = new int[V];

        if (!graphColorUtil(graph, m, color, 0)) {
            System.out.println("Solution does not exist");
            return;
        }

        System.out.println("Assigned Frequency (Color) to each GSM Cell:");
        for (int i = 0; i < V; i++) {
            System.out.println("Cell " + i + " ---> Frequency " + color[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        V = sc.nextInt();

        int[][] graph = new int[V][V];

        System.out.println("Enter adjacency matrix:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = sc.nextInt();
            }
        }

        System.out.print("Enter number of available frequencies (colors): ");
        int m = sc.nextInt();

        graphColoring(graph, m);
        sc.close();
    }
}
