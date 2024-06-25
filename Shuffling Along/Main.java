import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String input = reader.readLine();
        int spaceIndex = input.indexOf(' ');
        int cardCount = Integer.parseInt(input.substring(0, spaceIndex));
        boolean in = input.substring(spaceIndex + 1).equals("in");

        int result = in ? inShuffle(cardCount) : outShuffle(cardCount);
        writer.write(Integer.toString(result));

        writer.close();
    }

    private static int inShuffle(int cardCount) {
        int counter = 0;
        int[] cards = getCards(cardCount);
        do {
            int[] leftPart = new int[cardCount / 2 + 1];
            int[] rightPart = new int[cardCount / 2 + 1];
            int leftIndexCount = 0;
            int rightIndexCount = 0;

            for (int i = 0; i < cards.length; i++) {
                if (i < cardCount / 2) {
                    leftPart[leftIndexCount] = cards[i];
                    leftIndexCount++;
                } else {
                    rightPart[rightIndexCount] = cards[i];
                    rightIndexCount++;
                }
            }

            for (int i = 0; i < cards.length; i++) {
                if (i % 2 == 0) {
                    cards[i] = rightPart[i / 2];
                } else {
                    cards[i] = leftPart[i / 2];
                }
            }

            counter++;
        } while (!inOrder(cards));

        return counter;
    }

    private static int outShuffle(int cardCount) {
        int counter = 0;
        int[] cards = getCards(cardCount);
        do {
            int[] leftPart = new int[cardCount / 2 + 1];
            int[] rightPart = new int[cardCount / 2 + 1];
            int leftIndexCount = 0;
            int rightIndexCount = 0;

            for (int i = 0; i < cards.length; i++) {
                if (i < cardCount / 2 || (cardCount % 2 != 0 && i == cardCount / 2)) {
                    leftPart[leftIndexCount] = cards[i];
                    leftIndexCount++;
                } else {
                    rightPart[rightIndexCount] = cards[i];
                    rightIndexCount++;
                }
            }

            for (int i = 0; i < cards.length; i++) {
                if (i % 2 == 0) {
                    cards[i] = leftPart[i / 2];
                } else {
                    cards[i] = rightPart[i / 2];
                }
            }

            counter++;
        } while (!inOrder(cards));

        return counter;
    }

    private static boolean inOrder(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != i) {
                return false;
            }
        }

        return true;
    }

    private static int[] getCards(int cardCount) {
        int[] cards = new int[cardCount];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = i;
        }

        return cards;
    }
}
