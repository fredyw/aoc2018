package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
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
            Predicate<Integer> match = (i) -> i.intValue() == sample.after[sample.instruction[3]];
            if (match.test(addr(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(addi(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(mulr(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(muli(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(banr(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(bani(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(borr(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(bori(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(setr(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(seti(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(gtir(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(gtri(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(gtrr(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(eqir(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(eqri(sample.before, sample.instruction))) {
                count++;
            }
            if (match.test(eqrr(sample.before, sample.instruction))) {
                count++;
            }
            if (count >= 3) {
                answer++;
            }
        }
        return answer;
    }

    private static int part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        int blankCount = 0;
        List<Sample> samples = new ArrayList<>();
        int idx = 0;
        for (; idx < lines.size(); idx++) {
            String line = lines.get(idx);
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
                String[] split = lines.get(++idx).split(" ");
                instruction = new int[split.length];
                for (int j = 0; j < split.length; j++) {
                    instruction[j] = Integer.parseInt(split[j].trim());
                }
                matcher = PATTERN.matcher(lines.get(++idx));
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
        Map<Integer, String> mapping = getMapping(samples);
        int[] register = new int[]{0, 0, 0, 0};
        for (int i = idx; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isEmpty()) {
                continue;
            }
            String[] split = line.split(" ");
            int[] instruction = new int[split.length];
            for (int j = 0; j < instruction.length; j++) {
                instruction[j] = Integer.parseInt(split[j].trim());
            }
            String instr = mapping.get(instruction[0]);
            if (instr.equals("addr")) {
                register[instruction[3]] = addr(register, instruction);
            } else if (instr.equals("addi")) {
                register[instruction[3]] = addi(register, instruction);
            } else if (instr.equals("mulr")) {
                register[instruction[3]] = mulr(register, instruction);
            } else if (instr.equals("muli")) {
                register[instruction[3]] = muli(register, instruction);
            } else if (instr.equals("banr")) {
                register[instruction[3]] = banr(register, instruction);
            } else if (instr.equals("bani")) {
                register[instruction[3]] = bani(register, instruction);
            } else if (instr.equals("borr")) {
                register[instruction[3]] = borr(register, instruction);
            } else if (instr.equals("bori")) {
                register[instruction[3]] = bori(register, instruction);
            } else if (instr.equals("setr")) {
                register[instruction[3]] = setr(register, instruction);
            } else if (instr.equals("seti")) {
                register[instruction[3]] = seti(register, instruction);
            } else if (instr.equals("gtir")) {
                register[instruction[3]] = gtir(register, instruction);
            } else if (instr.equals("gtri")) {
                register[instruction[3]] = gtri(register, instruction);
            } else if (instr.equals("gtrr")) {
                register[instruction[3]] = gtrr(register, instruction);
            } else if (instr.equals("eqir")) {
                register[instruction[3]] = eqir(register, instruction);
            } else if (instr.equals("eqri")) {
                register[instruction[3]] = eqri(register, instruction);
            } else if (instr.equals("eqrr")) {
                register[instruction[3]] = eqrr(register, instruction);
            } else {
                throw new RuntimeException("Boom!");
            }
        }
        return register[0];
    }

    private static Map<Integer, String> getMapping(List<Sample> samples) {
        Set<String> instructions = new HashSet<>();
        Map<Integer, String> mapping = new HashMap<>();
        while (instructions.size() != 16) {
            for (Sample sample : samples) {
                Set<String> set = new HashSet<>();
                Predicate<Integer> match = (i) -> i.intValue() == sample.after[sample.instruction[3]];
                if (match.test(addr(sample.before, sample.instruction))) {
                    set.add("addr");
                }
                if (match.test(addi(sample.before, sample.instruction))) {
                    set.add("addi");
                }
                if (match.test(mulr(sample.before, sample.instruction))) {
                    set.add("mulr");
                }
                if (match.test(muli(sample.before, sample.instruction))) {
                    set.add("muli");
                }
                if (match.test(banr(sample.before, sample.instruction))) {
                    set.add("banr");
                }
                if (match.test(bani(sample.before, sample.instruction))) {
                    set.add("bani");
                }
                if (match.test(borr(sample.before, sample.instruction))) {
                    set.add("borr");
                }
                if (match.test(bori(sample.before, sample.instruction))) {
                    set.add("bori");
                }
                if (match.test(setr(sample.before, sample.instruction))) {
                    set.add("setr");
                }
                if (match.test(seti(sample.before, sample.instruction))) {
                    set.add("seti");
                }
                if (match.test(gtir(sample.before, sample.instruction))) {
                    set.add("gtir");
                }
                if (match.test(gtri(sample.before, sample.instruction))) {
                    set.add("gtri");
                }
                if (match.test(gtrr(sample.before, sample.instruction))) {
                    set.add("gtrr");
                }
                if (match.test(eqir(sample.before, sample.instruction))) {
                    set.add("eqir");
                }
                if (match.test(eqri(sample.before, sample.instruction))) {
                    set.add("eqri");
                }
                if (match.test(eqrr(sample.before, sample.instruction))) {
                    set.add("eqrr");
                }
                for (String f : instructions) {
                    set.remove(f);
                }
                if (set.size() == 1) {
                    String i = set.iterator().next();
                    mapping.put(sample.instruction[0], i);
                    instructions.add(i);
                }
            }
        }
        return mapping;
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

    private static int addr(int[] register, int[] instruction) {
        return register[instruction[1]] + register[instruction[2]];
    }

    private static int addi(int[] register, int[] instruction) {
        return register[instruction[1]] + instruction[2];
    }

    private static int mulr(int[] register, int[] instruction) {
        return register[instruction[1]] * register[instruction[2]];
    }

    private static int muli(int[] register, int[] instruction) {
        return register[instruction[1]] * instruction[2];
    }

    private static int banr(int[] register, int[] instruction) {
        return register[instruction[1]] & register[instruction[2]];
    }

    private static int bani(int[] register, int[] instruction) {
        return register[instruction[1]] & instruction[2];
    }

    private static int borr(int[] register, int[] instruction) {
        return register[instruction[1]] | register[instruction[2]];
    }

    private static int bori(int[] registr, int[] instruction) {
        return registr[instruction[1]] | instruction[2];
    }

    private static int setr(int[] before, int[] instruction) {
        return before[instruction[1]];
    }

    private static int seti(int[] register, int[] instruction) {
        return instruction[1];
    }

    private static int gtir(int[] register, int[] instruction) {
        return instruction[1] > register[instruction[2]] ? 1 : 0;
    }

    private static int gtri(int[] register, int[] instruction) {
        return register[instruction[1]] > instruction[2] ? 1 : 0;
    }

    private static int gtrr(int[] register, int[] instruction) {
        return register[instruction[1]] > register[instruction[2]] ? 1 : 0;
    }

    private static int eqir(int[] register, int[] instruction) {
        return instruction[1] == register[instruction[2]] ? 1 : 0;
    }

    private static int eqri(int[] register, int[] instruction) {
        return register[instruction[1]] == instruction[2] ? 1 : 0;
    }

    private static int eqrr(int[] register, int[] instruction) {
        return register[instruction[1]] == register[instruction[2]] ? 1 : 0;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
