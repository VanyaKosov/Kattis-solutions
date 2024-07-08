import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int start = Integer.parseInt(reader.readLine());
        int end = Integer.parseInt(reader.readLine());
        int option1 = Math.abs(end - start);
        int option2 = 360 - Math.max(start, end) + Math.min(start, end);

        if (start == end) {
            writer.write("0");
        } else if (option1 == option2) {
            writer.write("180");
        } else if (start < end) {
            writer.write(Integer.toString(option1 < option2 ? option1 : -option2));
        } else {
            writer.write(Integer.toString(option1 < option2 ? -option1 : option2));
        }

        writer.close();
    }
}
