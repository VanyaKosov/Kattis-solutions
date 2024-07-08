import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int passengers = 0;
        String[] params = reader.readLine().split(" ");
        int capacity = Integer.parseInt(params[0]);
        int stationCount = Integer.parseInt(params[1]);
        for (int i = 0; i < stationCount; i++) {
            String[] data = reader.readLine().split(" ");
            int left = Integer.parseInt(data[0]);
            int entered = Integer.parseInt(data[1]);
            int waited = Integer.parseInt(data[2]);

            if (i == 0 && left != 0) {
                impossible();
                return;
            }

            passengers -= left;

            if (passengers < 0) {
                impossible();
                return;
            }

            passengers += entered;

            if (passengers > capacity) {
                impossible();
                return;
            }

            if (capacity - passengers > 0 && waited != 0) {
                impossible();
                return;
            }

            if (i == stationCount - 1 && passengers != 0) {
                impossible();
                return;
            }

            if (i == stationCount - 1 && (entered != 0 || waited != 0)) {
                impossible();
                return;
            }
        }

        writer.write("possible");
        writer.close();
    }

    private static void impossible() throws IOException {
        writer.write("impossible");
        writer.close();
    }
}
