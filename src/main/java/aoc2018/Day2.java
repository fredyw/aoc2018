package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
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

    private static String part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        for (String line1 : lines) {
            for (String line2 : lines) {
                String diff = diff(line1, line2);
                if (diff != null) {
                    return diff;
                }
            }
        }
        return "";
    }

    private static String diff(String s1, String s2) {
        int count = 0;
        int index = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                count++;
                index = i;
            }
        }
        if (count != 1) {
            return null;
        }
        return s1.substring(0, index) + s1.substring(index + 1);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
