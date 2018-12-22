package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * https://adventofcode.com/2018/day/22
 */
public class Day22 {
    private static final String INPUT = "src/main/resources/aoc2018/day22.txt";

    private static int part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        int depth = Integer.parseInt(lines.get(0).split(" ")[1].trim());
        String[] split = lines.get(1).split(" ")[1].trim().split(",");
        int[] target = new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
        long[][] grid = new long[target[1] + 1][target[0] + 1];
        int answer = 0;
        for (int y = 0; y <= target[1]; y++) {
            for (int x = 0; x <= target[0]; x++) {
                if ((y == 0 && x == 0) ||
                    (y == target[0] && x == target[1])) {
                    long geoIndex = 0;
                    grid[y][x] = erosionLevel(geoIndex, depth);
                } else if (y == 0) {
                    long geoIndex = x * 16807;
                    grid[y][x] = erosionLevel(geoIndex, depth);
                } else if (x == 0) {
                    long geoIndex = y * 48271;
                    grid[y][x] = erosionLevel(geoIndex, depth);
                } else {
                    long geoIndex = grid[y][x - 1] * grid[y - 1][x];
                    grid[y][x] = erosionLevel(geoIndex, depth);
                }
                answer += grid[y][x] % 3;
            }
        }
        return answer;
    }

    private static long erosionLevel(long geoIndex, int depth) {
        return (geoIndex + depth) % 20183;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
    }
}
