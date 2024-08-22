import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        var words = readWords();
        var compoundWords = new HashSet<String>(words.size() * words.size());
        var answers = new ArrayList<String>(words.size() * words.size());

        for (int i = 0; i < words.size(); i++) {
            String word1 = words.get(i);
            for (int j = i + 1; j < words.size(); j++) {
                String word2 = words.get(j);
                String result1 = word1 + word2;
                String result2 = word2 + word1;
                if (!compoundWords.contains(result1)) {
                    compoundWords.add(result1);
                    answers.add(result1);
                }
                if (!compoundWords.contains(result2)) {
                    compoundWords.add(result2);
                    answers.add(result2);
                }
            }
        }

        answers.sort((a, b) -> a.compareTo(b));

        for (String word : answers) {
            WRITER.write(word);
            WRITER.newLine();
        }

        WRITER.close();
    }

    private static ArrayList<String> readWords() throws IOException {
        var words = new ArrayList<String>(100);
        String line = null;
        while ((line = READER.readLine()) != null) {
            Arrays.stream(line.split(" ")).forEach(a -> words.add(a));
        }

        return words;
    }
}
