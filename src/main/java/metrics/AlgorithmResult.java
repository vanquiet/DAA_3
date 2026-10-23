package metrics;

public class AlgorithmResult {
    public long result;
    public long dsuOps;
    public long edgeScans;
    public long comparisons;
    public long maxDepth;
    public double timeMs;

    public AlgorithmResult() {
        this.result = 0;
        this.dsuOps = 0;
        this.edgeScans = 0;
        this.comparisons = 0;
        this.maxDepth = 0;
        this.timeMs = 0.0;
    }
}