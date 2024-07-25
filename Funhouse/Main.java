import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int houseNumber = 1;
        char[][] map;
        while ((map = getNextMap()) != null) {
            Pos currentPos = findStartingPos(map);
            Pos offset = getStartingOffset(currentPos, map[0].length, map.length);
            currentPos = currentPos.add(offset);

            while (true) {
                char currentTile = map[currentPos.row][currentPos.col];
                if (currentTile == 'x') {
                    map[currentPos.row][currentPos.col] = '&';
                    break;
                }

                offset = getOffset(currentTile, offset);
                currentPos = currentPos.add(offset);
            }

            WRITER.write("HOUSE " + Integer.toString(houseNumber));
            WRITER.newLine();
            outputMap(map);
            houseNumber++;
        }

        WRITER.close();
    }

    private static void outputMap(char[][] map) throws IOException {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                WRITER.write(map[row][col]);
            }
            WRITER.newLine();
        }
    }

    private static Pos findStartingPos(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                if (map[row][col] == '*') {
                    return new Pos(row, col);
                }
            }
        }

        throw new IllegalStateException("Couldn't find entrance");
    }

    private static Pos getStartingOffset(Pos startingPos, int width, int height) {
        if (startingPos.row == 0) {
            return new Pos(1, 0);
        }
        if (startingPos.row == height - 1) {
            return new Pos(-1, 0);
        }
        if (startingPos.col == 0) {
            return new Pos(0, 1);
        }
        return new Pos(0, -1);
    }

    private static Pos getOffset(char currentTile, Pos offset) {
        if (currentTile != '/' && currentTile != '\\') {
            return offset;
        }

        if (currentTile == '/') {
            if (offset.equals(new Pos(0, 1))) {
                return new Pos(-1, 0);
            }
            if (offset.equals(new Pos(0, -1))) {
                return new Pos(1, 0);
            }
            if (offset.equals(new Pos(1, 0))) {
                return new Pos(0, -1);
            }
            return new Pos(0, 1);
        }
        if (offset.equals(new Pos(1, 0))) {
            return new Pos(0, 1);
        }
        if (offset.equals(new Pos(-1, 0))) {
            return new Pos(0, -1);
        }
        if (offset.equals(new Pos(0, 1))) {
            return new Pos(1, 0);
        }
        return new Pos(-1, 0);
    }

    private static char[][] getNextMap() throws IOException {
        int[] params = Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
        int width = params[0];
        int height = params[1];
        if (width == 0 && height == 0) {
            return null;
        }
        char[][] map = new char[height][width];

        for (int row = 0; row < height; row++) {
            map[row] = READER.readLine().toCharArray();
        }

        return map;
    }

    private static class Pos {
        public final int row;
        public final int col;

        public Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Pos add(Pos other) {
            return new Pos(row + other.row, col + other.col);
        }

        @Override
        public boolean equals(Object other) {
            if (other != null && (other instanceof Pos otherPos)) {
                return row == otherPos.row && col == otherPos.col;
            }
            return false;
        }
    }
}
