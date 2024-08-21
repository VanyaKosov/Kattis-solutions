import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int[] sequence = readSequence();
        Node[] nodes = new Node[sequence.length];
        Node startingNode = new Node(0, 0, nodes.length - 1);
        Arrays.fill(nodes, startingNode);

        int counter = 0;
        for (int i = 0; i < sequence.length; i++) {
            int currentNum = sequence[i];
            counter += nodes[currentNum].depth;
            WRITER.write(Integer.toString(counter));
            WRITER.newLine();

            int rightSegmentLength = nodes[currentNum].segmentEnd - currentNum;
            int leftSegmentLength = currentNum - nodes[currentNum].segmentStart;

            if (rightSegmentLength < leftSegmentLength) {
                replaceNodes(nodes, currentNum + 1, nodes[currentNum].segmentEnd);
                nodes[currentNum].depth++;
                nodes[currentNum].segmentEnd = currentNum - 1;
            } else {
                replaceNodes(nodes, nodes[currentNum].segmentStart, currentNum - 1);
                nodes[currentNum].depth++;
                nodes[currentNum].segmentStart = currentNum + 1;
            }
        }

        WRITER.close();
    }

    private static void replaceNodes(Node[] nodes, int from, int to) {
        if (from > to) {
            return;
        }

        Node node = new Node(nodes[from].depth + 1, from, to);
        for (int i = from; i <= to; i++) {
            nodes[i] = node;
        }
    }

    private static int[] readSequence() throws IOException {
        int length = Integer.parseInt(READER.readLine());
        int[] sequence = new int[length];

        for (int i = 0; i < length; i++) {
            sequence[i] = Integer.parseInt(READER.readLine()) - 1;
        }

        return sequence;
    }

    private static class Node {
        public int depth;
        public int segmentStart;
        public int segmentEnd;

        public Node(int depth, int segmentStart, int segmentEnd) {
            this.depth = depth;
            this.segmentStart = segmentStart;
            this.segmentEnd = segmentEnd;
        }
    }
}
