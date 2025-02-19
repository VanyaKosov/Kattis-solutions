import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in), 10000000);
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out), 10000000);

    public static void main(String[] args) throws IOException {
        String[] startingInfo = READER.readLine().split(" ");
        int nodeCount = Integer.parseInt(startingInfo[0]);
        int[] sets = new int[nodeCount];
        for (int i = 0; i < sets.length; i++) {
            sets[i] = i;
        }

        int operationsLeft = Integer.parseInt(startingInfo[1]);
        for (; operationsLeft > 0; operationsLeft--) {
            String[] info = READER.readLine().split(" ");
            int a = Integer.parseInt(info[1]);
            int b = Integer.parseInt(info[2]);

            int aRoot = findRoot(sets, a);
            int bRoot = findRoot(sets, b);
            if (info[0].equals("=")) {
                sets[bRoot] = aRoot;
            } else {
                if (aRoot == bRoot) {
                    WRITER.write("yes");
                } else {
                    WRITER.write("no");
                }
                WRITER.newLine();
            }
        }

        WRITER.close();
    }

    private static int findRoot(int[] sets, int node) {
        int root = sets[node];
        if (node != root) {
            root = findRoot(sets, root);
            sets[node] = root;
        }

        return root;
    }
}