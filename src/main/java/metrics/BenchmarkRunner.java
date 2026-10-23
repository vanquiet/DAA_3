package metrics;

import algorithms.*;
import graph.Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;

public class BenchmarkRunner {

    private static final int[] N_SIZES = {1000, 3162, 10000, 31623, 100000};
    private static final int[] DENSITIES = {4, 20};

    public static void runAll() throws IOException {
        new File("results").mkdirs();
        try (PrintWriter writer = new PrintWriter(new FileWriter("results/results.csv"))) {
            writer.println("algorithm,graph,n,m,time_ms,dsu_ops,edge_scans,comparisons,max_depth,result");

            for (int n : N_SIZES) {
                for (int d : DENSITIES) {
                    int m = d * n;
                    String graphName = (d == 4) ? "sparse" : "dense";

                    Graph undirected = GraphGenerator.generateUndirected(n, m);
                    runAlgorithm("Kruskal", graphName, n, m, () -> KruskalMST.run(undirected), writer);
                    runAlgorithm("Prim", graphName, n, m, () -> PrimMST.run(undirected), writer);

                    Graph directed = GraphGenerator.generateDirected(n, m);
                    runAlgorithm("Kosaraju", graphName, n, m, () -> KosarajuSCC.run(directed), writer);

                    Graph dag = GraphGenerator.generateDAG(n, m);
                    runAlgorithm("Kahn", graphName, n, m, () -> KahnTopologicalSort.run(dag).metrics, writer);
                    runAlgorithm("DagShortestPath", graphName, n, m, () -> DagShortestPaths.run(dag).metrics, writer);
                }
            }
        }
    }

    private interface AlgorithmAction {
        AlgorithmResult execute();
    }

    private static void runAlgorithm(String name, String graphName, int n, int m,
                                     AlgorithmAction action, PrintWriter writer) {
        double[] times = new double[5];
        AlgorithmResult lastRes = null;

        for (int run = 0; run < 5; run++) {
            long start = System.nanoTime();
            lastRes = action.execute();
            long end = System.nanoTime();
            times[run] = (end - start) / 1e6;
        }

        Arrays.sort(times);
        double medianTime = times[2];

        writer.printf(Locale.US, "%s,%s,%d,%d,%.4f,%d,%d,%d,%d,%d\n",
                name, graphName, n, m, medianTime,
                lastRes.dsuOps, lastRes.edgeScans, lastRes.comparisons, lastRes.maxDepth, lastRes.result);
        System.out.printf(Locale.US, "%-16s | %-6s | n=%-6d m=%-7d | result=%-10d | time=%.2f ms\n",
                name, graphName, n, m, lastRes.result, medianTime);
    }
}