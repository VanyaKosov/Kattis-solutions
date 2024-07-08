import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numTests = Integer.parseInt(reader.readLine());
        for (int i = 0; i < numTests; i++) {
            if (i != 0) {
                writer.newLine();
            }

            String[] info = reader.readLine().split(" ");
            int sum = Integer.parseInt(info[0]);
            int diff = Integer.parseInt(info[1]);

            if ((sum + diff) % 2 != 0) {
                writer.write("impossible");
                continue;
            }

            int first = (sum + diff) / 2;
            int second = sum - first;

            if (first < 0 || second < 0) {
                writer.write("impossible");
                continue;
            }

            writer.write(Integer.toString(Math.max(first, second)));
            writer.write(" ");
            writer.write(Integer.toString(Math.min(first, second)));
        }

        writer.close();
    }
}
