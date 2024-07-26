import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int[][] map = getStartingMap(); // Size may be zero? Or there could be no tree part?
        int ringCount = calculateRings(map);

        print(map, ringCount);
        WRITER.close();
    }

    private static void print(int[][] map, int ringCount) throws IOException {
        for (int row = 1; row < map.length - 1; row++) {
            for (int col = 1; col < map[0].length - 1; col++) {
                int cell = map[row][col];
                if (ringCount < 10) {
                    if (cell == 0) {
                        WRITER.write("..");
                    } else {
                        WRITER.write("." + Integer.toString(cell));
                    }
                } else {
                    if (cell == 0) {
                        WRITER.write("...");
                    } else {
                        if (cell < 10) {
                            WRITER.write(".." + cell);
                        } else {
                            WRITER.write("." + cell);
                        }
                    }
                }
            }
            WRITER.newLine();
        }
    }

    private static int calculateRings(int[][] map) {
        int currentRing = 0;
        boolean addedRing = true;
        while (addedRing) {
            addedRing = false;
            currentRing++;

            for (int row = 1; row < map.length - 1; row++) {
                for (int col = 1; col < map[0].length - 1; col++) {
                    if (map[row][col] == -1
                            && (map[row - 1][col] == currentRing - 1 || map[row + 1][col] == currentRing - 1 ||
                                    map[row][col - 1] == currentRing - 1 || map[row][col + 1] == currentRing - 1)) {
                        map[row][col] = currentRing;
                        addedRing = true;
                    }
                }
            }
        }

        return currentRing;
    }

    private static int[][] getStartingMap() throws IOException {
        int[] sizeInfo = Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
        int height = sizeInfo[0];
        int width = sizeInfo[1];
        int[][] map = new int[height + 2][width + 2]; // Add an empty border
        for (int row = 0; row < height; row++) {
            String line = READER.readLine();
            for (int col = 0; col < width; col++) {
                char letter = line.charAt(col);
                if (letter == '.') {
                    map[row + 1][col + 1] = 0;
                    continue;
                }
                map[row + 1][col + 1] = -1;
            }
        }

        return map;
    }
}
