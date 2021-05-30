package lab11.graphs;

import edu.princeton.cs.algs4.In;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int source;
    private int target;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX,targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> list = new LinkedList<>();

        if (source == target) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        list.offer(source);
        marked[source] = true;
        while (!list.isEmpty() && !targetFound) {
            int v = list.poll();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;

                    if (w == target) {
                        targetFound = true;
                    }

                    list.offer(w);
                    if (targetFound) {
                        break;
                    }
                }
            }
            announce();
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

