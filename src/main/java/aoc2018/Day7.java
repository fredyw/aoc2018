package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
    }
}
