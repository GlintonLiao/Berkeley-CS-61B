package hw4.puzzle;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;

public class Solver {
    private int move;
    private Stack<WorldState> al;
    private HashMap<WorldState, Integer> mp = new HashMap<>();

    public class SearchNode implements Comparable<SearchNode> {
        private WorldState ws;
        private int movessofar;
        private SearchNode prev;
        private int estimatedDis;

        @Override
        public int compareTo(SearchNode o) {
            return (this.movessofar - this.estimatedDis) - (o.movessofar - o.estimatedDis);
        }

        public SearchNode(WorldState ws, int sofar, SearchNode prev) {
            this.ws = ws;
            this.movessofar = sofar;
            this.prev = prev;

            if (mp != null && mp.containsKey(ws)) {
                this.estimatedDis = mp.get(ws);
            } else {
                this.estimatedDis = ws.estimatedDistanceToGoal();
                mp.put(ws, estimatedDis);
            }
        }
    }
}
