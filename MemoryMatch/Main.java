import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] cards = getInput();
        HashSet<String> cardPairs = new HashSet<>();
        int counter = 0;

        for (String card : cards) {
            if (card == "" || card == null) {
                continue;
            }

            if (cardPairs.contains(card)) {
                counter++;
                cardPairs.remove(card);

                continue;
            }

            cardPairs.add(card);
        }

        counter += cardPairs.size();
        writer.write(Integer.toString(counter));

        writer.close();
    }

    private static String[] getInput() throws IOException {
        int length = Integer.parseInt(reader.readLine());
        String[] cards = new String[length];
        int playedGames = Integer.parseInt(reader.readLine());

        for (int i = 0; i < playedGames; i++) {
            String line = reader.readLine();
            String[] parts = line.split(" ");
            int firstPos = Integer.valueOf(parts[0]) - 1;
            int secondPos = Integer.valueOf(parts[1]) - 1;
            String firstCard = parts[2];
            String secondCard = parts[3];

            if (firstCard.equals(secondCard)) {
                cards[firstPos] = "";
                cards[secondPos] = "";

                continue;
            }

            cards[firstPos] = firstCard;
            cards[secondPos] = secondCard;
        }

        return cards;
    }
}
