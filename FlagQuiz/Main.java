import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[][] options = getOptions();
        if (options.length == 1) {
            WRITER.write(flagToString(options[0]));
            WRITER.close();
            return;
        }

        int[] maxChanges = new int[options.length];
        for (int flagNum1 = 0; flagNum1 < options.length; flagNum1++) {
            int[] changes = new int[options.length];
            for (int flagNum2 = 0; flagNum2 < options.length; flagNum2++) {
                for (int partNum = 0; partNum < options[0].length; partNum++) {

                    if (!options[flagNum1][partNum].equals(options[flagNum2][partNum])) {
                        changes[flagNum2]++;
                    }
                }
            }
            maxChanges[flagNum1] = Arrays.stream(changes).max().getAsInt();
        }

        int minChangesNeeded = Arrays.stream(maxChanges).min().getAsInt();
        for (int i = 0; i < options.length; i++) {
            if (maxChanges[i] == minChangesNeeded) {
                WRITER.write(flagToString(options[i]));
                WRITER.newLine();
            }
        }

        WRITER.close();
    }

    private static String flagToString(String[] flag) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < flag.length; i++) {
            if (i != 0) {
                result.append(", ");
            }
            result.append(flag[i]);
        }

        return result.toString();
    }

    private static String[][] getOptions() throws IOException {
        READER.readLine();
        int numOptions = Integer.parseInt(READER.readLine());
        String[] firstLineOptions = READER.readLine().split(", ");
        String[][] options = new String[numOptions][firstLineOptions.length];
        options[0] = firstLineOptions;
        for (int i = 1; i < numOptions; i++) { // Already added the first option
            String[] lineOptions = READER.readLine().split(", ");
            options[i] = lineOptions;
        }

        return options;
    }
}
