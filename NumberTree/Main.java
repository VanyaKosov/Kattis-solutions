import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] line = READER.readLine().split(" ");
        int depth = Integer.parseInt(line[0]);
        String path = line.length > 1 ? line[1] : "";

        int indexFromTop = 0;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == 'L') {
                indexFromTop = indexFromTop * 2 + 1;
            } else {
                indexFromTop = indexFromTop * 2 + 2;
            }
        }

        int totalElements = 1 << depth + 1;
        WRITER.write(Integer.toString(totalElements - 1 - indexFromTop));
        WRITER.close();
    }
}
