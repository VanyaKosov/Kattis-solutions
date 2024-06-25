import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static HashMap<Character, Integer> normal = new HashMap<>() {
        {
            put('A', 11);
            put('K', 4);
            put('Q', 3);
            put('J', 2);
            put('T', 10);
            put('9', 0);
            put('8', 0);
            put('7', 0);
        }
    };
    private static HashMap<Character, Integer> dominant = new HashMap<>() {
        {
            put('A', 11);
            put('K', 4);
            put('Q', 3);
            put('J', 20);
            put('T', 10);
            put('9', 14);
            put('8', 0);
            put('7', 0);
        }
    };

    public static void main(String[] args) throws IOException {
        String inputLine = reader.readLine();
        Integer cardCount = Integer.parseInt(inputLine.substring(0, inputLine.length() - 2)) * 4;
        char dominantCard = inputLine.charAt(inputLine.length() - 1);

        int answer = 0;
        for (int i = 0; i < cardCount; i++) {
            String card = reader.readLine();
            if (card.charAt(1) == dominantCard) {
                answer += dominant.get(card.charAt(0));
                continue;
            }
            answer += normal.get(card.charAt(0));
        }

        writer.write(Integer.toString(answer));
        writer.close();
    }
}
