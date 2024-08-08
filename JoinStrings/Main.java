import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Node[] words = getWords();
        int firstWord = 0;
        for (int i = 0; i < words.length - 1; i++) {
            String[] info = READER.readLine().split(" ");
            int a = Integer.parseInt(info[0]) - 1; // To make it zero-based
            int b = Integer.parseInt(info[1]) - 1; // To make it zero-based

            words[a].last.next = words[b];
            words[b].previous = words[a];
            words[b].first = words[a];
            words[a].last = words[b].last;

            firstWord = a;
        }

        Node currentNode = words[firstWord];
        for (int i = 0; i < words.length; i++) {
            WRITER.write(currentNode.word);
            currentNode = currentNode.next;
        }

        WRITER.close();
    }

    private static Node[] getWords() throws IOException {
        Node[] words = new Node[Integer.parseInt(READER.readLine())];
        for (int i = 0; i < words.length; i++) {
            words[i] = new Node(READER.readLine());
            words[i].first = words[i];
            words[i].last = words[i];
        }

        return words;
    }

    private static class Node {
        Node next = null;
        Node previous = null;
        Node first = null;
        Node last = null;
        final String word;

        public Node(String word) {
            this.word = word;
        }
    }
}
