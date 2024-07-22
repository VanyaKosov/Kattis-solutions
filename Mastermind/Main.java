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

    /*
        HashMap<Character, Integer> missedCodeLetters = new HashMap<>();
        HashMap<Character, Integer> missedGuessLetters = new HashMap<>();
    
        if (missedCodeLetters.containsKey(codeLetter)) {
            missedCodeLetters.put(codeLetter, missedCodeLetters.get(codeLetter) + 1);
        } else {
            missedCodeLetters.put(codeLetter, 1);
        }
    
        if (missedGuessLetters.containsKey(guessLetter)) {
            missedGuessLetters.put(guessLetter, missedGuessLetters.get(guessLetter) + 1);
        } else {
            missedGuessLetters.put(guessLetter, 1);
        }
    
        if (missedCodeLetters.containsKey(guessLetter)) {
            same++;
            missedCodeLetters.put(guessLetter, missedCodeLetters.get(guessLetter) - 1);
            if (missedCodeLetters.get(guessLetter) == 0) {
                missedCodeLetters.remove(guessLetter);
            }
    
            continue;
        }
    
        if (missedGuessLetters.containsKey(codeLetter)) {
            same++;
            missedGuessLetters.put(codeLetter, missedGuessLetters.get(codeLetter) - 1);
            if (missedGuessLetters.get(codeLetter) == 0) {
                missedGuessLetters.remove(codeLetter);
            }
        }
     */
}
