import algorithms.KosarajuSCC;
import graph.Edge;
import graph.Graph;
import metrics.AlgorithmResult;
import metrics.GraphGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class SCCTest {

    @Test
    public void testKosarajuAgainstBruteForce() {
        int n = 30;
        Graph g = GraphGenerator.generateDirected(n, 60);

        boolean[][] reachable = new boolean[n][n];
        for (int start = 0; start < n; start++) {
            Queue<Integer> q = new ArrayDeque<>();
            q.add(start);
            reachable[start][start] = true;
            while (!q.isEmpty()) {
                int u = q.poll();
                for (Edge e : g.adj.get(u)) {
                    if (!reachable[start][e.v]) {
                        reachable[start][e.v] = true;
                        q.add(e.v);
                    }
                }
            }
        }

        boolean[] visited = new boolean[n];
        int bruteCount = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                bruteCount++;
                for (int j = 0; j < n; j++) {
                    if (reachable[i][j] && reachable[j][i]) {
                        visited[j] = true;
                    }
                }
            }
        }

        AlgorithmResult res = KosarajuSCC.run(g);
        assertEquals(bruteCount, res.result);
    }

    @Test
    public void testStackSafety100k() {
        int n = 100000;
        Graph line = new Graph(n, true);
        for (int i = 0; i < n - 1; i++) {
            line.addEdge(i, i + 1, 1);
        }
        assertDoesNotThrow(() -> KosarajuSCC.run(line));
    }
}