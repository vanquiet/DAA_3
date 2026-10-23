package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.AlgorithmResult;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class KahnTopologicalSort {

    public static class KahnOutput {
        public AlgorithmResult metrics;
        public List<Integer> order;
        public boolean hasCycle;

        public KahnOutput(AlgorithmResult metrics, List<Integer> order, boolean hasCycle) {
            this.metrics = metrics;
            this.order = order;
            this.hasCycle = hasCycle;
        }
    }

    public static KahnOutput run(Graph graph) {
        AlgorithmResult res = new AlgorithmResult();
        int n = graph.n;
        int[] inDegree = new int[n];

        for (Edge e : graph.allEdges) {
            inDegree[e.v]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);

            for (Edge e : graph.adj.get(u)) {
                res.edgeScans++;
                inDegree[e.v]--;
                if (inDegree[e.v] == 0) {
                    queue.add(e.v);
                }
            }
        }

        boolean hasCycle = order.size() < n;
        res.result = order.size();
        return new KahnOutput(res, order, hasCycle);
    }
}