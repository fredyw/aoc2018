package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * https://adventofcode.com/2018/day/5
 */
public class Day5 {
    private static final String INPUT = "src/main/resources/aoc2018/day5.txt";

    private static int part1() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line = reader.readLine();
            return react(new StringBuilder(line));
        }
    }

    private static int part2() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line = reader.readLine();
            int min = Integer.MAX_VALUE;
            for (char c = 'a'; c <= 'z'; c++) {
                String newLine = line;
                newLine = newLine.replaceAll("" + c, "");
                newLine = newLine.replaceAll(("" + c).toUpperCase(), "");
                min = Math.min(min, react(new StringBuilder(newLine)));
            }
            return min;
        }
    }

    private static int react(StringBuilder sb) {
        while (true) {
            boolean found = false;
            for (int i = 0, j = 1; j < sb.length(); i++, j++) {
                if (canReact(sb.charAt(i), sb.charAt(j))) {
                    sb.deleteCharAt(j--);
                    sb.deleteCharAt(i--);
                    found = true;
                }
            }
            if (!found) {
                break;
            }
        }
        return sb.length();
    }

    private static boolean isUpperCase(char c) {
        return 'A' <= c && c <= 'Z';
    }

    private static boolean isLowerCase(char c) {
        return 'a' <= c && c <= 'z';
    }

    private static boolean canReact(char a, char b) {
        if ((isUpperCase(a) && isLowerCase(b)) ||
            (isLowerCase(a) && isUpperCase(b))) {
            return ("" + a).toLowerCase().equalsIgnoreCase("" + b);
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
