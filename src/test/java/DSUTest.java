import algorithms.DSU;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DSUTest {

    @Test
    public void testAgainstNaive() {
        int n = 100;
        DSU dsu = new DSU(n);
        int[] naive = new int[n];
        for (int i = 0; i < n; i++) naive[i] = i;

        Random rnd = new Random(123);
        for (int i = 0; i < 500; i++) {
            int u = rnd.nextInt(n);
            int v = rnd.nextInt(n);
            dsu.union(u, v);

            int target = naive[u];
            int replace = naive[v];
            for (int j = 0; j < n; j++) {
                if (naive[j] == replace) {
                    naive[j] = target;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                assertEquals(naive[i] == naive[j], dsu.find(i) == dsu.find(j));
            }
        }
    }
}