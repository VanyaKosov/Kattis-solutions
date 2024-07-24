import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final Integer[][] RIGHT_ROTATION = {
            { 0, -1 },
            { 1, 0 }
    };
    private static final Integer[][] LEFT_ROTATION = {
            { 0, 1 },
            { -1, 0 }
    };
    private static final Integer[][] OPPOSITE_ROTATION = {
            { -1, 0 },
            { 0, -1 }
    };
    private static final Integer[][] NO_ROTATION = {
            { 1, 0 },
            { 0, 1 }
    };
    private static final HashMap<Integer, Integer[][]> ROTATIONS = new HashMap<>() {
        {
            put(0, LEFT_ROTATION);
            put(1, OPPOSITE_ROTATION);
            put(2, RIGHT_ROTATION);
            put(3, NO_ROTATION);
        }
    };
    private static final HashMap<Integer, Integer[][]> REVERSE_ROTATIONS = new HashMap<>() {
        {
            put(0, RIGHT_ROTATION);
            put(1, OPPOSITE_ROTATION);
            put(2, LEFT_ROTATION);
            put(3, NO_ROTATION);
        }
    };

    public static void main(String[] args) throws IOException {
        int[][] field = getInputField();
        int move = Integer.parseInt(READER.readLine());
        Integer[][] rotation = ROTATIONS.get(move);
        Integer[][] reverseRotation = REVERSE_ROTATIONS.get(move);
        field = rotate(field, rotation);
        combine(field);
        field = rotate(field, reverseRotation);

        output(field);
        WRITER.close();
    }

    private static void output(int[][] field) throws IOException {
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[0].length; col++) {
                WRITER.write(Integer.toString(field[row][col]) + " ");
            }
            WRITER.newLine();
        }
    }

    // Remember: one number can't combine twice in one swipe
    private static void combine(int[][] field) {
        boolean[][] combined = new boolean[field.length][field[0].length];

        for (int i = 0; i < 3; i++) {
            for (int row = field.length - 2; row >= 0; row--) { // Start from second last line
                for (int col = 0; col < field[0].length; col++) {
                    int currentNum = field[row][col];
                    int lowerNum = field[row + 1][col];
                    if (lowerNum == 0) {
                        field[row + 1][col] = currentNum;
                        field[row][col] = 0;

                        continue;
                    }

                    if (lowerNum == currentNum && !combined[row + 1][col] && !combined[row][col]) {
                        combined[row + 1][col] = true;
                        field[row + 1][col] = lowerNum + currentNum;
                        field[row][col] = 0;
                    }
                }
            }
        }
    }

    private static int[][] rotate(int[][] field, Integer[][] rotation) {
        int[][] result = new int[field.length][field[0].length];
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[0].length; col++) {
                int newCol = (int) (((col - 1.5) * rotation[0][0] + (row - 1.5) * rotation[0][1]) + 1.5);
                int newRow = (int) (((col - 1.5) * rotation[1][0] + (row - 1.5) * rotation[1][1]) + 1.5);
                result[newRow][newCol] = field[row][col];
            }
        }

        return result;
    }

    private static int[][] getInputField() throws IOException {
        int[][] field = {
                readLine(),
                readLine(),
                readLine(),
                readLine()
        };

        return field;
    }

    private static int[] readLine() throws IOException {
        return Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
    }
}
