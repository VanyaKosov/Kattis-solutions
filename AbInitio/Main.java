import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final long DIVISOR = 1000000007;
    private static ArrayList<HashSet<Integer>> graph = new ArrayList<>(2000);
    private static ArrayList<HashSet<Integer>> transposeGraph = new ArrayList<>(2000);
    private static boolean complementary = false;

    public static void main(String[] args) throws IOException {
        String[] startingInfo = READER.readLine().split(" ");
        int vertexCount = toInt(startingInfo[0]);
        int edgeCount = toInt(startingInfo[1]);
        int numQueries = toInt(startingInfo[2]);

        for (int i = 0; i < vertexCount; i++) {
            addVertex(i);
        }

        for (int i = 0; i < edgeCount; i++) {
            String[] edgeInfo = READER.readLine().split(" ");
            int from = toInt(edgeInfo[0]);
            int to = toInt(edgeInfo[1]);

            addEdge(from, to);
        }

        for (; numQueries > 0; numQueries--) {
            String[] info = READER.readLine().split(" ");
            int action = toInt(info[0]);
            if (action == 1) { // Create a new vertex
                addVertex(graph.size());

                continue;
            }

            if (action == 2) { // Create a new edge
                int from = toInt(info[1]);
                int to = toInt(info[2]);
                addEdge(from, to);

                continue;
            }

            if (action == 3) { // Delete all incoming and outgoing edges of a vertex
                int vertex = toInt(info[1]);
                if (complementary) {
                    for (int i = 0; i < graph.size(); i++) {
                        if (i == vertex) {
                            continue;
                        }

                        graph.get(vertex).add(i);
                        graph.get(i).add(vertex);
                        transposeGraph.get(vertex).add(i);
                        transposeGraph.get(i).add(vertex);
                    }

                    continue;
                }

                for (int otherVertex : graph.get(vertex).stream().toList()) {
                    removeEdge(vertex, otherVertex);
                }

                for (int otherVertex : transposeGraph.get(vertex).stream().toList()) {
                    removeEdge(otherVertex, vertex);
                }

                continue;
            }

            if (action == 4) { // Remove an edge
                int from = toInt(info[1]);
                int to = toInt(info[2]);
                removeEdge(from, to);

                continue;
            }

            if (action == 5) { // Reverse edge directions
                var temp = graph;
                graph = transposeGraph;
                transposeGraph = temp;

                continue;
            }

            complementary = complementary ? false : true; // Switch edge and not edge
        }

        output(graph.size());
        WRITER.close();
    }

    private static void output(int vertexCount) throws IOException {
        WRITER.write(toString(vertexCount));
        WRITER.newLine();

        for (int i = 0; i < vertexCount; i++) {
            var vertex = graph.get(i);
            if (complementary) {
                WRITER.write(toString(vertexCount - vertex.size() - 1)); // Minus 1 because can't connect to itself
            } else {
                WRITER.write(toString(vertex.size()));
            }
            WRITER.write(' ');

            long hash = 0;
            for (int j = vertexCount - 1; j >= 0; j--) {
                if (i == j) {
                    continue;
                }

                if (complementary) {
                    if (vertex.contains(j)) {
                        continue;
                    }
                } else {
                    if (!vertex.contains(j)) {
                        continue;
                    }
                }

                hash = (hash * 7 + j) % DIVISOR;
            }

            WRITER.write(Long.toString(hash));
            WRITER.newLine();
        }
    }

    private static void addVertex(int vertex) {
        graph.add(vertex, new HashSet<>(2000));
        transposeGraph.add(vertex, new HashSet<>(2000));
    }

    private static void addEdge(int from, int to) {
        if (!complementary) {
            graph.get(from).add(to);
            transposeGraph.get(to).add(from);

            return;
        }

        graph.get(from).remove(to);
        transposeGraph.get(to).remove(from);
    }

    private static void removeEdge(int from, int to) {
        if (!complementary) {
            graph.get(from).remove(to);
            transposeGraph.get(to).remove(from);

            return;
        }

        graph.get(from).add(to);
        transposeGraph.get(to).add(from);
    }

    private static int toInt(String num) {
        return Integer.parseInt(num);
    }

    private static String toString(int num) {
        return Integer.toString(num);
    }
}
