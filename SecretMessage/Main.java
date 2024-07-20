import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numTests = Integer.parseInt(reader.readLine());
        for (; numTests > 0; numTests--) {
            String message = reader.readLine();
            int sideLength = closestSquare(message.length());
            message = addStars(message, sideLength * sideLength);
            String[] textBoard = getTextBoard(message, sideLength);

            for (int col = 0; col < textBoard[0].length(); col++) {
                for (int row = textBoard.length - 1; row >= 0; row--) {
                    char letter = textBoard[row].charAt(col);
                    if (letter == '*') {
                        continue;
                    }
                    writer.write(letter);
                }
            }

            writer.newLine();
        }

        writer.close();
    }

    private static String[] getTextBoard(String text, int sideLength) {
        String[] textBoard = new String[sideLength];
        for (int i = 0; i < sideLength; i++) {
            textBoard[i] = text.substring(0, sideLength);
            text = text.substring(sideLength);
        }

        return textBoard;
    }

    private static String addStars(String text, int finalLength) {
        for (int i = 0; i < finalLength - text.length(); i++) {
            text = text + "*";
        }

        return text;
    }

    private static int closestSquare(int number) {
        int squareRoot = Double.valueOf(Math.sqrt(number)).intValue();
        if (squareRoot * squareRoot < number) {
            return squareRoot + 1;
        }
        return squareRoot;
    }
}
