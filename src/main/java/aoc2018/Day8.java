package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2018/day/8
 */
public class Day8 {
    private static final String INPUT = "src/main/resources/aoc2018/day8.txt";

    private static int part1() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line = reader.readLine();
            String[] array = line.split(" ");
            IntRef sum = new IntRef();
            part1(array, new IntRef(), sum);
            return sum.val;
        }
    }

    private static void part1(String[] array, IntRef index, IntRef sum) {
        if (index.val == array.length) {
            return;
        }
        int nChild = Integer.parseInt(array[index.val++]);
        int nMetadata = Integer.parseInt(array[index.val++]);
        for (int i = 0; i < nChild; i++) {
            part1(array, index, sum);
        }
        for (int i = 0; i < nMetadata; i++) {
            sum.val += Integer.parseInt(array[index.val++]);
        }
    }

    private static class IntRef {
        private int val;
    }

    private static class Node {
        private int val;
        private final List<Node> children = new ArrayList<>();
    }

    private static int part2() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line = reader.readLine();
            String[] array = line.split(" ");
            Node root = new Node();
            part2(root, array, new IntRef());
            return root.val;
        }
    }

    private static void part2(Node root, String[] array, IntRef index) {
        if (index.val == array.length) {
            return;
        }
        int nChild = Integer.parseInt(array[index.val++]);
        int nMetadata = Integer.parseInt(array[index.val++]);
        // no child
        if (nChild == 0) {
            int sum = 0;
            for (int i = 0; i < nMetadata; i++) {
                sum += Integer.parseInt(array[index.val++]);
            }
            root.val = sum;
        } else {
            for (int i = 0; i < nChild; i++) {
                Node child = new Node();
                root.children.add(child);
                part2(child, array, index);
            }
            int sum = 0;
            for (int i = 0; i < nMetadata; i++) {
                int idx = Integer.parseInt(array[index.val++]);
                if (idx == 0) {
                    continue;
                }
                if (idx - 1 < root.children.size()) {
                    sum += root.children.get(idx - 1).val;
                }
            }
            root.val = sum;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
