package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * https://adventofcode.com/2018/day/20
 */
public class Day20 {
    private static final String INPUT = "src/main/resources/aoc2018/day20.txt";

    private static Map<String, Integer> getDistances() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        String s = lines.get(0).substring(1, lines.get(0).length() - 1);
        Stack<int[]> stack = new Stack<>();
        int x = 0;
        int y = 0;
        int prevX = 0;
        int prevY = 0;
        Map<String, Integer> dist = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.add(new int[]{x, y});
            } else if (s.charAt(i) == '|') {
                int[] p = stack.peek();
                x = p[0];
                y = p[1];
            } else if (s.charAt(i) == ')') {
                int[] p = stack.pop();
                x = p[0];
                y = p[1];
            } else {
                if (s.charAt(i) == 'N') {
                    y -= 1;
                } else if (s.charAt(i) == 'E') {
                    x += 1;
                } else if (s.charAt(i) == 'S') {
                    y += 1;
                } else if (s.charAt(i) == 'W') {
                    x -= 1;
                }
                if (!dist.containsKey(key(x, y))) {
                    int val = dist.getOrDefault(key(prevX, prevY), 0) + 1;
                    dist.put(key(x, y), val);
                } else {
                    int val = Math.min(
                        dist.getOrDefault(key(x, y), 1),
                        dist.getOrDefault(key(prevX, prevY), 0) + 1);
                    dist.put(key(x, y), val);
                }
            }
            prevX = x;
            prevY = y;
        }
        return dist;
    }

    private static int part1() throws IOException {
        Map<String, Integer> dist = getDistances();
        return dist.values().stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    private static int part2() throws IOException {
        Map<String, Integer> dist = getDistances();
        int answer = 0;
        for (int val : dist.values()) {
            if (val >= 1000) {
                answer++;
            }
        }
        return answer;
    }

    private static String key(int a, int b) {
        return a + "," + b;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
