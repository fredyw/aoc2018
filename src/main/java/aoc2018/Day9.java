package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2018/day/9
 */
public class Day9 {
    private static final String INPUT = "src/main/resources/aoc2018/day9.txt";
    private static final Pattern PATTERN = Pattern.compile(
        "(\\d+) players; last marble is worth (\\d+) points");

    private static long part1() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line = reader.readLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int nPlayers = Integer.parseInt(matcher.group(1));
                int nMarbles = Integer.parseInt(matcher.group(2));
                return highestScore(nPlayers, nMarbles);
            }
            return 0;
        }
    }

    private static long part2() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line = reader.readLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int nPlayers = Integer.parseInt(matcher.group(1));
                int nMarbles = Integer.parseInt(matcher.group(2));
                return highestScore(nPlayers, nMarbles * 100);
            }
            return 0;
        }
    }

    private static long highestScore(int nPlayers, int nMarbles) {
        long[] players = new long[nPlayers + 1];
        LinkedList<Integer> marbles = new LinkedList<>();
        int curPlayer = 0;
        for (int i = 0; i <= nMarbles; i++) {
            if (i == 0) {
                marbles.add(i);
            } else if (i % 23 == 0) {
                for (int j = 0; j < 6; j++) {
                    Integer val = marbles.removeFirst();
                    marbles.addLast(val);
                }
                players[curPlayer] += i + marbles.pollFirst();
            } else {
                for (int j = 0; j < 2; j++) {
                    Integer val = marbles.removeLast();
                    marbles.addFirst(val);
                }
                marbles.addLast(i);
            }
            curPlayer++;
            if (curPlayer == nPlayers + 1) {
                curPlayer = 1;
            }
        }
        return Arrays.stream(players).max().getAsLong();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
