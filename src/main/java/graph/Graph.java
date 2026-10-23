package graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    public int n;
    public boolean directed;
    public List<List<Edge>> adj;
    public List<Edge> allEdges;

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        this.adj = new ArrayList<>(n);
        this.allEdges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            this.adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        Edge edge = new Edge(u, v, weight);
        adj.get(u).add(edge);
        allEdges.add(edge);
        if (!directed) {
            adj.get(v).add(new Edge(v, u, weight));
        }
    }

    public static Graph fromFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String firstLine = br.readLine();
            if (firstLine == null) {
                throw new IllegalArgumentException("File is empty");
            }
            String[] parts = firstLine.trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            boolean directed = parts[2].equalsIgnoreCase("directed");

            Graph graph = new Graph(n, directed);
            for (int i = 0; i < m; i++) {
                String line = br.readLine();
                if (line == null) break;
                String[] edgeData = line.trim().split("\\s+");
                int u = Integer.parseInt(edgeData[0]);
                int v = Integer.parseInt(edgeData[1]);
                int w = Integer.parseInt(edgeData[2]);
                graph.addEdge(u, v, w);
            }
            return graph;
        }
    }
}