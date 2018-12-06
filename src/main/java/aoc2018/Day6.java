package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://adventofcode.com/2018/day/6
 */
public class Day6 {
    private static final String INPUT = "src/main/resources/aoc2018/day6.txt";

    private static int part1() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            List<int[]> coordinates = new ArrayList<>();
            int maxX = 0;
            int maxY = 0;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0].trim());
                int y = Integer.parseInt(split[1].trim());
                coordinates.add(new int[]{x, y});
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
            }
            maxX++;
            maxY++;
            Map<String, Integer> map = new HashMap<>();
            Set<String> infinites = new HashSet<>();
            for (int x1 = -1; x1 < maxX + 1; x1++) {
                for (int y1 = -1; y1 < maxY + 1; y1++) {
                    int[] minCoord = null;
                    int minDistance = -1;
                    for (int[] coordinate : coordinates) {
                        int x2 = coordinate[0];
                        int y2 = coordinate[1];
                        int distance = distance(x1, y1, x2, y2);
                        if (minDistance == -1 || minDistance >= distance) {
                            minCoord = minDistance == distance ? null : coordinate;
                            minDistance = distance;
                        }
                    }
                    if (minCoord != null) {
                        String key = key(minCoord);
                        if (!map.containsKey(key)) {
                            map.put(key, 1);
                        } else {
                            map.put(key, map.get(key) + 1);
                        }
                        if (x1 < 0 || x1 >= maxX || y1 < 0 || y1 >= maxY) {
                            infinites.add(key);
                        }
                    }
                }
            }
            for (String infinite : infinites) {
                map.remove(infinite);
            }
            int max = 0;
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                max = Math.max(max, e.getValue());
            }
            return max;
        }
    }

    private static int part2() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            List<int[]> coordinates = new ArrayList<>();
            int maxX = 0;
            int maxY = 0;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0].trim());
                int y = Integer.parseInt(split[1].trim());
                coordinates.add(new int[]{x, y});
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
            }
            maxX++;
            maxY++;
            int count = 0;
            for (int x1 = 0; x1 < maxX; x1++) {
                for (int y1 = 0; y1 < maxY; y1++) {
                    int totalDistance = 0;
                    for (int[] coordinate : coordinates) {
                        int x2 = coordinate[0];
                        int y2 = coordinate[1];
                        int distance = distance(x1, y1, x2, y2);
                        totalDistance += distance;
                    }
                    if (totalDistance < 10000) {
                        count++;
                    }
                }
            }
            return count;
        }
    }

    private static String key(int[] coordinate) {
        return coordinate[0] + "," + coordinate[1];
    }

    private static int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
