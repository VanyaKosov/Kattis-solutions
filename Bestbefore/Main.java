import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String originalDate = reader.readLine();
        String[] mixedDate = originalDate.split("/");
        ArrayList<String[]> allDates = new ArrayList<>();
        generatePermutations(allDates, mixedDate, mixedDate.length - 1);
        var answer = allDates.stream()
                .map(a -> stringDateToInt(a))
                .filter(a -> valiDate(a))
                .min((a, b) -> compareDates(a, b));
        if (answer.isPresent()) {
            writer.write(dateToString(answer.get()));
        } else {
            writer.write(originalDate + " is illegal");
        }

        writer.close();
    }

    private static String dateToString(int[] date) {
        return String.format("%04d-%02d-%02d", date[0], date[1], date[2]);
    }

    private static int compareDates(int[] date1, int[] date2) {
        if (date1[0] == date2[0]) {
            if (date1[1] == date2[1]) {
                return date1[2] - date2[2];
            }
            return date1[1] - date2[1];
        }
        return date1[0] - date2[0];
    }

    private static int[] stringDateToInt(String[] date) {
        String sYear = date[2];
        String sMonth = date[1];
        String sDay = date[0];

        if (sYear.length() == 1) {
            sYear = "200" + sYear;
        } else if (sYear.length() == 2) {
            sYear = "20" + sYear;
        } else if (sYear.length() == 3) {
            sYear = "2" + sYear;
        }

        int year = Integer.parseInt(sYear);
        int month = Integer.parseInt(sMonth);
        int day = Integer.parseInt(sDay);

        return new int[] { year, month, day };
    }

    private static boolean valiDate(int[] date) {
        int year = date[0];
        int month = date[1];
        int day = date[2];

        if (year < 2000 || year > 2999) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > getMonthDayCount(year)[month]) {
            return false;
        }

        return true;
    }

    private static int[] getMonthDayCount(int year) { // Accounting for leap years
        int[] monthDayCount = {
                0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };

        if (year % 4 == 0) {
            if (year % 100 == 0 && year % 400 != 0) {
                return monthDayCount;
            }

            monthDayCount[2] = 29;
            return monthDayCount;
        }

        return monthDayCount;
    }

    private static void generatePermutations(ArrayList<String[]> permutations, String[] date, int lastIndex) {
        if (lastIndex == 0) {
            permutations.add(new String[] { date[0], date[1], date[2] });
            return;
        }

        generatePermutations(permutations, date, lastIndex - 1);

        for (int i = 0; i < lastIndex; i++) {
            if ((lastIndex + 1) % 2 == 0) {
                swap(date, i, lastIndex);
            } else {
                swap(date, 0, lastIndex);
            }
            generatePermutations(permutations, date, lastIndex - 1);
        }
    }

    private static void swap(String[] date, int i1, int i2) {
        String temp = date[i1];
        date[i1] = date[i2];
        date[i2] = temp;
    }
}
