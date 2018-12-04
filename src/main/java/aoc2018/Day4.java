package aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2018/day/4
 */
public class Day4 {
    private static final String INPUT = "src/main/resources/aoc2018/day4.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm");
    private static final Pattern ID_PATTERN = Pattern.compile(".*#(\\d+).*");
    private static final Pattern MIN_PATTERN = Pattern.compile(
        "\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:(\\d{2})\\].*");

    private static int part1() throws IOException {
        List<String> lines = new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                dates.add(getDate(line));
            }
        }
        Collections.sort(lines, Comparator.comparing(Day4::getDate));
        Map<Integer, SleepInfo> map = new HashMap<>();
        int id = 0;
        int sleep = 0;
        for (String line : lines) {
            if (line.contains("Guard #")) {
                Matcher matcher = ID_PATTERN.matcher(line);
                if (matcher.matches()) {
                    id = Integer.parseInt(matcher.group(1));
                }
            } else if (line.contains("falls asleep")) {
                Matcher matcher = MIN_PATTERN.matcher(line);
                if (matcher.matches()) {
                    sleep = Integer.parseInt(matcher.group(1));
                }
            } else { // wakes up
                Matcher matcher = MIN_PATTERN.matcher(line);
                if (matcher.matches()) {
                    int wakeUp = Integer.parseInt(matcher.group(1));
                    int time = wakeUp - sleep;
                    if (!map.containsKey(id)) {
                        Map<Integer, Integer> minuteCount = new HashMap<>();
                        for (int min = sleep; min < wakeUp; min++) {
                            if (!minuteCount.containsKey(min)) {
                                minuteCount.put(min, 1);
                            } else {
                                minuteCount.put(min, minuteCount.get(min) + 1);
                            }
                        }
                        map.put(id, new SleepInfo(time, minuteCount));
                    } else {
                        SleepInfo sleepInfo = map.get(id);
                        for (int min = sleep; min < wakeUp; min++) {
                            if (!sleepInfo.minuteCount.containsKey(min)) {
                                sleepInfo.minuteCount.put(min, 1);
                            } else {
                                sleepInfo.minuteCount.put(min,
                                    sleepInfo.minuteCount.get(min) + 1);
                            }
                        }
                        map.get(id).sleepTime += time;
                    }
                }
            }
        }
        int maxId = 0;
        int maxSleep = 0;
        for (Map.Entry<Integer, SleepInfo> e : map.entrySet()) {
            if (maxSleep <= e.getValue().sleepTime) {
                maxId = e.getKey();
                maxSleep = e.getValue().sleepTime;
            }
        }
        int maxMinute = 0;
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> e : map.get(maxId).minuteCount.entrySet()) {
            if (maxCount <= e.getValue()) {
                maxMinute = e.getKey();
                maxCount = e.getValue();
            }
        }
        return maxId * maxMinute;
    }

    private static class SleepInfo {
        private int sleepTime;
        private final Map<Integer, Integer> minuteCount;

        public SleepInfo(int sleepTime, Map<Integer, Integer> minuteCount) {
            this.sleepTime = sleepTime;
            this.minuteCount = minuteCount;
        }
    }

    private static int part2() throws IOException {
        List<String> lines = new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(INPUT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                dates.add(getDate(line));
            }
        }
        Collections.sort(lines, Comparator.comparing(Day4::getDate));
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        int id = 0;
        int sleep = 0;
        for (String line : lines) {
            if (line.contains("Guard #")) {
                Matcher matcher = ID_PATTERN.matcher(line);
                if (matcher.matches()) {
                    id = Integer.parseInt(matcher.group(1));
                }
            } else if (line.contains("falls asleep")) {
                Matcher matcher = MIN_PATTERN.matcher(line);
                if (matcher.matches()) {
                    sleep = Integer.parseInt(matcher.group(1));
                }
            } else { // wakes up
                Matcher matcher = MIN_PATTERN.matcher(line);
                if (matcher.matches()) {
                    int wakeUp = Integer.parseInt(matcher.group(1));
                    if (!map.containsKey(id)) {
                        Map<Integer, Integer> minCount = new HashMap<>();
                        for (int min = sleep; min < wakeUp; min++) {
                            if (!minCount.containsKey(min)) {
                                minCount.put(min, 1);
                            } else {
                                minCount.put(min, minCount.get(min) + 1);
                            }
                        }
                        map.put(id, minCount);
                    } else {
                        Map<Integer, Integer> minuteCount = map.get(id);
                        for (int min = sleep; min < wakeUp; min++) {
                            if (!minuteCount.containsKey(min)) {
                                minuteCount.put(min, 1);
                            } else {
                                minuteCount.put(min, minuteCount.get(min) + 1);
                            }
                        }
                    }
                }
            }
        }
        int maxId = 0;
        int maxMinute = 0;
        int maxCount = 0;
        for (Map.Entry<Integer, Map<Integer, Integer>> e : map.entrySet()) {
            int minute = 0;
            int count = 0;
            for (Map.Entry<Integer, Integer> m : e.getValue().entrySet()) {
                if (count <= m.getValue()) {
                    minute = m.getKey();
                    count = m.getValue();
                }
            }
            if (maxCount <= count) {
                maxId = e.getKey();
                maxCount = count;
                maxMinute = minute;
            }
        }
        return maxId * maxMinute;
    }

    private static Date getDate(String line) {
        StringBuilder date = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '[') {
                continue;
            }
            if (line.charAt(i) == ']') {
                break;
            }
            date.append(line.charAt(i));
        }
        try {
            return DATE_FORMAT.parse(date.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
