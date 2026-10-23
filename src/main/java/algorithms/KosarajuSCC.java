package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.AlgorithmResult;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class KosarajuSCC {

    public static AlgorithmResult run(Graph graph) {
        AlgorithmResult res = new AlgorithmResult();
        int n = graph.n;

        boolean[] visited = new boolean[n];
        int[] order = new int[n];
        int orderIdx = 0;

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int[] edgeIdx = new int[n];

        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }

            stack.push(i);
            visited[i] = true;

            while (!stack.isEmpty()) {
                res.maxDepth = Math.max(res.maxDepth, stack.size());
                int u = stack.peek();
                List<Edge> edges = graph.adj.get(u);

                if (edgeIdx[u] < edges.size()) {
                    Edge e = edges.get(edgeIdx[u]++);
                    res.edgeScans++;
                    if (!visited[e.v]) {
                        visited[e.v] = true;
                        stack.push(e.v);
                    }
                } else {
                    stack.pop();
                    order[orderIdx++] = u;
                }
            }
        }

        List<List<Integer>> revAdj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            revAdj.add(new ArrayList<>());
        }
        for (Edge e : graph.allEdges) {
            revAdj.get(e.v).add(e.u);
        }

        boolean[] visitedRev = new boolean[n];
        int[] edgeIdxRev = new int[n];
        int sccCount = 0;

        for (int i = n - 1; i >= 0; i--) {
            int root = order[i];
            if (visitedRev[root]) {
                continue;
            }

            sccCount++;
            stack.push(root);
            visitedRev[root] = true;

            while (!stack.isEmpty()) {
                res.maxDepth = Math.max(res.maxDepth, stack.size());
                int u = stack.peek();
                List<Integer> edges = revAdj.get(u);

                if (edgeIdxRev[u] < edges.size()) {
                    int v = edges.get(edgeIdxRev[u]++);
                    res.edgeScans++;
                    if (!visitedRev[v]) {
                        visitedRev[v] = true;
                        stack.push(v);
                    }
                } else {
                    stack.pop();
                }
            }
        }

        res.result = sccCount;
        return res;
    }
}