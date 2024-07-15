import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException, FileNotFoundException {
        //var timezones = getTimezones();
        var timezones = timezonesNew;
        var tests = getTests();
        for (int i = 0; i < tests.length; i++) {
            Test test = tests[i];

            int from = timezones.get(test.from);
            int to = timezones.get(test.to);
            int utc = test.time - from;
            int result = utc + to;

            while (result < 0) {
                result += 24 * 60;
            }

            result = result % (24 * 60);
            writer.write(formatTime(result));
            writer.newLine();
        }

        writer.close();
    }

    private static String formatTime(int time) {
        int hours = time / 60;
        int minutes = time % 60;

        if (hours == 0 && minutes == 0) {
            return "midnight";
        }

        if (hours == 12 && minutes == 0) {
            return "noon";
        }

        if (hours == 12) {
            return String.format("%d:%02d p.m.", hours, minutes);
        }

        if (hours == 0) {
            return String.format("%d:%02d a.m.", 12, minutes);
        }

        if (hours > 12) {
            return String.format("%d:%02d p.m.", hours - 12, minutes);
        }

        return String.format("%d:%02d a.m.", hours, minutes);
    }

    private static Test[] getTests() throws IOException {
        int numTests = Integer.parseInt(reader.readLine());
        Test[] tests = new Test[numTests];
        for (int i = 0; i < numTests; i++) {
            String[] test = reader.readLine().split(" ");
            String to = test[test.length - 1];
            String from = test[test.length - 2];
            int startTime = 0;
            if (test[0].equals("noon")) {
                startTime = 12 * 60;
            } else if (test[0].equals("midnight")) {
                startTime = 0;
            } else {
                String[] timeParts = test[0].split(":");
                startTime = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
            }

            if (test[1].equals("p.m.") && startTime / 60 != 12) {
                startTime += 12 * 60;
            }

            tests[i] = new Test(startTime, from, to);
        }

        return tests;
    }

    private static HashMap<String, Integer> getTimezones() throws IOException, FileNotFoundException {
        var fileReader = new BufferedReader(new StringReader(timezonesInfo));
        var timezones = new HashMap<String, Integer>();
        String line = null;
        while ((line = fileReader.readLine()) != null) {
            String[] info = line.split(" ");
            if (info[0].equals("UTC")) {
                timezones.put("UTC", 0);
                continue;
            }

            int timeIndex = info.length - 1;
            if (info[timeIndex].equals("hours") || info[timeIndex].equals("hour")) {
                timeIndex--;
            }

            int offset = 0;
            if (!info[timeIndex].equals("UTC")) {
                String roughOffset = info[timeIndex].substring(3);
                int dotIndex = roughOffset.indexOf(".");
                if (dotIndex == -1) {
                    offset = Integer.parseInt(roughOffset) * 60;
                } else {
                    int hours = Integer.parseInt(roughOffset.substring(0, dotIndex));
                    offset = hours * 60;
                    offset += offset < 0 ? -30 : 30;
                }
            }

            timezones.put(info[0], offset);
        }

        fileReader.close();
        return timezones;
    }

    private static class Test {
        public final int time;
        public final String from;
        public final String to;

        public Test(int time, String from, String to) {
            this.time = time;
            this.from = from;
            this.to = to;
        }
    }

    private static final HashMap<String, Integer> timezonesNew = new HashMap<>() {
        {
            put("UTC", 0);
            put("GMT", 0);
            put("BST", 60);
            put("IST", 60);
            put("WET", 0);
            put("WEST", 60);
            put("CET", 60);
            put("CEST", 2 * 60);
            put("EET", 2 * 60);
            put("EEST", 3 * 60);
            put("MSK", 3 * 60);
            put("MSD", 4 * 60);
            put("AST", -4 * 60);
            put("ADT", -3 * 60);
            put("NST", -3 * 60 - 30);
            put("NDT", -2 * 60 - 30);
            put("EST", -5 * 60);
            put("EDT", -4 * 60);
            put("CST", -6 * 60);
            put("CDT", -5 * 60);
            put("MST", -7 * 60);
            put("MDT", -6 * 60);
            put("PST", -8 * 60);
            put("PDT", -7 * 60);
            put("HST", -10 * 60);
            put("AKST", -9 * 60);
            put("AKDT", -8 * 60);
            put("AEST", 10 * 60);
            put("AEDT", 11 * 60);
            put("ACST", 9 * 60 + 30);
            put("ACDT", 10 * 60 + 30);
            put("AWST", 8 * 60);
        }
    };

    private static final String timezonesInfo = """
            UTC Coordinated Universal Time
            GMT Greenwich Mean Time, defined as UTC
            BST British Summer Time, defined as UTC+1 hour
            IST Irish Summer Time, defined as UTC+1 hour
            WET Western Europe Time, defined as UTC
            WEST Western Europe Summer Time, defined as UTC+1 hour
            CET Central Europe Time, defined as UTC+1
            CEST Central Europe Summer Time, defined as UTC+2
            EET Eastern Europe Time, defined as UTC+2
            EEST Eastern Europe Summer Time, defined as UTC+3
            MSK Moscow Time, defined as UTC+3
            MSD Moscow Summer Time, defined as UTC+4
            AST Atlantic Standard Time, defined as UTC-4 hours
            ADT Atlantic Daylight Time, defined as UTC-3 hours
            NST Newfoundland Standard Time, defined as UTC-3.5 hours
            NDT Newfoundland Daylight Time, defined as UTC-2.5 hours
            EST Eastern Standard Time, defined as UTC-5 hours
            EDT Eastern Daylight Saving Time, defined as UTC-4 hours
            CST Central Standard Time, defined as UTC-6 hours
            CDT Central Daylight Saving Time, defined as UTC-5 hours
            MST Mountain Standard Time, defined as UTC-7 hours
            MDT Mountain Daylight Saving Time, defined as UTC-6 hours
            PST Pacific Standard Time, defined as UTC-8 hours
            PDT Pacific Daylight Saving Time, defined as UTC-7 hours
            HST Hawaiian Standard Time, defined as UTC-10 hours
            AKST Alaska Standard Time, defined as UTC-9 hours
            AKDT Alaska Standard Daylight Saving Time, defined as UTC-8 hours
            AEST Australian Eastern Standard Time, defined as UTC+10 hours
            AEDT Australian Eastern Daylight Time, defined as UTC+11 hours
            ACST Australian Central Standard Time, defined as UTC+9.5 hours
            ACDT Australian Central Daylight Time, defined as UTC+10.5 hours
            AWST Australian Western Standard Time, defined as UTC+8 hours
            """;
}
