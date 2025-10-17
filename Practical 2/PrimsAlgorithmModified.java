import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PrimsAlgorithmModified {
    public static class Node implements Comparable<Node> {
        int vertex;
        double key;

        Node(int vertex, double key) {
            this.vertex = vertex;
            this.key = key;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.key, o.key);
        }
    }

    public static class City {
        String name;
        double latitude;
        double longitude;

        public City(String name, double latitude, double longitude) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    public static double[] fetchLocation(String cityName, String apiKey) throws Exception {
        String urlString = "http://api.openweathermap.org/geo/1.0/direct?q="
                + URLEncoder.encode(cityName, "UTF-8")
                + "&limit=1&appid=" + apiKey;

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder json = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            json.append(inputLine);
        }
        in.close();

        int latIdx = json.indexOf("\"lat\":");
        int lonIdx = json.indexOf("\"lon\":");

        if (latIdx != -1 && lonIdx != -1) {
            String latStr = json.substring(latIdx + 6, json.indexOf(",", latIdx));
            String lonStr = json.substring(lonIdx + 6, json.indexOf(",", lonIdx));
            double lat = Double.parseDouble(latStr.trim());
            double lon = Double.parseDouble(lonStr.trim());
            return new double[]{lat, lon};
        } else {
            throw new Exception("Could not fetch lat/lon for " + cityName);
        }
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDist = Math.toRadians(lat2 - lat1);
        double lonDist = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDist / 2) * Math.sin(latDist / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDist / 2) * Math.sin(lonDist / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static void generateGraph(double[][] graph, String[] cityNames, String apiKey) throws Exception {
        int n = cityNames.length;
        List<City> cities = new ArrayList<>();

        for (String cityName : cityNames) {
            double[] latLon = fetchLocation(cityName, apiKey);
            cities.add(new City(cityName, latLon[0], latLon[1]));
        }

        for (int i = 0; i < n; i++) {
            City cityA = cities.get(i);
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    graph[i][j] = 0.0;
                } else {
                    City cityB = cities.get(j);
                    graph[i][j] = calculateDistance(
                            cityA.latitude, cityA.longitude,
                            cityB.latitude, cityB.longitude
                    );
                }
            }
        }

        System.out.println("Created Adjacency Matrix: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void generateMST(double[][] graph, String[] cities) {
        int vertices = graph.length;
        boolean[] visited = new boolean[vertices];
        int[] parent = new int[vertices];
        double[] key = new double[vertices];
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

        printMST(parent, graph, cities);
    }

    public static void printMST(int[] parent, double[][] graph, String[] cities) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < parent.length; i++) {
            System.out.println((cities[parent[i]]) + " - " + (cities[i]) + "\t" + graph[i][parent[i]]);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please input the names of the cities: ");
        String input = sc.nextLine();

        String apiKey = "c2e307e6b5551ceda8466e5191406406";

        String[] cities = input.split(" ");

        double[][] graph = new double[cities.length][cities.length];
        generateGraph(graph, cities, apiKey);

        generateMST(graph, cities);
    }
}
