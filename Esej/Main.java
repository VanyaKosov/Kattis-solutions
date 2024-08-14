import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] info = READER.readLine().split(" ");
        int maxWordsNeeded = Integer.parseInt(info[1]);

        char[] word = new char[4];
        Arrays.fill(word, 'a');
        int currentIndex = 0;
        for (int i = 0; i < maxWordsNeeded; i++) {
            if (i != 0) {
                WRITER.write(" ");
            }

            word[currentIndex]++;
            while (word[currentIndex] > 'z') {
                word[currentIndex] = 'a';
                currentIndex++;
                word[currentIndex]++;
            }
            currentIndex = 0;

            WRITER.write(new String(word));
        }

        WRITER.newLine();
        WRITER.close();
    }
}
