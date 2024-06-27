import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] white = reader.readLine().substring(7).split(",");
        String[] black = reader.readLine().substring(7).toLowerCase().split(",");
        char[][] board = new char[][] {
                { ':', '.', ':', '.', ':', '.', ':', '.' },
                { '.', ':', '.', ':', '.', ':', '.', ':' },
                { ':', '.', ':', '.', ':', '.', ':', '.' },
                { '.', ':', '.', ':', '.', ':', '.', ':' },
                { ':', '.', ':', '.', ':', '.', ':', '.' },
                { '.', ':', '.', ':', '.', ':', '.', ':' },
                { ':', '.', ':', '.', ':', '.', ':', '.' },
                { '.', ':', '.', ':', '.', ':', '.', ':' }
        };

        addPiecesToBoard(white, board, true);
        addPiecesToBoard(black, board, false);

        writer.write("+---+---+---+---+---+---+---+---+");
        writer.newLine();
        for (int row = 7; row >= 0; row--) {
            writer.write("|");
            for (int col = 0; col < 8; col++) {
                String color = ":";
                if ((row + col) % 2 != 0) {
                    color = ".";
                }
                writer.write(color + board[row][col] + color + "|");
            }
            writer.newLine();
            writer.write("+---+---+---+---+---+---+---+---+");
            writer.newLine();
        }

        writer.close();
    }

    private static void addPiecesToBoard(String[] pieces, char[][] board, boolean isWhite) {
        if (pieces[0] == "") {
            return;
        }

        for (String piece : pieces) {
            char symbol = isWhite ? 'P' : 'p';
            int i = 0;
            if (piece.length() > 2) {
                symbol = piece.charAt(i);
                i++;
            }
            int col = piece.charAt(i) - 'a';
            i++;
            int row = piece.charAt(i) - '0' - 1;
            board[row][col] = symbol;
        }
    }
}
