import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String first = reader.readLine();
        String second = reader.readLine();
        int numZeroes = second.length() - 1;
        if (numZeroes == 0) {
            writer.write(first);
            writer.close();
            return;
        }

        writer.write(divide(first, numZeroes));
        writer.close();
    }

    private static String divide(String first, int numZeroes) {
        var firstNums = new ArrayList<Character>();
        firstNums.add('.');
        for (int i = first.length() - 1; i >= 0; i--) {
            firstNums.add(first.charAt(i));
        }

        for (int i = 0; i < numZeroes; i++) {
            if (i + 1 == firstNums.size()) {
                firstNums.add('0');
            }
            var nextNum = firstNums.get(i + 1);

            firstNums.set(i, nextNum);
            firstNums.set(i + 1, '.');
        }

        if (firstNums.get(firstNums.size() - 1) == '.') {
            firstNums.add('0');
        }

        for (int i = 0; i < firstNums.size(); i++) {
            if (firstNums.get(i) == '.') {
                firstNums.remove(i);
                break;
            }
            if (firstNums.get(i) != '0') {
                break;
            }

            firstNums.remove(i);
            i--; // The size of the list has changed
        }

        StringBuilder result = new StringBuilder();
        for (Character num : firstNums) {
            result.append(num);
        }
        result.reverse();

        return result.toString();
    }
}
