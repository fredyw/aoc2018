package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2018/day/7
 */
public class Day7 {
    private static final String INPUT = "src/main/resources/aoc2018/day7.txt";
    private static final Pattern PATTERN = Pattern.compile("" +
        "Step (.) must be finished before step (.) can begin\\.");

    private static String part1() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            Map<String, Set<String>> deps = new HashMap<>();
            Set<String> allNodes = new TreeSet<>();
            while ((line = reader.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.matches()) {
                    String from = matcher.group(1);
                    String to = matcher.group(2);
                    allNodes.add(from);
                    allNodes.add(to);
                    if (!deps.containsKey(to)) {
                        Set<String> set = new HashSet<>();
                        set.add(from);
                        deps.put(to, set);
                    } else {
                        deps.get(to).add(from);
                    }
                }
            }
            StringBuilder answer = new StringBuilder();
            Set<String> set = new HashSet<>();
            while (!allNodes.isEmpty()) {
                Iterator<String> iterator = allNodes.iterator();
                while (iterator.hasNext()) {
                    String node = iterator.next();
                    Set<String> nodes = deps.getOrDefault(node, new HashSet<>());
                    nodes.removeAll(set);
                    if (nodes.isEmpty()) {
                        answer.append(node);
                        set.add(node);
                        iterator.remove();
                        break;
                    }
                }
            }
            return answer.toString();
        }
    }

    private static int part2() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            Map<String, Set<String>> deps = new HashMap<>();
            Set<String> allNodes = new TreeSet<>();
            while ((line = reader.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.matches()) {
                    String from = matcher.group(1);
                    String to = matcher.group(2);
                    allNodes.add(from);
                    allNodes.add(to);
                    if (!deps.containsKey(to)) {
                        Set<String> set = new HashSet<>();
                        set.add(from);
                        deps.put(to, set);
                    } else {
                        deps.get(to).add(from);
                    }
                }
            }
            return part2(deps, allNodes, 5, 60);
        }
    }

    private static int part2(Map<String, Set<String>> deps, Set<String> allNodes, int workers, int step) {
        int time = 0;
        Map<String, Integer> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        while (!allNodes.isEmpty()) {
            Iterator<String> iterator = allNodes.iterator();
            Set<String> prevDeps = new HashSet<>(map.keySet());
            while (iterator.hasNext()) {
                String node = iterator.next();
                Set<String> nodes = deps.getOrDefault(node, new HashSet<>());
                Set<String> clone = new HashSet<>(nodes);
                clone.removeAll(prevDeps);
                if (clone.isEmpty()) {
                    int t = time;
                    for (String n : nodes) {
                        if (set.remove(n)) {
                            t = Math.max(t, map.get(n));
                        }
                    }
                    set.add(node);
                    int val = step + node.charAt(0) - 'A' + 1 + t;
                    map.put(node, val);
                    iterator.remove();
                    if (set.size() == workers) {
                        break;
                    }
                }
            }
            List<String> removed = new ArrayList<>();
            int min = Integer.MAX_VALUE;
            for (String n : set) {
                if (min > map.get(n)) {
                    removed.clear();
                    removed.add(n);
                    min = map.get(n);
                } else if (min == map.get(n)) {
                    removed.add(n);
                }
            }
            time = min;
            for (String n : removed) {
                set.remove(n);
            }
        }
        return time;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
