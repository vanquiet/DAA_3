package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.AlgorithmResult;

import java.util.Arrays;
import java.util.List;

public class DagShortestPaths {

    public static class PathsOutput {
        public AlgorithmResult metrics;
        public long[] dist;
        public int[] parent;

        public PathsOutput(AlgorithmResult metrics, long[] dist, int[] parent) {
            this.metrics = metrics;
            this.dist = dist;
            this.parent = parent;
        }
    }

    public static PathsOutput run(Graph graph) {
        KahnTopologicalSort.KahnOutput kahn = KahnTopologicalSort.run(graph);
        AlgorithmResult res = new AlgorithmResult();
        int n = graph.n;

        int[] inDegree = new int[n];
        for (Edge e : graph.allEdges) {
            inDegree[e.v]++;
        }

        long[] dist = new long[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        Arrays.fill(parent, -1);

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                dist[i] = 0;
            }
        }

        List<Integer> topoOrder = kahn.order;
        for (int u : topoOrder) {
            if (dist[u] == Long.MAX_VALUE) {
                continue;
            }
            for (Edge e : graph.adj.get(u)) {
                res.edgeScans++;
                res.comparisons++;
                if (dist[e.v] > dist[u] + e.weight) {
                    dist[e.v] = dist[u] + e.weight;
                    parent[e.v] = u;
                }
            }
        }

        long sumDist = 0;
        for (int i = 0; i < n; i++) {
            if (dist[i] != Long.MAX_VALUE) {
                sumDist += dist[i];
            }
        }

        res.result = sumDist;
        return new PathsOutput(res, dist, parent);
    }
}