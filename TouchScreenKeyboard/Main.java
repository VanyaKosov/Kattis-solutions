import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        HashMap<Character, Pos> keys = getKeys();
        int numTests = Integer.parseInt(reader.readLine());
        for (; numTests > 0; numTests--) {
            String[] info = reader.readLine().split(" ");
            String typedWord = info[0];
            String[] dict = getDict(Integer.parseInt(info[1]));
            Word[] dictWords = new Word[dict.length];
            for (int i = 0; i < dict.length; i++) {
                String currentWord = dict[i];
                int sum = 0;
                for (int j = 0; j < typedWord.length(); j++) {
                    sum += keys.get(currentWord.charAt(j)).distance(keys.get(typedWord.charAt(j)));
                }
                dictWords[i] = new Word(dict[i], sum);
            }

            Arrays.sort(dictWords);
            for (int i = 0; i < dictWords.length; i++) {
                writer.write(dictWords[i].word + " " + Integer.toString(dictWords[i].distance));
                writer.newLine();
            }
        }

        writer.close();
    }

    private static String[] getDict(int length) throws IOException {
        String[] dict = new String[length];
        for (int i = 0; i < length; i++) {
            dict[i] = reader.readLine();
        }

        return dict;
    }

    private static HashMap<Character, Pos> getKeys() {
        HashMap<Character, Pos> keys = new HashMap<>();
        String[] keyBoard = {
                "qwertyuiop",
                "asdfghjkl ",
                "zxcvbnm   "
        };

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < keyBoard[0].length(); col++) {
                keys.put(keyBoard[row].charAt(col), new Pos(row, col));
            }
        }
        keys.remove(' ');

        return keys;
    }

    private static class Pos {
        public final int row;
        public final int col;

        public Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int distance(Pos other) {
            return Math.abs(other.row - row) + Math.abs(other.col - col);
        }
    }

    private static class Word implements Comparable<Word> {
        public final String word;
        public final int distance;

        public Word(String word, int distance) {
            this.word = word;
            this.distance = distance;
        }

        @Override
        public int compareTo(Word other) {
            if (distance == other.distance) {
                return word.compareTo(other.word);
            }

            return distance - other.distance;
        }
    }
}
