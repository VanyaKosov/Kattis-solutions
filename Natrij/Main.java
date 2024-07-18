import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int[] startInfo = Arrays.stream(reader.readLine().split(":"))
                .mapToInt(a -> Integer.parseInt(a))
                .toArray();
        int[] endInfo = Arrays.stream(reader.readLine().split(":"))
                .mapToInt(a -> Integer.parseInt(a))
                .toArray();

        Time start = new Time(startInfo[0], startInfo[1], startInfo[2]);
        Time end = new Time(endInfo[0], endInfo[1], endInfo[2]);

        if (start.equals(end)) {
            output(new Time(24, 0, 0));
            return;
        }

        if (end.isLessThen(start)) {
            end = new Time(end.hours + 24, end.minutes, end.seconds);
        }

        Time answer = end.diff(start);
        output(answer);
    }

    private static void output(Time time) throws IOException {
        try (writer) {
            writer.write(String.format("%02d:%02d:%02d", time.hours, time.minutes, time.seconds));
        }
    }

    private static class Time {
        public final int hours;
        public final int minutes;
        public final int seconds;

        public Time(int h, int m, int s) {
            hours = h;
            minutes = m;
            seconds = s;
        }

        public Time diff(Time other) {
            int thisSeconds = hours * 60 * 60 + minutes * 60 + seconds;
            int otherSeconds = other.hours * 60 * 60 + other.minutes * 60 + other.seconds;
            int diff = Math.abs(thisSeconds - otherSeconds);
            int diffHours = diff / (60 * 60);
            diff = diff % (60 * 60);
            int diffMinutes = diff / 60;
            diff = diff % 60;
            int diffSeconds = diff;

            return new Time(diffHours, diffMinutes, diffSeconds);
        }

        public boolean isLessThen(Time other) {
            if (hours == other.hours) {
                if (minutes == other.minutes) {
                    return seconds < other.seconds;
                }

                return minutes < other.minutes;
            }

            return hours < other.hours;
        }

        public boolean equals(Time other) {
            return hours == other.hours && minutes == other.minutes && seconds == other.seconds;
        }
    }
}
