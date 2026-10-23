package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.AlgorithmResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {

    public static AlgorithmResult run(Graph graph) {
        AlgorithmResult res = new AlgorithmResult();
        List<Edge> edges = new ArrayList<>(graph.allEdges);

        Collections.sort(edges);
        if (edges.size() > 1) {
            res.comparisons += (long) (edges.size() * (Math.log(edges.size()) / Math.log(2)));
        }

        DSU dsu = new DSU(graph.n);
        long totalWeight = 0;

        for (Edge edge : edges) {
            res.edgeScans++;
            if (dsu.find(edge.u) != dsu.find(edge.v)) {
                dsu.union(edge.u, edge.v);
                totalWeight += edge.weight;
                if (dsu.components() == 1) {
                    break;
                }
            }
        }

        res.dsuOps = dsu.getDsuOps();
        res.result = totalWeight;
        return res;
    }
}