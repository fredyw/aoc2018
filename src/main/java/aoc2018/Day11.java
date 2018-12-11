package aoc2018;

/**
 * https://adventofcode.com/2018/day/11
 */
public class Day11 {
    private static String part1(int serialNumber) {
        String xy = null;
        int max = 0;
        int n = 300;
        int[][] powers = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                powers[i][j] = powerLevel(i + 1, j + 1, serialNumber);
            }
        }
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                if (x + 2 > n || y + 2 > n) {
                    continue;
                }
                int sum = 0;
                for (int i = x; i <= x + 2; i++) {
                    for (int j = y; j <= y + 2; j++) {
                        sum += powers[i - 1][j - 1];
                    }
                }
                if (sum > max) {
                    max = sum;
                    xy = x + "," + y;
                }
            }
        }
        return xy;
    }

    private static int powerLevel(int x, int y, int serialNumber) {
        int rackId = x + 10;
        int powerLevel = y * rackId;
        powerLevel += serialNumber;
        powerLevel *= rackId;
        powerLevel /= 100;
        powerLevel %= 10;
        powerLevel -= 5;
        return powerLevel;
    }

    private static String part2(int serialNumber) {
        String xy = null;
        int max = 0;
        int n = 300;
        int[][] powers = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                powers[i][j] = powerLevel(i + 1, j + 1, serialNumber);
            }
        }
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                for (int size = 1; size < n; size++) {
                    if (x + size > n || y + size > n) {
                        continue;
                    }
                    int sum = 0;
                    for (int i = x; i < x + size; i++) {
                        for (int j = y; j < y + size; j++) {
                            sum += powers[i - 1][j - 1];
                        }
                    }
                    if (sum > max) {
                        max = sum;
                        xy = x + "," + y + "," + size;
                    }
                }
            }
        }
        return xy;
    }

    public static void main(String[] args) {
        int serialNumber = 9798;
        System.out.println(part1(serialNumber));
        System.out.println(part2(serialNumber));
    }
}
