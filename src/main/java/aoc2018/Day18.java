package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * https://adventofcode.com/2018/day/18
 */
public class Day18 {
    private static final String INPUT = "src/main/resources/aoc2018/day18.txt";

    private static int part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        int maxRow = lines.size();
        int maxCol = lines.get(0).length();
        char[][] grid = new char[maxRow][maxCol];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        for (int i = 0; i < 10; i++) {
            char[][] tmp = clone(grid);
            for (int row = 0; row < maxRow; row++) {
                for (int col = 0; col < maxCol; col++) {
                    if (tmp[row][col] == '.') {
                        if (toTree(tmp, maxRow, maxCol, row, col)) {
                            grid[row][col] = '|';
                        }
                    } else if (tmp[row][col] == '|') {
                        if (toLumberyard(tmp, maxRow, maxCol, row, col)) {
                            grid[row][col] = '#';
                        }
                    } else if (tmp[row][col] == '#') {
                        if (toOpen(tmp, maxRow, maxCol, row, col)) {
                            grid[row][col] = '.';
                        }
                    }
                }
            }
        }
        int nTrees = 0;
        int nLumberyards = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '#') {
                    nLumberyards++;
                } else if (grid[i][j] == '|') {
                    nTrees++;
                }
            }
        }
        return nLumberyards * nTrees;
    }

    private static char[][] clone(char[][] a) {
        char[][] clone = new char[a.length][a[0].length];
        for (int i = 0; i < clone.length; i++) {
            for (int j = 0; j < clone[i].length; j++) {
                clone[i][j] = a[i][j];
            }
        }
        return clone;
    }

    private static void draw(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean toOpen(char[][] grid, int maxRow, int maxCol, int row, int col) {
        int nLumberyards = 0;
        int nTrees = 0;
        // top
        if (valid(maxRow, maxCol, row - 1, col)) {
            if (grid[row - 1][col] == '#') {
                nLumberyards++;
            } else if (grid[row  - 1][col] == '|') {
                nTrees++;
            }
        }
        // top right
        if (valid(maxRow, maxCol, row - 1, col + 1)) {
            if (grid[row - 1][col + 1] == '#') {
                nLumberyards++;
            } else if (grid[row - 1][col + 1] == '|') {
                nTrees++;
            }
        }
        // right
        if (valid(maxRow, maxCol, row, col + 1)) {
            if (grid[row][col + 1] == '#') {
                nLumberyards++;
            } else if (grid[row][col + 1] == '|') {
                nTrees++;
            }
        }
        // bottom right
        if (valid(maxRow, maxCol, row + 1, col + 1)) {
            if (grid[row + 1][col + 1] == '#') {
                nLumberyards++;
            } else if (grid[row + 1][col + 1] == '|') {
                nTrees++;
            }
        }
        // bottom
        if (valid(maxRow, maxCol, row + 1, col)) {
            if (grid[row + 1][col] == '#') {
                nLumberyards++;
            } else if (grid[row + 1][col] == '|') {
                nTrees++;
            }
        }
        // bottom left
        if (valid(maxRow, maxCol, row + 1, col - 1)) {
            if (grid[row + 1][col - 1] == '#') {
                nLumberyards++;
            } else if (grid[row + 1][col - 1] == '|') {
                nTrees++;
            }
        }
        // left
        if (valid(maxRow, maxCol, row, col - 1)) {
            if (grid[row][col - 1] == '#') {
                nLumberyards++;
            } else if (grid[row][col - 1] == '|') {
                nTrees++;
            }
        }
        // top left
        if (valid(maxRow, maxCol, row - 1, col - 1)) {
            if (grid[row - 1][col - 1] == '#') {
                nLumberyards++;
            } else if (grid[row - 1][col - 1] == '|') {
                nTrees++;
            }
        }
        return !(nLumberyards >= 1 && nTrees >= 1);
    }

    private static boolean toLumberyard(char[][] grid, int maxRow, int maxCol, int row, int col) {
        int nLumberyards = 0;
        // top
        if (valid(maxRow, maxCol, row - 1, col)) {
            if (grid[row - 1][col] == '#') {
                nLumberyards++;
            }
        }
        // top right
        if (valid(maxRow, maxCol, row - 1, col + 1)) {
            if (grid[row - 1][col + 1] == '#') {
                nLumberyards++;
            }
        }
        // right
        if (valid(maxRow, maxCol, row, col + 1)) {
            if (grid[row][col + 1] == '#') {
                nLumberyards++;
            }
        }
        // bottom right
        if (valid(maxRow, maxCol, row + 1, col + 1)) {
            if (grid[row + 1][col + 1] == '#') {
                nLumberyards++;
            }
        }
        // bottom
        if (valid(maxRow, maxCol, row + 1, col)) {
            if (grid[row + 1][col] == '#') {
                nLumberyards++;
            }
        }
        // bottom left
        if (valid(maxRow, maxCol, row + 1, col - 1)) {
            if (grid[row + 1][col - 1] == '#') {
                nLumberyards++;
            }
        }
        // left
        if (valid(maxRow, maxCol, row, col - 1)) {
            if (grid[row][col - 1] == '#') {
                nLumberyards++;
            }
        }
        // top left
        if (valid(maxRow, maxCol, row - 1, col - 1)) {
            if (grid[row - 1][col - 1] == '#') {
                nLumberyards++;
            }
        }
        return nLumberyards >= 3;
    }

    private static boolean toTree(char[][] grid, int maxRow, int maxCol, int row, int col) {
        int nTrees = 0;
        // top
        if (valid(maxRow, maxCol, row - 1, col)) {
            if (grid[row - 1][col] == '|') {
                nTrees++;
            }
        }
        // top right
        if (valid(maxRow, maxCol, row - 1, col + 1)) {
            if (grid[row - 1][col + 1] == '|') {
                nTrees++;
            }
        }
        // right
        if (valid(maxRow, maxCol, row, col + 1)) {
            if (grid[row][col + 1] == '|') {
                nTrees++;
            }
        }
        // bottom right
        if (valid(maxRow, maxCol, row + 1, col + 1)) {
            if (grid[row + 1][col + 1] == '|') {
                nTrees++;
            }
        }
        // bottom
        if (valid(maxRow, maxCol, row + 1, col)) {
            if (grid[row + 1][col] == '|') {
                nTrees++;
            }
        }
        // bottom left
        if (valid(maxRow, maxCol, row + 1, col - 1)) {
            if (grid[row + 1][col - 1] == '|') {
                nTrees++;
            }
        }
        // left
        if (valid(maxRow, maxCol, row, col - 1)) {
            if (grid[row][col - 1] == '|') {
                nTrees++;
            }
        }
        // top left
        if (valid(maxRow, maxCol, row - 1, col - 1)) {
            if (grid[row - 1][col - 1] == '|') {
                nTrees++;
            }
        }
        return nTrees >= 3;
    }

    private static boolean valid(int maxRow, int maxCol, int row, int col) {
        return row >= 0 && row < maxRow && col >= 0 && col < maxCol;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
    }
}
