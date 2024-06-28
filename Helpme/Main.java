import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Input input = readInput();
        ArrayList<Piece> whitePieces = input.whitePieces;
        ArrayList<Piece> blackPieces = input.blackPieces;
        Collections.sort(whitePieces);
        Collections.sort(blackPieces);

        printPieces(whitePieces, "White");
        writer.newLine();
        printPieces(blackPieces, "Black");

        writer.close();
    }

    private static void printPieces(ArrayList<Piece> pieces, String color) throws IOException {
        writer.write(color + ": ");

        for (int i = 0; i < pieces.size(); i++) {
            if (i != 0) {
                writer.write(",");
            }
            writer.write(pieces.get(i).toString());
        }
    }

    private static Input readInput() throws IOException {
        ArrayList<Piece> whitePieces = new ArrayList<>();
        ArrayList<Piece> blackPieces = new ArrayList<>();
        Input input = new Input(whitePieces, blackPieces);

        for (int row = 7; row >= 0; row--) {
            reader.readLine();
            String line = reader.readLine();
            for (int col = 0; col < 8; col++) {
                char symbol = line.charAt(col * 4 + 2);
                if (symbol == '.' || symbol == ':') {
                    continue;
                }

                if (Character.isUpperCase(symbol)) {
                    whitePieces.add(new Piece(symbol, row, col));
                } else {
                    blackPieces.add(new Piece(symbol, row, col));
                }
            }
        }

        reader.readLine();

        return input;
    }

    private static class Input {
        ArrayList<Piece> whitePieces;
        ArrayList<Piece> blackPieces;

        public Input(ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces) {
            this.whitePieces = whitePieces;
            this.blackPieces = blackPieces;
        }
    }

    private static class Piece implements Comparable<Piece> {
        private static final HashMap<Character, Integer> order = new HashMap<>() {
            {
                put('K', 0);
                put('Q', 1);
                put('R', 2);
                put('B', 3);
                put('N', 4);
                put('P', 5);
            }
        };

        public boolean isWhite;
        public char symbol;
        public int row;
        public int col;

        public Piece(char symbol, int row, int col) {
            this.isWhite = Character.isUpperCase(symbol);
            this.symbol = Character.toUpperCase(symbol);
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Piece other) {
            if (order.get(symbol) < order.get(other.symbol)) {
                return -1;
            }

            if (order.get(symbol) > order.get(other.symbol)) {
                return 1;
            }

            if ((isWhite && row < other.row) || (!isWhite && row > other.row)) {
                return -1;
            }

            if ((isWhite && row > other.row) || (!isWhite && row < other.row)) {
                return 1;
            }

            if (col < other.col) {
                return -1;
            }

            if (col > other.col) {
                return 1;
            }

            return 0;
        }

        @Override
        public String toString() {
            return (symbol == 'P' ? "" : symbol) + Character.toString('a' + col) + (row + 1);
        }
    }
}
