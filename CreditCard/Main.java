import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int testCount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < testCount; i++) {
            String[] info = reader.readLine().split(" ");
            double interestPercent = Double.parseDouble(info[0]) / 100.0;
            double balance = Double.parseDouble(info[1]);
            double payment = Double.parseDouble(info[2]);

            Integer answer = null;
            for (int monthCount = 1; monthCount <= 1200; monthCount++) {
                balance += balance * interestPercent;
                balance = Math.round(balance * 100.0) / 100.0;
                balance -= payment;

                if (balance <= 0) {
                    answer = monthCount;
                    break;
                }
            }

            if (i > 0) {
                writer.newLine();
            }
            if (answer == null) {
                writer.write("impossible");
                continue;
            }
            writer.write(Integer.toString(answer));
        }

        writer.close();
    }
}
