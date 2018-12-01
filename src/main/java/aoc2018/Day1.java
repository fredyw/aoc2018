package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * https://adventofcode.com/2018/day/1
 */
public class Day1 {
    private static final String INPUT = "src/main/resources/aoc2018/day1.txt";

    private static int part1() throws IOException {
        return Files.readAllLines(Paths.get(INPUT))
            .stream()
            .mapToInt(Integer::parseInt)
            .sum();
    }

    private static int part2() throws IOException {
        Set<Integer> set = new HashSet<>();
        int sum = 0;
        outer:
        while (true) {
            for (String line : Files.readAllLines(Paths.get(INPUT))) {
                sum += Integer.parseInt(line);
                if (!set.add(sum)) {
                    break outer;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
