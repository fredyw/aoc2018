package aoc2018;

import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2018/day/14
 */
public class Day14 {
    private static String part1(int input) {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(7);
        int size = input + 10;
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
        for (int x = input; x < list.size(); x++) {
            answer.append(list.get(x));
        }
        return answer.toString();
    }

    private static int part2(int input) {
        StringBuilder sb = new StringBuilder();
        sb.append(3);
        sb.append(7);
        int i = 0;
        int j = 1;
        String sRecipe = Integer.toString(input);
        while (true) {
            int sum = sb.charAt(i) - '0' + sb.charAt(j) - '0';
            if (sum < 10) {
                sb.append(sum);
            } else {
                String s = Integer.toString(sum);
                sb.append(s.charAt(0));
                sb.append(s.charAt(1));
            }
            i = (i + sb.charAt(i) - '0' + 1) % sb.length();
            j = (j + sb.charAt(j) - '0' + 1) % sb.length();
            // There can be maximum 2 new elements added.
            if (sb.length() - sRecipe.length() - 2 >= 0) {
                if (sb.substring(sb.length() - sRecipe.length() - 2).contains(sRecipe)) {
                    break;
                }
            }
        }
        return sb.indexOf(sRecipe);
    }

    public static void main(String[] args) {
        int input = 894501;
        System.out.println(part1(input));
        System.out.println(part2(input));
    }
}
