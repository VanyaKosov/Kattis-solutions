import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Position[] positions = readInput();

        for (Position pos : positions) {
            if (!((pos.endCol + pos.endRow + pos.startCol + pos.startRow) % 2 == 0)) {
                writer.write("Impossible");
                writer.newLine();
                continue;
            }

            if (pos.startCol == pos.endCol && pos.startRow == pos.endRow) {
                writer.write("0 " + translatePosToLetters(pos.endRow, pos.endCol));
                writer.newLine();
                continue;
            }

            if (theSameParallel(pos.startRow, pos.startCol, pos.endRow, pos.endCol)) {
                writer.write("1 " + translatePosToLetters(pos.startRow, pos.startCol) +
                        " " + translatePosToLetters(pos.endRow, pos.endCol));
                writer.newLine();
                continue;
            }

            int[] firstMove = findFirstMove(pos.startRow, pos.startCol, pos.endRow, pos.endCol);
            writer.write("2 " + translatePosToLetters(pos.startRow, pos.startCol) +
                    " " + translatePosToLetters(firstMove[0], firstMove[1]) +
                    " " + translatePosToLetters(pos.endRow, pos.endCol));
            writer.newLine();
        }

        writer.close();
    }

    private static int[] findFirstMove(int startRow, int startCol, int endRow, int endCol) {
        int rowOffset = 0;
        int colOffset = 0;
        while (startRow + rowOffset < 8 && startCol + colOffset < 8) {
            if (theSameParallel(startRow + rowOffset, startCol + colOffset, endRow, endCol)) {
                return new int[] { startRow + rowOffset, startCol + colOffset };
            }
            rowOffset++;
            colOffset++;
        }

        rowOffset = 0;
        colOffset = 0;
        while (startRow + rowOffset >= 0 && startCol + colOffset >= 0) {
            if (theSameParallel(startRow + rowOffset, startCol + colOffset, endRow, endCol)) {
                return new int[] { startRow + rowOffset, startCol + colOffset };
            }
            rowOffset--;
            colOffset--;
        }

        rowOffset = 0;
        colOffset = 0;
        while (startRow + rowOffset < 8 && startCol + colOffset >= 0) {
            if (theSameParallel(startRow + rowOffset, startCol + colOffset, endRow, endCol)) {
                return new int[] { startRow + rowOffset, startCol + colOffset };
            }
            rowOffset++;
            colOffset--;
        }

        rowOffset = 0;
        colOffset = 0;
        while (startRow + rowOffset >= 0 && startCol + colOffset < 8) {
            if (theSameParallel(startRow + rowOffset, startCol + colOffset, endRow, endCol)) {
                return new int[] { startRow + rowOffset, startCol + colOffset };
            }
            rowOffset--;
            colOffset++;
        }

        throw new IllegalStateException("Couldn't find correct cell");
    }

    private static boolean theSameParallel(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) == Math.abs(col1 - col2);
    }

    private static String translatePosToLetters(int row, int col) {
        return String.valueOf((char) (col + 'A')) + " " + (row + 1);
    }

    private static Position[] readInput() throws IOException {
        int testCount = Integer.parseInt(reader.readLine());
        Position[] positions = new Position[testCount];

        for (int i = 0; i < testCount; i++) {
            String[] cords = reader.readLine().split(" ");
            positions[i] = new Position(
                    Integer.parseInt(cords[1]) - 1, cords[0].charAt(0) - 'A',
                    Integer.parseInt(cords[3]) - 1, cords[2].charAt(0) - 'A');
        }

        return positions;
    }

    private static class Position {
        int startRow;
        int startCol;
        int endRow;
        int endCol;

        public Position(int startRow, int startCol, int endRow, int endCol) {
            this.startRow = startRow;
            this.startCol = startCol;
            this.endRow = endRow;
            this.endCol = endCol;
        }
    }
}
