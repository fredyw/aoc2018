package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2018/day/12
 */
public class Day12 {
    private static final String INPUT = "src/main/resources/aoc2018/day12.txt";
    private static final Pattern PATTERN = Pattern.compile("initial state: (.*)");

    private static int part1() throws IOException {
        String initial = null;
        Set<String> plants = new HashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line = reader.readLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                initial = matcher.group(1);
            }
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" => ");
                if (split[1].equals("#")) {
                    plants.add(split[0]);
                }
            }
        }
        String gen = "...." + initial + "....";
        int index = gen.indexOf("#");
        int nGen = 20;
        for (int i = 1; i <= nGen; i++) {
            char[] tmp = new char[gen.length()];
            for (int x = 0; x < tmp.length; x++) {
                tmp[x] = '.';
            }
            for (int j = 0; j + 5 < gen.length(); j++) {
                String sub = gen.substring(j, j + 5);
                if (plants.contains(sub)) {
                    tmp[j + 2] = '#'; // put in the center
                }
            }
            gen = new String(tmp);
            int count = 0;
            // Pad the left with .
            for (int j = 0; j < index; j++) {
                if (gen.charAt(j) == '.') {
                    count++;
                }
            }
            for (int j = 0; j < 4 - count; j++) {
                gen = "." + gen;
                index++;
            }
            // Pad the right with .
            count = 0;
            for (int j = gen.length() - 4; j < gen.length(); j++) {
                if (gen.charAt(j) == '.') {
                    count++;
                }
            }
            for (int j = 0; j < 4 - count; j++) {
                gen += ".";
            }
        }
        int sum = 0;
        for (int i = 0; i < index; i++) {
            if (gen.charAt(i) == '#') {
                sum -= index - i;
            }
        }
        for (int i = index; i < gen.length(); i++) {
            if (gen.charAt(i) == '#') {
                sum += i - index;
            }
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
    }
}
