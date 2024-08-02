import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        ArrayDeque<Integer> cards = getCards();

        boolean changed = true;
        while (changed && !cards.isEmpty()) {
            changed = false;
            ArrayDeque<Integer> leftoverCards = new ArrayDeque<>();

            boolean deletedLastCard = false;
            int currentCard = cards.poll();
            while (!cards.isEmpty()) {
                int nextCard = cards.poll();
                if ((currentCard + nextCard) % 2 == 0) {
                    changed = true;
                    if (!cards.isEmpty()) {
                        currentCard = cards.poll();
                    } else {
                        deletedLastCard = true;
                    }
                } else {
                    leftoverCards.add(currentCard);
                    currentCard = nextCard;
                }
            }

            if (!deletedLastCard) {
                leftoverCards.add(currentCard);
            }
            cards = leftoverCards;
        }

        WRITER.write(Integer.toString(cards.size()));

        WRITER.close();
    }

    private static ArrayDeque<Integer> getCards() throws IOException {
        READER.readLine();
        ArrayDeque<Integer> cards = new ArrayDeque<>();
        Arrays.stream(READER.readLine().split(" "))
                .mapToInt(a -> Integer.parseInt(a))
                .forEach(a -> cards.add(a));

        return cards;
    }
}
