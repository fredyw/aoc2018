package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * https://adventofcode.com/2018/day/2
 */
public class Day2 {
    private static final String INPUT = "src/main/resources/aoc2018/day2.txt";

    private static int part1() throws IOException {
        int countTwo = 0;
        int countThree = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Map<Character, Integer> countMap = new HashMap<>();
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (!countMap.containsKey(c)) {
                        countMap.put(c, 1);
                    } else {
                        int newCount = countMap.get(c) + 1;
                        countMap.put(c, newCount);
                    }
                }
                boolean foundTwo = false;
                boolean foundThree = false;
                for (int value : countMap.values()) {
                    if (value == 2) {
                        foundTwo = true;
                    }
                    if (value == 3) {
                        foundThree = true;
                    }
                }
                if (foundTwo) {
                    countTwo++;
                }
                if (foundThree) {
                    countThree++;
                }
            }
        }
        return countTwo * countThree;
    }

    private static int part2() throws IOException {
        // TODO
        return 0;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
