import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] info = reader.readLine().split(" ");
        int length = Integer.parseInt(info[0]);
        String code = info[1];
        String guess = info[2];

        int[] missedCodeLetters = new int[26];
        int[] missedGuessLetters = new int[26];

        int identical = 0;
        int same = 0;
        for (int i = 0; i < length; i++) {
            char codeLetter = code.charAt(i);
            char guessLetter = guess.charAt(i);

            if (codeLetter == guessLetter) {
                identical++;
                continue;
            }

            if (missedGuessLetters[codeLetter - 'A'] > 0) {
                same++;
                missedGuessLetters[codeLetter - 'A']--;
            } else {
                missedCodeLetters[codeLetter - 'A']++;
            }

            if (missedCodeLetters[guessLetter - 'A'] > 0) {
                same++;
                missedCodeLetters[guessLetter - 'A']--;
            } else {
                missedGuessLetters[guessLetter - 'A']++;
            }
        }

        writer.write(String.format("%d %d", identical, same));

        writer.close();
    }
}
