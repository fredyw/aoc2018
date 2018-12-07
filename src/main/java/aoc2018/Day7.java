package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
            Map<String, Integer> map = new HashMap<>();
            Set<String> set = new HashSet<>();
            int step = 60;
            while (!allNodes.isEmpty()) {
                Iterator<String> iterator = allNodes.iterator();
                Set<String> tmpSet = new HashSet<>();
                Set<String> tmpDeps = new HashSet<>();
                while (iterator.hasNext()) {
                    String node = iterator.next();
                    Set<String> nodes = deps.getOrDefault(node, new HashSet<>());
                    Set<String> clone = new HashSet<>(nodes);
                    nodes.removeAll(set);
                    if (nodes.isEmpty()) {
                        tmpSet.add(node);
                        String maxNode = !clone.isEmpty() ? Collections.max(clone) : "";
                        int val = step + node.charAt(0) - 'A' + 1 + map.getOrDefault(maxNode, 0);
                        tmpDeps.addAll(clone);
                        map.put(node, val);
                        iterator.remove();
                    }
                }
                int max = 0;
                for (String dep : tmpDeps) {
                    set.remove(dep);
                    max = Math.max(max, map.get(dep));
                }
                set.addAll(tmpSet);
            }
            int max = 0;
            for (String s : set) {
                max = Math.max(max, map.get(s));
            }
            return max;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
