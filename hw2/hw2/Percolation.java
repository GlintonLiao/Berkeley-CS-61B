package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int[][] grid;
    WeightedQuickUnionUF UnionFind;
    int openSize = 0;


    //Create N-by-N grid with all sites initially blocked
    public Percolation(int N) {
        grid = new int[N][N];
        UnionFind = new WeightedQuickUnionUF(N * N - 1);

        // Virtual head node
        for (int i = 0; i < N; i += 1) {
            UnionFind.union(-1, i);
        }

        // Virtual tail node
        for (int j = N * N - N; j < N * N; j += 1) {
            UnionFind.union(j, N * N + 1);
        }
    }

    // 1 -> Open; 0 -> Block
    public void open(int row, int col) {
        grid[row][col] = 1;
        openSize += 1;
    }

    public boolean isOpen(int row, int col) {
        return grid[row][col] == 1;
    }

    // Connected to the virtual head node -> full
    public boolean isFull(int row, int col) {
        int boxIndex = xyTo1D(row, col);
        return UnionFind.connected(-1, boxIndex);
    }
    
    public int numberOfOpenSites() {
        return openSize;
    }

    public void connectTop(int row, int col) {
        if (row != 0) {
            if (isOpen(row - 1, col)) {
                UnionFind.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
        }
    }

    public void connectLeft(int row, int col) {
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                UnionFind.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }
    }

    public void connectRight(int row, int col) {
        if (col != grid.length - 1) {
            if (isOpen(row, col + 1)) {
                UnionFind.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    // if the virtual head node and the virtual tail node is connected, return true;
    public boolean percolates() {
        return UnionFind.connected(-1, grid.length * grid.length - 1);
    }

    // helper method to get the index of the box
    private int xyTo1D(int row, int col) {
        return col * row - 1;
    }
}
