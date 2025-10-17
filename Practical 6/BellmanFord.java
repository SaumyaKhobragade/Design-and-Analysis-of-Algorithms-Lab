public class BellmanFord {
    static final double R = 6371.0;

    public static void bellmanFord(double[][] graph, int vertices, int src) {
        double[] dist = new double[vertices];
        for (int i = 0; i < dist.length; i++) {
            if (i != src) {
                dist[i] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < vertices - 1; i++) {
            for (int j = 0; j < vertices; j++) {
                for (int k = 0; k < vertices; k++) {
                    if (graph[j][k] != 0 && graph[j][k] != Integer.MAX_VALUE && dist[j] != Integer.MAX_VALUE && dist[j] + graph[j][k] < dist[k]) {
                        dist[k] = dist[j] + graph[j][k];
                    }
                }
            }
        }

        for (int u = 0; u < vertices; u++) {
            for (int v = 0; v < vertices; v++) {
                if (graph[u][v] != 0 && graph[u][v] != Integer.MAX_VALUE && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    System.out.println("Graph contains a negative weight cycle!");
                    return;
                }
            }
        }

        for (int i = 0; i < dist.length; i++) {
            System.out.println("To location: " + i + ", Distance: " + dist[i]);
        }
        System.out.println();
    }

    public static float calculateDist(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (int) Math.round(R * c);
    }

    public static void main(String[] args) {
//        int[][] graph = {{0, 4, 6, 0, 0, 0, 0},
//                {4, 0, 5, 10, 0, 0, 0},
//                {6, 5, 0, 0, 4, 0, 0},
//                {0, 10, 0, 0, 3, 6, 0},
//                {0, 0, 4, 3, 0, 2, 8},
//                {0, 0, 0, 6, 2, 0, 5},
//                {0, 0, 0, 0, 8, 5, 0}};
//
//        int[][] graph2 = {{0, 6, 5, 0, 0, 0, 0},
//                {6, 0, 2, 7, 0, 0, 0},
//                {5, 2, 0, -2, 3, 0, 0},
//                {0, 7, -2, 0, 4, 3, 0},
//                {0, 0, 3, 4, 0, 2, 5},
//                {0, 0, 0, 3, 2, 0, 4},
//                {0, 0, 0, 0, 5, 4, 0}};
//
//        int src = 0;
//        bellmanFord(graph, graph.length, src);
//        bellmanFord(graph2, graph2.length, src);

        Location[] arr = new Location[8];
        arr[0] = new Location(21.1498, 79.0820);
        arr[1] = new Location(21.1450, 79.0882);
        arr[2] = new Location(21.1446, 79.0595);
        arr[3] = new Location(21.1621, 79.0808);
        arr[4] = new Location(21.1555, 79.0731);
        arr[5] = new Location(21.1366, 79.0619);
        arr[6] = new Location(21.1020, 79.0640);
        arr[7] = new Location(21.0915, 79.0690);

        double[][] graph = new double[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = calculateDist(arr[i].x, arr[i].y, arr[j].x, arr[j].y);
                }
            }
        }

        bellmanFord(graph, 8, 0);
    }
}

class Location {
    double x;
    double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
