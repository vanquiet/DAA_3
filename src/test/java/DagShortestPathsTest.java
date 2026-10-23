import algorithms.DagShortestPaths;
import graph.Edge;
import graph.Graph;
import metrics.GraphGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DagShortestPathsTest {

    @Test
    public void testAgainstBellmanFord() {
        int n = 50;
        Graph dag = GraphGenerator.generateDAG(n, 150);

        DagShortestPaths.PathsOutput out = DagShortestPaths.run(dag);

        int[] inDegree = new int[n];
        for (Edge e : dag.allEdges) {
            inDegree[e.v]++;
        }

        long[] bf = new long[n];
        Arrays.fill(bf, Long.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                bf[i] = 0;
            }
        }

        for (int i = 0; i < n - 1; i++) {
            for (Edge e : dag.allEdges) {
                if (bf[e.u] != Long.MAX_VALUE && bf[e.v] > bf[e.u] + e.weight) {
                    bf[e.v] = bf[e.u] + e.weight;
                }
            }
        }

        assertArrayEquals(bf, out.dist);
    }
}