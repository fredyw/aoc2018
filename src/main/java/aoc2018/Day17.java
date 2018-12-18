package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2018/day/17
 */
public class Day17 {
    private static final String INPUT = "src/main/resources/aoc2018/day17.txt";

    private static class Simulation {
        private final char[][] grid;
        private final int minRow;
        private final int maxRow;
        private final int minCol;
        private final int maxCol;

        public Simulation(char[][] grid, int minRow, int maxRow, int minCol, int maxCol) {
            this.grid = grid;
            this.minRow = minRow;
            this.maxRow = maxRow;
            this.minCol = minCol;
            this.maxCol = maxCol;
        }
    }

    private static Simulation simulate() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        int minRow = Integer.MAX_VALUE;
        int minCol = Integer.MAX_VALUE;
        int maxRow = 0;
        int maxCol = 0;
        List<int[]> clays = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(",");
            String a = split[0].trim();
            String[] kv = a.split("=");
            int x = 0;
            int y = 0;
            if (kv[0].equals("x")) {
                x = Integer.parseInt(kv[1]);
                minCol = Math.min(minCol, x);
                maxCol = Math.max(maxCol, x);
            } else if (kv[0].equals("y")) {
                y = Integer.parseInt(kv[1]);
                minRow = Math.min(minRow, Integer.parseInt(kv[1]));
                maxRow = Math.max(maxRow, Integer.parseInt(kv[1]));
            }
            String b = split[1].trim();
            kv = b.split("=");
            String[] lr = kv[1].split("\\.\\.");
            int left = Integer.parseInt(lr[0]);
            int right = Integer.parseInt(lr[1]);
            if (kv[0].equals("x")) {
                minCol = Math.min(minCol, left);
                maxCol = Math.max(maxCol, right);
                for (int i = left; i <= right; i++) {
                    clays.add(new int[]{y, i});
                }
            } else if (kv[0].equals("y")) {
                minRow = Math.min(minRow, left);
                maxRow = Math.max(maxRow, right);
                for (int i = left; i <= right; i++) {
                    clays.add(new int[]{i, x});
                }
            }
        }
        char[][] grid = new char[2000][2000];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = '.';
            }
        }
        for (int[] point : clays) {
            grid[point[0]][point[1]] = '#';
        }
        fill(grid, maxRow, 0, 500);
        return new Simulation(grid, minRow, maxRow, minCol, maxCol);
    }

    private static int part1() throws IOException {
        Simulation simulation = simulate();
        int answer = 0;
        for (int i = simulation.minRow; i <= simulation.maxRow; i++) {
            for (int j = simulation.minCol - 1; j <= simulation.maxCol + 1; j++) {
                if (simulation.grid[i][j] == '~' || simulation.grid[i][j] == '|') {
                    answer++;
                }
            }
        }
        return answer;
    }

    private static int part2() throws IOException {
        Simulation simulation = simulate();
        int answer = 0;
        for (int i = simulation.minRow; i <= simulation.maxRow; i++) {
            for (int j = simulation.minCol; j <= simulation.maxCol; j++) {
                if (simulation.grid[i][j] == '~') {
                    answer++;
                }
            }
        }
        return answer;
    }

    private static boolean canFill(char[][] grid, int row, int col) {
        return grid[row][col] == '.' || grid[row][col] == '|';
    }

    private static void fill(char[][] grid, int maxRow, int row, int col) {
        if (row > maxRow || !canFill(grid, row, col)) {
            return;
        }
        if (!canFill(grid, row + 1, col)) {
            int left = col;
            while (canFill(grid, row, left) && !canFill(grid, row + 1, left)) {
                grid[row][left] = '|';
                left--;
            }
            int right = col + 1;
            while (canFill(grid, row, right) && !canFill(grid, row + 1, right)) {
                grid[row][right] = '|';
                right++;
            }
            if (canFill(grid, row + 1, left) || canFill(grid, row + 1, right)) {
                fill(grid, maxRow, row, left);
                fill(grid, maxRow, row, right);
            } else if (grid[row][left] == '#' && grid[row][right] == '#') {
                for (int i = left + 1; i < right; i++) {
                    grid[row][i] = '~';
                }
            }
        } else if (grid[row][col] == '.') {
            grid[row][col] = '|';
            fill(grid, maxRow, row + 1, col);
            if (grid[row + 1][col] == '~') {
                fill(grid, maxRow, row, col);
            }
        }
    }

    private static void draw(char[][] grid, int minRow, int maxRow, int minCol, int maxCol) {
        for (int row = minRow - 1; row <= maxRow; row++) {
            for (int col = minCol - 1; col <= maxCol + 1; col++) {
                if (row == 0 && col == 500) {
                    System.out.print("+");
                } else {
                    System.out.print(grid[row][col]);
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
