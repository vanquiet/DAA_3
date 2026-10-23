package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.AlgorithmResult;

import java.util.PriorityQueue;

public class PrimMST {

    public static AlgorithmResult run(Graph graph) {
        AlgorithmResult res = new AlgorithmResult();
        boolean[] visited = new boolean[graph.n];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        long totalWeight = 0;

        for (int start = 0; start < graph.n; start++) {
            if (visited[start]) {
                continue;
            }

            visited[start] = true;
            for (Edge edge : graph.adj.get(start)) {
                res.edgeScans++;
                pq.add(edge);
            }

            while (!pq.isEmpty()) {
                Edge minEdge = pq.poll();
                res.comparisons++;

                if (visited[minEdge.v]) {
                    continue;
                }

                visited[minEdge.v] = true;
                totalWeight += minEdge.weight;

                for (Edge edge : graph.adj.get(minEdge.v)) {
                    res.edgeScans++;
                    if (!visited[edge.v]) {
                        pq.add(edge);
                    }
                }
            }
        }

        res.result = totalWeight;
        return res;
    }
}