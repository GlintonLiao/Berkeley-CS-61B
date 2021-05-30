package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Guotong Liao
 */
public class MazeAStarPath extends MazeExplorer {
    private int source;
    private int target;
    private boolean targetFound = false;
    private Maze maze;
    MinPQ<Integer> queue;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
        queue = new MinPQ<>((o1, o2) -> {
            return Integer.compare(h(o1) + distTo[o1], h(o2) + distTo[o2]);
        });
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(source) - maze.toX(target)) + Math.abs(maze.toY(source) - maze.toY(target));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return queue.delMin();
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex source. */
    private void astar(int source) {
        queue.insert(source);
        marked[source] = true;
        announce();

        while (!queue.isEmpty()) {
            int v = findMinimumUnmarked();
            announce();

            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    queue.insert(w);
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;

                    if (w == target) {
                        targetFound = true;
                        announce();
                    }

                    if (targetFound) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(source);
    }

}

