package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2018/day/16
 */
public class Day16 {
    private static final String INPUT = "src/main/resources/aoc2018/day16.txt";
    private static final Pattern PATTERN = Pattern.compile("(?:Before|After):\\s+\\[(.*)\\]");

    private static int part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        int blankCount = 0;
        List<Sample> samples = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isEmpty()) {
                blankCount++;
                if (blankCount == 2) {
                    break;
                }
            } else if (line.startsWith("Before:")) {
                Matcher matcher = PATTERN.matcher(line);
                int[] before = null;
                int[] instruction;
                int[] after = null;
                if (matcher.matches()) {
                    String[] split = matcher.group(1).split(",");
                    before = new int[split.length];
                    for (int j = 0; j < split.length; j++) {
                        before[j] = Integer.parseInt(split[j].trim());
                    }
                }
                String[] split = lines.get(++i).split(" ");
                instruction = new int[split.length];
                for (int j = 0; j < split.length; j++) {
                    instruction[j] = Integer.parseInt(split[j].trim());
                }
                matcher = PATTERN.matcher(lines.get(++i));
                if (matcher.matches()) {
                    split = matcher.group(1).split(",");
                    after = new int[split.length];
                    for (int j = 0; j < split.length; j++) {
                        after[j] = Integer.parseInt(split[j].trim());
                    }
                }
                samples.add(new Sample(before, after, instruction));
                blankCount = 0;
            }
        }
        int answer = 0;
        for (Sample sample : samples) {
            int count = 0;
            if (addr(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (addi(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (mulr(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (muli(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (banr(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (bani(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (borr(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (bori(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (setr(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (seti(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (gtir(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (gtri(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (gtrr(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (eqir(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (eqri(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (eqrr(sample.before, sample.after, sample.instruction)) {
                count++;
            }
            if (count >= 3) {
                answer++;
            }
        }
        return answer;
    }

    private static class Sample {
        private final int[] before;
        private final int[] after;
        private final int[] instruction;

        public Sample(int[] before, int[] after, int[] instruction) {
            this.before = before;
            this.after = after;
            this.instruction = instruction;
        }
    }

    private static boolean addr(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] + before[instruction[2]];
        return val == after[instruction[3]];
    }

    private static boolean addi(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] + instruction[2];
        return val == after[instruction[3]];
    }

    private static boolean mulr(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] * before[instruction[2]];
        return val == after[instruction[3]];
    }

    private static boolean muli(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] * instruction[2];
        return val == after[instruction[3]];
    }

    private static boolean banr(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] & before[instruction[2]];
        return val == after[instruction[3]];
    }

    private static boolean bani(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] & instruction[2];
        return val == after[instruction[3]];
    }

    private static boolean borr(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] | before[instruction[2]];
        return val == after[instruction[3]];
    }

    private static boolean bori(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] | instruction[2];
        return val == after[instruction[3]];
    }

    private static boolean setr(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]];
        return val == after[instruction[3]];
    }

    private static boolean seti(int[] before, int[] after, int[] instruction) {
        int val = instruction[1];
        return val == after[instruction[3]];
    }

    private static boolean gtir(int[] before, int[] after, int[] instruction) {
        int val = instruction[1] > before[instruction[2]] ? 1 : 0;
        return val == after[instruction[3]];
    }

    private static boolean gtri(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] > instruction[2] ? 1 : 0;
        return val == after[instruction[3]];
    }

    private static boolean gtrr(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] > before[instruction[2]] ? 1 : 0;
        return val == after[instruction[3]];
    }

    private static boolean eqir(int[] before, int[] after, int[] instruction) {
        int val = instruction[1] == before[instruction[2]] ? 1 : 0;
        return val == after[instruction[3]];
    }

    private static boolean eqri(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] == instruction[2] ? 1 : 0;
        return val == after[instruction[3]];
    }

    private static boolean eqrr(int[] before, int[] after, int[] instruction) {
        int val = before[instruction[1]] == before[instruction[2]] ? 1 : 0;
        return val == after[instruction[3]];
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
    }
}
