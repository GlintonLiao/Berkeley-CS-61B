package hw4.puzzle;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;

public class Solver {
    private int moves;
    private Stack<WorldState> al;
    private HashMap<WorldState, Integer> mp = new HashMap<>();

    public class SearchNode implements Comparable<SearchNode> {
        private WorldState state;
        private int currentMoves;
        private SearchNode prev;
        private int estimatedDis;

        @Override
        public int compareTo(SearchNode o) {
            return (this.currentMoves - this.estimatedDis) - (o.currentMoves - o.estimatedDis);
        }

        public SearchNode(WorldState state, int sofar, SearchNode prev) {
            this.state = state;
            this.currentMoves = sofar;
            this.prev = prev;

            if (mp != null && mp.containsKey(state)) {
                this.estimatedDis = mp.get(state);
            } else {
                this.estimatedDis = state.estimatedDistanceToGoal();
                mp.put(state, estimatedDis);
            }
        }
    }

    public Solver(WorldState initialState) {
        SearchNode node = new SearchNode(initialState, 0, null);
        MinPQ<SearchNode> queue = new MinPQ<>();
        while (!node.state.isGoal()) {
            for (WorldState neighbor : node.state.neighbors()) {
                if (node.prev == null || !neighbor.equals(node.prev.state)) {
                    queue.insert(new SearchNode(neighbor, node.currentMoves + 1, node));
                }
            }
            node = queue.delMin();
        }

        moves = node.currentMoves;
        al = new Stack<>();
        while (node != null) {
            al.push(node.state);
            node = node.prev;
        }
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return al;
    }
}
