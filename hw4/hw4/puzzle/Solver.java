package hw4.puzzle;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;

public class Solver {
    private int moves;
    private Stack<WorldState> traversedWords = new Stack<>();
    private HashMap<WorldState, Integer> wordsMap = new HashMap<>();

    public class SearchNode implements Comparable<SearchNode> {
        private WorldState state;
        private int currentMoves;
        private SearchNode prev;
        private int estimatedDis;

        @Override
        public int compareTo(SearchNode o) {
            return (this.currentMoves - this.estimatedDis) - (o.currentMoves - o.estimatedDis);
        }

        public SearchNode(WorldState state, int currentMoves, SearchNode prev) {
            this.state = state;
            this.currentMoves = currentMoves;
            this.prev = prev;

            // Determine the value of estimated distance
            if (wordsMap != null && wordsMap.containsKey(state)) {
                this.estimatedDis = wordsMap.get(state);
            } else {
                this.estimatedDis = state.estimatedDistanceToGoal();
                wordsMap.put(state, estimatedDis);
            }
        }
    }

    public Solver(WorldState initial) {
        SearchNode node = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> queue = new MinPQ<>();

        // Add the neighbors to the priority queue, and remove the node which has smallest estimatedDis
        while (!node.state.isGoal()) {
            for (WorldState neighbor : node.state.neighbors()) {
                if (node.prev == null || !neighbor.equals(node.prev.state)) {
                    queue.insert(new SearchNode(neighbor, node.currentMoves + 1, node));
                }
            }
            node = queue.delMin();
        }

        // Set the final moves number when reaches to the goal
        moves = node.currentMoves;

        // Iterate through all the words from back to front, add them to the stack
        while (node != null) {
            traversedWords.push(node.state);
            node = node.prev;
        }
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return traversedWords;
    }
}
