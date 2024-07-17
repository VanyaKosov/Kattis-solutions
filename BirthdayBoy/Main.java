import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final int[] monthDayCount = {
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public static void main(String[] args) throws IOException {
        List<Integer> birthdays = readBirthdays();
        var gaps = new ArrayList<Gap>();
        for (int i = 0; i < birthdays.size(); i++) {
            if (i == birthdays.size() - 1) {
                gaps.add(new Gap(364 - birthdays.get(birthdays.size() - 1) + birthdays.get(0) + 1, birthdays.get(0)));
                break;
            }
            gaps.add(new Gap(birthdays.get(i + 1) - birthdays.get(i), birthdays.get(i + 1)));
        }

        List<Gap> sortedGaps = gaps.stream().sorted((a, b) -> b.size - a.size).toList();
        int maxSize = sortedGaps.get(0).size;
        sortedGaps = sortedGaps.stream()
                .filter(a -> a.size == maxSize)
                .sorted((a, b) -> a.lastDate - b.lastDate)
                .toList();
        int october29th = dateToDays(10, 29);

        Gap answer;
        var answerOpt = sortedGaps.stream()
                .filter(a -> a.lastDate >= october29th)
                .min((a, b) -> a.lastDate - b.lastDate);
        if (answerOpt.isPresent()) {
            answer = answerOpt.get();
        } else {
            answer = sortedGaps.stream().min((a, b) -> a.lastDate - b.lastDate).get();
        }

        writer.write(getDate(answer.lastDate));
        writer.close();
    }

    private static String getDate(int days) {
        if (days == 0) {
            days = 365;
        }

        int monthCount = 1;
        for (int i = 1; i < monthDayCount.length; i++) {
            if (days - monthDayCount[i] > 0) {
                monthCount++;
                days -= monthDayCount[i];
                continue;
            }

            break;
        }

        return String.format("%02d-%02d", monthCount, days);
    }

    private static List<Integer> readBirthdays() throws IOException {
        int lineCount = Integer.parseInt(reader.readLine());
        var birthdays = new ArrayList<Integer>();
        for (; lineCount > 0; lineCount--) {
            String line = reader.readLine();
            int length = line.length();
            int month = Integer.parseInt(line.substring(length - 5, length - 3));
            int day = Integer.parseInt(line.substring(length - 2, length));

            birthdays.add(dateToDays(month, day));
        }

        return birthdays.stream().sorted().toList();
    }

    private static int dateToDays(int month, int day) {
        int daysCount = day - 1;
        for (int i = month - 1; i > 0; i--) {
            daysCount += monthDayCount[i];
        }

        return daysCount;
    }

    private static class Gap {
        public final int size;
        public final int lastDate;

        public Gap(int size, int lastDate) {
            this.size = size;
            this.lastDate = lastDate;
        }
    }
}
