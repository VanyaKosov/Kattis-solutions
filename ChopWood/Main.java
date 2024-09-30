import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numLines = Integer.parseInt(READER.readLine());
        int numNodes = numLines + 1;
        int[] v = new int[numLines];
        int[] numConnections = new int[numNodes + 1]; // Ignore index 0
        for (int i = 0; i < numLines; i++) {
            int num = Integer.parseInt(READER.readLine());
            v[i] = num;
            numConnections[num]++;
        }

        PriorityQueue<Integer> leaves = new PriorityQueue<>();
        for (int i = 1; i < numConnections.length; i++) {
            if (numConnections[i] == 0) {
                leaves.add(i);
            }
        }

        int[] u = new int[numLines];
        for (int i = 0; i < numLines; i++) {
            if (v[i] == 0 || leaves.isEmpty()) {
                error();
                return;
            }

            u[i] = leaves.poll();
            if (u[i] == numNodes) {
                error();
                return;
            }
            numConnections[v[i]]--;
            if (numConnections[v[i]] == 0) {
                leaves.add(v[i]);
            }
        }

        for (int num : u) {
            WRITER.write(Integer.toString(num));
            WRITER.newLine();
        }

        WRITER.close();
    }

    private static void error() throws IOException {
        WRITER.write("Error");
        WRITER.close();
    }
}
