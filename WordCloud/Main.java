import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int testCount = 1;
        Test test = null;
        while ((test = nextTest()) != null) {
            int maxWidth = test.maxWidth;
            Word[] words = test.words;
            ArrayList<Integer> lineHeights = new ArrayList<>();
            lineHeights.add(0, 0);
            int currentLine = 0;
            int currentWidth = 0;

            for (Word word : words) {
                if (currentWidth + word.width + 10 > maxWidth) {
                    currentLine++;
                    currentWidth = word.width;
                    lineHeights.add(currentLine, word.height);
                    continue;
                }

                currentWidth += word.width + (currentWidth == 0 ? 0 : 10);
                lineHeights.set(currentLine, Math.max(lineHeights.get(currentLine), word.height));
            }

            int totalHeight = 0;
            for (Integer height : lineHeights) {
                totalHeight += height;
            }

            if (testCount != 1) {
                writer.newLine();
            }
            writer.write("CLOUD " + Integer.toString(testCount) + ": " + Integer.toString(totalHeight));
            testCount++;
        }

        writer.close();
    }

    private static Test nextTest() throws IOException {
        String[] params = reader.readLine().split(" ");
        if (params[0].equals("0") && params[1].equals("0")) {
            return null;
        }

        int maxWidth = Integer.parseInt(params[0]);
        int wordCount = Integer.parseInt(params[1]);
        Word[] words = new Word[wordCount];
        int mostOccurrences = 0;

        for (int i = 0; i < wordCount; i++) {
            String[] info = reader.readLine().split(" ");
            int frequency = Integer.parseInt(info[1]);
            words[i] = new Word(info[0], frequency);
            mostOccurrences = Math.max(mostOccurrences, frequency);
        }

        return new Test(maxWidth, words, mostOccurrences);
    }

    private static class Test {
        public final int maxWidth;
        public final Word[] words;
        public final int mostOccurrences;

        public Test(int maxWidth, Word[] words, int mostOccurrences) {
            this.maxWidth = maxWidth;
            this.words = words;
            this.mostOccurrences = mostOccurrences;

            calculateHeights();
            calculateWidths();
        }

        private void calculateHeights() {
            for (Word word : words) {
                word.height = 8 + (int) Math.ceil((40.0 * (word.frequency - 4.0)) / (mostOccurrences - 4.0));
            }
        }

        private void calculateWidths() {
            for (Word word : words) {
                word.width = (int) Math.ceil((9.0 / 16.0) * word.word.length() * word.height);
            }
        }
    }

    private static class Word {
        public final String word;
        public final int frequency;
        public int width;
        public int height;

        public Word(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }
    }
}
