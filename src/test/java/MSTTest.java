import algorithms.KruskalMST;
import algorithms.PrimMST;
import graph.Graph;
import metrics.AlgorithmResult;
import metrics.GraphGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    @Test
    public void testKruskalAndPrimMatch() {
        for (int i = 0; i < 100; i++) {
            Graph g = GraphGenerator.generateUndirected(50, 200);
            AlgorithmResult kruskal = KruskalMST.run(g);
            AlgorithmResult prim = PrimMST.run(g);
            assertEquals(kruskal.result, prim.result);
        }
    }
}