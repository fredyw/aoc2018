package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://adventofcode.com/2018/day/15
 */
public class Day15 {
    private static final String INPUT = "src/main/resources/aoc2018/day15.txt";

    private static int part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        char[][] grid = new char[lines.size()][];
        int[][] hp = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
            hp[i] = new int[grid[i].length];
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'G' || grid[i][j] == 'E') {
                    hp[i][j] = 200;
                }
            }
        }
        int maxRow = grid.length;
        int maxCol = grid[0].length;
        int round = 1;
        while (true) {
            boolean fullRound = true;
            boolean[][] processed = new boolean[maxRow][maxCol];
            outer:
            for (int row = 0; row < maxRow; row++) {
                for (int col = 0; col < maxCol; col++) {
                    if (processed[row][col]) {
                        continue;
                    }
                    if (grid[row][col] == 'G') {
                        List<int[]> elves = new ArrayList<>();
                        for (int i = 0; i < maxRow; i++) {
                            for (int j = 0; j < maxCol; j++) {
                                if (grid[i][j] == 'E') {
                                    elves.add(new int[]{i, j});
                                }
                            }
                        }
                        fullRound = exec(grid, new int[]{row, col}, elves, processed, hp,
                            3, 3);
                    } else if (grid[row][col] == 'E') {
                        List<int[]> goblins = new ArrayList<>();
                        for (int i = 0; i < maxRow; i++) {
                            for (int j = 0; j < maxCol; j++) {
                                if (grid[i][j] == 'G') {
                                    goblins.add(new int[]{i, j});
                                }
                            }
                        }
                        fullRound = exec(grid, new int[]{row, col}, goblins, processed, hp,
                            3, 3);
                    }
                }
            }
            int eHealth = 0;
            int gHealth = 0;
            for (int i = 0; i < maxRow; i++) {
                for (int j = 0; j < maxCol; j++) {
                    if (grid[i][j] == 'G') {
                        gHealth += hp[i][j];
                    } else if (grid[i][j] == 'E') {
                        eHealth += hp[i][j];
                    }
                }
            }
            if (eHealth <= 0) {
                if (!fullRound) {
                    round--;
                }
                return round * gHealth;
            }
            if (gHealth <= 0) {
                if (!fullRound) {
                    round--;
                }
                return round * eHealth;
            }
            round++;
        }
    }

    private static int part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        for (int elfAttack = 4; elfAttack <= 200; elfAttack++) {
            char[][] grid = new char[lines.size()][];
            int[][] hp = new int[lines.size()][];
            int nElves = 0;
            for (int i = 0; i < lines.size(); i++) {
                grid[i] = lines.get(i).toCharArray();
                hp[i] = new int[grid[i].length];
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 'G') {
                        hp[i][j] = 200;
                    } else if (grid[i][j] == 'E') {
                        hp[i][j] = 200;
                        nElves++;
                    }
                }
            }
            int maxRow = grid.length;
            int maxCol = grid[0].length;
            int round = 1;
            while (true) {
                boolean fullRound = true;
                boolean[][] processed = new boolean[maxRow][maxCol];
                outer:
                for (int row = 0; row < maxRow; row++) {
                    for (int col = 0; col < maxCol; col++) {
                        if (processed[row][col]) {
                            continue;
                        }
                        if (grid[row][col] == 'G') {
                            List<int[]> elves = new ArrayList<>();
                            for (int i = 0; i < maxRow; i++) {
                                for (int j = 0; j < maxCol; j++) {
                                    if (grid[i][j] == 'E') {
                                        elves.add(new int[]{i, j});
                                    }
                                }
                            }
                            fullRound = exec(grid, new int[]{row, col}, elves, processed,
                                hp, 3, elfAttack);
                        } else if (grid[row][col] == 'E') {
                            List<int[]> goblins = new ArrayList<>();
                            for (int i = 0; i < maxRow; i++) {
                                for (int j = 0; j < maxCol; j++) {
                                    if (grid[i][j] == 'G') {
                                        goblins.add(new int[]{i, j});
                                    }
                                }
                            }
                            fullRound = exec(grid, new int[]{row, col}, goblins, processed,
                                hp, 3, elfAttack);
                        }
                    }
                }
                int elves = 0;
                int eHealth = 0;
                int gHealth = 0;
                for (int i = 0; i < maxRow; i++) {
                    for (int j = 0; j < maxCol; j++) {
                        if (grid[i][j] == 'G') {
                            gHealth += hp[i][j];
                        } else if (grid[i][j] == 'E') {
                            eHealth += hp[i][j];
                            elves++;
                        }
                    }
                }
                if (eHealth <= 0) {
                    if (!fullRound) {
                        round--;
                    }
                    break; // next attack power
                }
                if (gHealth <= 0) {
                    if (!fullRound) {
                        round--;
                    }
                    if (elves == nElves) {
                        return round * eHealth;
                    }
                    break; // next attack power
                }
                round++;
            }
        }
        return 0;
    }

    private static boolean exec(char[][] grid, int[] point, List<int[]> opposite,
                                boolean[][] processed, int[][] hp, int goblinAttack,
                                int elfAttack) {
        char type = grid[point[0]][point[1]];
        char oppositeType = type == 'G'? 'E' : 'G';
        int maxRow = grid.length;
        int maxCol = grid[0].length;
        int pRow = point[0];
        int pCol = point[1];
        int[] pUp = new int[]{pRow - 1, pCol};
        int[] pLeft = new int[]{pRow, pCol - 1};
        int[] pRight = new int[]{pRow, pCol + 1};
        int[] pDown = new int[]{pRow + 1, pCol};
        int minHp = Integer.MAX_VALUE;
        int[] adj = null;
        for (int[] i : new int[][]{pUp, pLeft, pRight, pDown}) {
            if (i[0] < 0 || i[0] >= maxRow || i[1] < 0 || i[1] >= maxCol) {
                continue;
            }
            if (grid[i[0]][i[1]] == oppositeType) {
                if (minHp > hp[i[0]][i[1]]) {
                    minHp = hp[i[0]][i[1]];
                    adj = i;
                }
            }
        }
        if (adj != null) {
            hp[adj[0]][adj[1]] -= type == 'G' ? goblinAttack : elfAttack;
            if (hp[adj[0]][adj[1]] <= 0) {
                grid[adj[0]][adj[1]] = '.'; // dead
            }
            return true;
        }
        int shortest = Integer.MAX_VALUE;
        int[] move = null;
        for (int[] pAdj : new int[][]{pUp, pLeft, pRight, pDown}) {
            for (int[] o : opposite) {
                int oRow = o[0];
                int oCol = o[1];
                int[] oUp = new int[]{oRow - 1, oCol};
                int[] oLeft = new int[]{oRow, oCol - 1};
                int[] oRight = new int[]{oRow, oCol + 1};
                int[] eDown = new int[]{oRow + 1, oCol};
                for (int[] oAdj : new int[][]{oUp, oLeft, oRight, eDown}) {
                    int path = shortestPath(grid, pAdj, oAdj);
                    if (shortest > path) {
                        shortest = path;
                        move = pAdj;
                    }
                }
            }
        }
        if (move == null) {
            return false;
        }
        hp[move[0]][move[1]] = hp[point[0]][point[1]];
        hp[point[0]][point[1]] = 0;

        grid[move[0]][move[1]] = grid[point[0]][point[1]];
        grid[point[0]][point[1]] = '.';
        processed[move[0]][move[1]] = true;

        int mRow = move[0];
        int mCol = move[1];
        int[] mUp = new int[]{mRow - 1, mCol};
        int[] mLeft = new int[]{mRow, mCol - 1};
        int[] mRight = new int[]{mRow, mCol + 1};
        int[] mDown = new int[]{mRow + 1, mCol};
        minHp = Integer.MAX_VALUE;
        adj = null;
        for (int[] i : new int[][]{mUp, mLeft, mRight, mDown}) {
            if (i[0] < 0 || i[0] >= maxRow || i[1] < 0 || i[1] >= maxCol) {
                continue;
            }
            if (grid[i[0]][i[1]] == oppositeType) {
                if (minHp > hp[i[0]][i[1]]) {
                    minHp = hp[i[0]][i[1]];
                    adj = i;
                }
            }
        }
        if (adj != null) {
            hp[adj[0]][adj[1]] -= type == 'G' ? goblinAttack : elfAttack;
            if (hp[adj[0]][adj[1]] <= 0) {
                grid[adj[0]][adj[1]] = '.'; // dead
            }
        }
        return true;
    }

    private static class Point {
        private final int row;
        private final int col;
        private int step;

        public Point(int row, int col, int step) {
            this.row = row;
            this.col = col;
            this.step = step;
        }
    }

    private static int shortestPath(char[][] grid, int[] point1, int[] point2) {
        int maxRow = grid.length;
        int maxCol = grid[0].length;
        if (point1[0] < 0 || point1[1] >= maxRow || point1[1] < 0 || point1[1] >= maxCol ||
            point2[0] < 0 || point2[1] >= maxRow || point2[1] < 0 || point2[1] >= maxCol) {
            return Integer.MAX_VALUE;
        }
        if (grid[point1[0]][point1[1]] != '.' || grid[point2[0]][point2[1]] != '.') {
            return Integer.MAX_VALUE;
        }
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[maxRow][maxCol];
        queue.add(new Point(point1[0], point1[1], 0));
        while (!queue.isEmpty()) {
            Point p = queue.remove();
            if (visited[p.row][p.col]) {
                continue;
            }
            if (p.row == point2[0] && p.col == point2[1]) {
                return p.step;
            }
            visited[p.row][p.col] = true;
            // up
            if (p.row - 1 >= 0) {
                if (grid[p.row - 1][p.col] == '.' && !visited[p.row - 1][p.col]) {
                    queue.add(new Point(p.row - 1, p.col, p.step + 1));
                }
            }
            // right
            if (p.col + 1 < maxRow) {
                if (grid[p.row][p.col + 1] == '.' && !visited[p.row][p.col + 1]) {
                    queue.add(new Point(p.row, p.col + 1, p.step + 1));
                }
            }
            // down
            if (p.row + 1 < maxRow) {
                if (grid[p.row + 1][p.col] == '.' && !visited[p.row + 1][p.col]) {
                    queue.add(new Point(p.row + 1, p.col, p.step + 1));
                }
            }
            // left
            if (p.col - 1 >= 0) {
                if (grid[p.row][p.col - 1] == '.' && !visited[p.row][p.col - 1]) {
                    queue.add(new Point(p.row, p.col - 1, p.step + 1));
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    private static void draw(char[][] grid, int[][] hp) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < hp.length; i++) {
            for (int j = 0; j < hp[i].length; j++) {
                if (grid[i][j] == 'G' || grid[i][j] == 'E') {
                    System.out.println(grid[i][j] + "(" + i + ", " + j + "): " + hp[i][j]);
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
