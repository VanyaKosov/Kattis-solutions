import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String customer;
        while ((customer = reader.readLine()) != null) {
            String[] info = customer.split(" ");
            List<String> name = Arrays.stream(info)
                    .filter(a -> a.charAt(0) > '9')
                    .toList();

            double[] measurements = Arrays.stream(info)
                    .filter(a -> a.charAt(0) <= '9')
                    .mapToDouble(a -> Double.parseDouble(a)).toArray();

            double average = Arrays.stream(measurements).average().getAsDouble();

            writer.write(Double.toString(average));
            for (String namePart : name) {
                writer.write(" " + namePart);
            }
            writer.newLine();
        }

        writer.close();
    }
}
