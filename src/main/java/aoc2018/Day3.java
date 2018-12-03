package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://adventofcode.com/2018/day/3
 */
public class Day3 {
    private static final String INPUT = "src/main/resources/aoc2018/day3.txt";

    private static int part1() throws IOException {
        Map<String, Integer> map = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                String[] coord = split[2].substring(0, split[2].length() - 1).split(",");
                int x1 = Integer.parseInt(coord[0]);
                int y1 = Integer.parseInt(coord[1]);
                String[] dimension = split[3].split("x");
                for (int x = x1; x < x1 + Integer.parseInt(dimension[0]); x++) {
                    for (int y = y1; y < y1 + Integer.parseInt(dimension[1]); y++) {
                        String key = key(x, y);
                        if (!map.containsKey(key)) {
                            map.put(key, 1);
                        } else {
                            map.put(key, map.get(key) + 1);
                        }
                    }
                }
            }
        }
        int answer = 0;
        for (int count : map.values()) {
            if (count > 1) {
                answer++;
            }
        }
        return answer;
    }

    private static String key(int x, int y) {
        return x + "," + y;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
    }
}
