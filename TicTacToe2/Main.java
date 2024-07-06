import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numGames = Integer.parseInt(reader.readLine());
        for (int i = 0; i < numGames; i++) {
            char[][] map = new char[3][3];
            int xCount = 0;
            int oCount = 0;
            for (int row = 0; row < 3; row++) {
                String line = reader.readLine();
                for (int col = 0; col < 3; col++) {
                    char symbol = line.charAt(col);
                    map[row][col] = symbol;

                    if (symbol == 'X') {
                        xCount++;
                    } else if (symbol == 'O') {
                        oCount++;
                    }
                }
            }

            int xLineCount = checkLines(map, 'X');
            int oLineCount = checkLines(map, 'O');

            if (i != 0) {
                writer.newLine();
            }

            String answer = "no";
            if (oCount == xCount && xLineCount == 0 && oLineCount <= 1) {
                answer = "yes";
            } else if (oCount == xCount - 1 && oLineCount == 0 && xLineCount <= 2) {
                answer = "yes";
            }

            writer.write(answer);

            if (i < numGames - 1) {
                reader.readLine();
            }
        }

        writer.close();
    }

    private static int checkLines(char[][] map, char s) {
        int lineCount = 0;
        if (map[0][0] == s && map[0][1] == s && map[0][2] == s) {
            lineCount++;
        }
        if (map[1][0] == s && map[1][1] == s && map[1][2] == s) {
            lineCount++;
        }
        if (map[2][0] == s && map[2][1] == s && map[2][2] == s) {
            lineCount++;
        }

        if (map[0][0] == s && map[1][0] == s && map[2][0] == s) {
            lineCount++;
        }
        if (map[0][1] == s && map[1][1] == s && map[2][1] == s) {
            lineCount++;
        }
        if (map[0][2] == s && map[1][2] == s && map[2][2] == s) {
            lineCount++;
        }

        if (map[0][0] == s && map[1][1] == s && map[2][2] == s) {
            lineCount++;
        }
        if (map[2][0] == s && map[1][1] == s && map[0][2] == s) {
            lineCount++;
        }

        return lineCount;
    }
}
