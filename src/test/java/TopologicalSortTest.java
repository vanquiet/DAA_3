import algorithms.KahnTopologicalSort;
import graph.Edge;
import graph.Graph;
import metrics.GraphGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSortTest {

    @Test
    public void testValidDAG() {
        Graph dag = GraphGenerator.generateDAG(100, 300);
        KahnTopologicalSort.KahnOutput out = KahnTopologicalSort.run(dag);

        assertFalse(out.hasCycle);
        int[] pos = new int[dag.n];
        for (int i = 0; i < out.order.size(); i++) {
            pos[out.order.get(i)] = i;
        }

        for (Edge e : dag.allEdges) {
            assertTrue(pos[e.u] < pos[e.v]);
        }
    }

    @Test
    public void testCycleDetection() {
        Graph g = new Graph(3, true);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);

        KahnTopologicalSort.KahnOutput out = KahnTopologicalSort.run(g);
        assertTrue(out.hasCycle);
    }
}