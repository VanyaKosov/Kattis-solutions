import java.io.*;
import java.util.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        long numCandies = Long.parseLong(READER.readLine());

        var firstHalf = new ArrayList<Long>();
        firstHalf.add(0L);
        var secondHalf = new ArrayList<Long>(); // Read backwards
        for (long i = 1; i * i < numCandies; i++) {

            if (numCandies % (i + 1) == 0) {
                firstHalf.add(Long.valueOf(i));
                secondHalf.add(Long.valueOf(numCandies / (i + 1) - 1));
            }

            if (firstHalf.size() > 0 && secondHalf.size() > 0
                    && firstHalf.get(firstHalf.size() - 1) > secondHalf.get(secondHalf.size() - 1)) {
                firstHalf.remove(firstHalf.size() - 1);
                secondHalf.remove(secondHalf.size() - 1);
                break;
            }
        }

        if (firstHalf.size() > 0 && secondHalf.size() > 0
                && firstHalf.get(firstHalf.size() - 1).equals(secondHalf.get(secondHalf.size() - 1))) {
            secondHalf.remove(secondHalf.size() - 1);
        }

        for (Long num : firstHalf) {
            WRITER.write(Long.toString(num));
            WRITER.write(' ');
        }
        for (int i = secondHalf.size() - 1; i >= 0; i--) {
            WRITER.write(Long.toString(secondHalf.get(i)));
            WRITER.write(' ');
        }

        WRITER.write(Long.toString(numCandies - 1));

        WRITER.close();
    }
}
