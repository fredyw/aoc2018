package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * https://adventofcode.com/2018/day/19
 */
public class Day19 {
    private static final String INPUT = "src/main/resources/aoc2018/day19.txt";

    private static int part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        int ip = Integer.parseInt(lines.get(0).split(" ")[1]);
        int i = 0;
        int[] register = new int[6];
        List<String> instructions = lines.subList(1, lines.size());
        while (i < instructions.size()) {
            String[] split = instructions.get(i).split(" ");
            String opcode = split[0];
            int[] instruction = new int[3];
            for (int j = 0; j < instruction.length; j++) {
                instruction[j] = Integer.parseInt(split[j + 1]);
            }
            if (opcode.equals("addr")) {
                register[instruction[2]] = addr(register, instruction);
            } else if (opcode.equals("addi")) {
                register[instruction[2]] = addi(register, instruction);
            } else if (opcode.equals("mulr")) {
                register[instruction[2]] = mulr(register, instruction);
            } else if (opcode.equals("muli")) {
                register[instruction[2]] = muli(register, instruction);
            } else if (opcode.equals("banr")) {
                register[instruction[2]] = banr(register, instruction);
            } else if (opcode.equals("bani")) {
                register[instruction[2]] = bani(register, instruction);
            } else if (opcode.equals("borr")) {
                register[instruction[2]] = borr(register, instruction);
            } else if (opcode.equals("bori")) {
                register[instruction[2]] = bori(register, instruction);
            } else if (opcode.equals("setr")) {
                register[instruction[2]] = setr(register, instruction);
            } else if (opcode.equals("seti")) {
                register[instruction[2]] = seti(register, instruction);
            } else if (opcode.equals("gtir")) {
                register[instruction[2]] = gtir(register, instruction);
            } else if (opcode.equals("gtri")) {
                register[instruction[2]] = gtri(register, instruction);
            } else if (opcode.equals("gtrr")) {
                register[instruction[2]] = gtrr(register, instruction);
            } else if (opcode.equals("eqir")) {
                register[instruction[2]] = eqir(register, instruction);
            } else if (opcode.equals("eqri")) {
                register[instruction[2]] = eqri(register, instruction);
            } else if (opcode.equals("eqrr")) {
                register[instruction[2]] = eqrr(register, instruction);
            } else {
                throw new RuntimeException("Boom!");
            }
            register[ip]++;
            i = register[ip];
        }
        return register[0];
    }

    private static int addr(int[] register, int[] instruction) {
        return register[instruction[0]] + register[instruction[1]];
    }

    private static int addi(int[] register, int[] instruction) {
        return register[instruction[0]] + instruction[1];
    }

    private static int mulr(int[] register, int[] instruction) {
        return register[instruction[0]] * register[instruction[1]];
    }

    private static int muli(int[] register, int[] instruction) {
        return register[instruction[0]] * instruction[1];
    }

    private static int banr(int[] register, int[] instruction) {
        return register[instruction[0]] & register[instruction[1]];
    }

    private static int bani(int[] register, int[] instruction) {
        return register[instruction[0]] & instruction[1];
    }

    private static int borr(int[] register, int[] instruction) {
        return register[instruction[0]] | register[instruction[1]];
    }

    private static int bori(int[] register, int[] instruction) {
        return register[instruction[0]] | instruction[1];
    }

    private static int setr(int[] before, int[] instruction) {
        return before[instruction[0]];
    }

    private static int seti(int[] register, int[] instruction) {
        return instruction[0];
    }

    private static int gtir(int[] register, int[] instruction) {
        return instruction[0] > register[instruction[1]] ? 1 : 0;
    }

    private static int gtri(int[] register, int[] instruction) {
        return register[instruction[0]] > instruction[1] ? 1 : 0;
    }

    private static int gtrr(int[] register, int[] instruction) {
        return register[instruction[0]] > register[instruction[1]] ? 1 : 0;
    }

    private static int eqir(int[] register, int[] instruction) {
        return instruction[0] == register[instruction[1]] ? 1 : 0;
    }

    private static int eqri(int[] register, int[] instruction) {
        return register[instruction[0]] == instruction[1] ? 1 : 0;
    }

    private static int eqrr(int[] register, int[] instruction) {
        return register[instruction[0]] == register[instruction[1]] ? 1 : 0;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
    }
}
