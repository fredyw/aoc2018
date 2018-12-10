package aoc2018;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2018/day/10
 */
public class Day10 {
    private static final String INPUT = "src/main/resources/aoc2018/day10.txt";
    private static final Pattern PATTERN = Pattern.compile(
        "position=<\\s*(-?\\d+),\\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>");

    private static class Point {
        private int x;
        private int y;
        private final int vx;
        private final int vy;

        public Point(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }
    }

    private static boolean draw(List<Point> points, int time) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Point point : points) {
            minX = Math.min(minX, point.x);
            minY = Math.min(minY, point.y);
            maxX = Math.max(maxX, point.x);
            maxY = Math.max(maxY, point.y);
            if (!map.containsKey(point.y)) {
                Set<Integer> set = new HashSet<>();
                set.add(point.x);
                map.put(point.y, set);
            } else {
                map.get(point.y).add(point.x);
            }
        }
        // Assume 100x100 boundary.
        if ((Math.abs(maxY) - Math.abs(minY) >= 100) && (Math.abs(maxX) - Math.abs(minX) >= 100)) {
            return false;
        }
        System.out.println("Time: " + time);
        for (int y = minY; y <= maxY; y++) {
            Set<Integer> rows = map.get(y);
            for (int x = minX; x <= maxX; x++) {
                if (rows != null && rows.contains(x)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
        return true;
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            List<Point> points = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.matches()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    int vx = Integer.parseInt(matcher.group(3));
                    int vy = Integer.parseInt(matcher.group(4));
                    points.add(new Point(x, y, vx, vy));
                }
            }
            // Hopefully 20K is big enough (this is trial and error).
            for (int i = 1; i <= 20000; i++) {
                for (Point point : points) {
                    point.y += point.vy;
                    point.x += point.vx;
                }
                draw(points, i);
            }
        }
    }
}
