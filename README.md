# DAA Assignment 3

Student: Alizhan Alikhanov  
Group: SE-2523

## Project Structure
- `src/main/java/` — Graph core, DSU, algorithms, and benchmark runner
- `src/test/java/` — JUnit 5 tests (DSU, MST, SCC, TopoSort, DAG Shortest Paths)
- `data/` — Small test graphs (connected, disconnected, cyclic)
- `results/` — Output `results.csv` and generated charts in `plots/`
- `REPORT.md` — Complexity analysis, correctness arguments, and discussion

## How to Build and Run

### 1. Build project
```bash
mvn clean compile