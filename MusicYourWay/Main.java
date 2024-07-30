import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static boolean isFirstOutput = true;

    public static void main(String[] args) throws IOException {
        String[] attributes = READER.readLine().split(" ");
        HashMap<String, Integer> attributeIndex = new HashMap<>();
        for (int i = 0; i < attributes.length; i++) {
            attributeIndex.put(attributes[i], i);
        }

        int numSongs = Integer.parseInt(READER.readLine());
        String[][] songs = new String[numSongs][attributes.length];
        for (int i = 0; i < numSongs; i++) {
            songs[i] = READER.readLine().split(" ");
        }

        int numSorts = Integer.parseInt(READER.readLine());
        for (int i = 0; i < numSorts; i++) {
            int sort = attributeIndex.get(READER.readLine());
            Arrays.sort(songs, (a, b) -> a[sort].compareTo(b[sort]));

            outputSongs(songs, attributes);
        }

        WRITER.close();
    }

    private static void outputSongs(String[][] songs, String[] attributes) throws IOException {
        if (isFirstOutput) {
            isFirstOutput = false;
        } else {
            WRITER.newLine();
        }

        for (int i = 0; i < attributes.length; i++) {
            if (i != 0) {
                WRITER.write(" ");
            }
            WRITER.write(attributes[i]);
        }

        WRITER.newLine();

        for (int songNum = 0; songNum < songs.length; songNum++) {
            for (int attributeNum = 0; attributeNum < songs[0].length; attributeNum++) {
                if (attributeNum != 0) {
                    WRITER.write(" ");
                }
                WRITER.write(songs[songNum][attributeNum]);
            }
            WRITER.newLine();
        }
    }
}
