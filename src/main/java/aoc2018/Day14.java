package aoc2018;

import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2018/day/14
 */
public class Day14 {
    private static String part1(int recipe) {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(7);
        int size = recipe + 10;
        int i = 0;
        int j = 1;
        while (list.size() < size) {
            int sum = list.get(i) + list.get(j);
            if (sum < 10) {
                list.add(sum);
            } else {
                list.add(Integer.toString(sum).charAt(0) - '0');
                list.add(Integer.toString(sum).charAt(1) - '0');
            }
            i = (i + list.get(i) + 1) % list.size();
            j = (j + list.get(j) + 1) % list.size();
        }
        StringBuilder answer = new StringBuilder();
        for (int x = recipe; x < list.size(); x++) {
            answer.append(list.get(x));
        }
        return answer.toString();
    }

    public static void main(String[] args) {
        System.out.println(part1(894501));
    }
}
