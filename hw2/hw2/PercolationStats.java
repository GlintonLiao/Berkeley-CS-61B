package hw2;

import java.util.Random;

public class PercolationStats {
    int[] threshold;
    Random random = new Random();
    int totalTime;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        int count = 0;
        int x = 0;
        int y = 0;
        threshold = new int[T];
        totalTime = T;

        while (count <= T) {
            Percolation test = pf.make(N);

            while (!test.percolates()) {
                x = random.nextInt(N);
                y = random.nextInt(N);

                if (!test.isOpen(x, y)) {
                    test.open(x, y);
                    test.connectLeft(x, y);
                    test.connectRight(x, y);
                    test.connectTop(x, y);
                }
            }

            threshold[count] = test.numberOfOpenSites();
            count += 1;
            clearGrid(test);
        }

        mean();
        stddev();
        confidenceLow();
        confidenceHigh();
    }

    // clear the grid, make it all blocked
    public void clearGrid(Percolation p) {
        for (int i = 0; i < p.grid.length; i += 1) {
            for (int j = 0; j < p.grid[0].length; j += 1) {
                p.grid[i][j] = 0;
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        int sum = 0;

        for (int item : threshold) {
            sum += item;
        }

        return  (double) sum / (double) threshold.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        int sumOfPower = 0;
        double mean = mean();

        for (int item : threshold) {
            sumOfPower += Math.pow((item - mean), 2);
        }

        return Math.sqrt((double) sumOfPower / (double) threshold.length - 1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double num = 1.96 * stddev() / Math.sqrt(totalTime);
        return mean() - num;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double num = 1.96 * stddev() / Math.sqrt(totalTime);
        return mean() + num;
    }
}
