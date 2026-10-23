import metrics.BenchmarkRunner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Benchmark...");
        try {
            BenchmarkRunner.runAll();
            System.out.println("Finished. CSV saved to results/results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}