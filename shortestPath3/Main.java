import java.io.*;
import java.util.*;

public class Main {
    public static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {
        while (true) {
            String[] startingInfo = reader.readLine().split(" ");
            int nodeCount = Integer.parseInt(startingInfo[0]);
            int edgeCount = Integer.parseInt(startingInfo[1]);
            int queryCount = Integer.parseInt(startingInfo[2]);
            int start = Integer.parseInt(startingInfo[3]);

            if (nodeCount + edgeCount + queryCount + start == 0) {
                break;
            }

            var edges = new ArrayList<int[]>(edgeCount);
            for (int i = 0; i < edgeCount; i++) {
                int[] edge = Arrays.stream(reader.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
                edges.add(edge);
            }

            long[] distances = new long[nodeCount];
            Arrays.fill(distances, Long.MAX_VALUE);
            distances[start] = 0;
            countDistances(edges, distances, nodeCount);

            doQueries(distances, queryCount);
        }

        reader.close();
        writer.close();
    }

    private static void countDistances(ArrayList<int[]> edges, long[] distances, int nodeCount) {
        for (int i = 0; i < nodeCount - 1; i++) {
            for (var edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];

                if (distances[from] < Long.MAX_VALUE) {
                    distances[to] = Math.min(distances[to], distances[from] + weight);
                }
            }
        }

        for (int i = 0; i < nodeCount - 1; i++) {
            for (var edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];

                if (distances[from] == Long.MIN_VALUE) {
                    distances[to] = Long.MIN_VALUE;
                    continue;
                }

                if (distances[from] < Long.MAX_VALUE && distances[to] > distances[from] + weight) {
                    distances[to] = Long.MIN_VALUE;
                }
            }
        }
    }

    private static void doQueries(long[] distances, int queryCount) throws Exception {
        for (int i = 0; i < queryCount; i++) {
            long distance = distances[Integer.parseInt(reader.readLine())];
            if (distance == Long.MAX_VALUE) {
                writer.write("Impossible");
                writer.newLine();
                continue;
            }

            if (distance == Long.MIN_VALUE) {
                writer.write("-Infinity");
                writer.newLine();
                continue;
            }

            writer.write(Long.toString(distance));
            writer.newLine();
        }
    }
}
