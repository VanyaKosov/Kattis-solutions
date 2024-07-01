import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final HashMap<Character, Character> transitions = new HashMap<>() {
        {
            put('9', 'a');
            put('z', 'A');
        }
    };

    public static void main(String[] args) throws IOException {
        char[][] map;
        while ((map = getNextMap()) != null) {
            HashMap<Character, Pos> dots = getDots(map);
            char current = '0';
            Pos currentPos = dots.get(current);
            char next = (char) (current + 1);
            Pos nextPos;
            while ((nextPos = dots.get(next)) != null) {
                connectDots(map, currentPos, nextPos);

                current = next;
                currentPos = nextPos;
                Character transition = transitions.get(next);
                if (transition != null) {
                    next = transition;
                    nextPos = dots.get(next);
                    continue;
                }
                next++;
                nextPos = dots.get(next);

                /*Character transition = transitions.get(next); // Rewrite from here
                if (transition == null || transitions.get(current) != null) {
                    next++;
                } else {
                    next = transition;
                }
                current++;
                currentPos = nextPos; // To here*/
            }

            printMap(map);
        }

        writer.close();
    }

    private static void printMap(char[][] map) throws IOException {
        for (char[] line : map) {
            for (char letter : line) {
                writer.write(Character.toString(letter));
            }
            writer.newLine();
        }

        writer.newLine();
    }

    private static void connectDots(char[][] map, Pos p1, Pos p2) {
        HashMap<Character, Character> otherSymbol = new HashMap<>() {
            {
                put('|', '-');
                put('-', '|');
            }
        };
        Pos offset = p2.sub(p1).norm();
        char symbol = offset.row == 0 ? '-' : '|';
        Pos current = p1.add(offset);
        while (!current.equals(p2)) {
            char mapSymbol = map[current.row][current.col];
            if (mapSymbol == '.') {
                map[current.row][current.col] = symbol;
            } else if (mapSymbol == otherSymbol.get(symbol)) {
                map[current.row][current.col] = '+';
            }

            current = current.add(offset);
        }
    }

    private static HashMap<Character, Pos> getDots(char[][] map) {
        HashMap<Character, Pos> dots = new HashMap<>();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] != '.') {
                    dots.put(map[row][col], new Pos(row, col));
                }
            }
        }

        return dots;
    }

    private static char[][] getNextMap() throws IOException {
        ArrayList<String> roughMap = new ArrayList<>();

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                if (roughMap.size() > 0) {
                    break;
                }

                return null;
            }

            if (line.equals("")) {
                break;
            }

            roughMap.add(line);
        }

        char[][] map = new char[roughMap.size()][roughMap.get(0).length()];
        for (int row = 0; row < roughMap.size(); row++) {
            for (int col = 0; col < roughMap.get(0).length(); col++) {
                map[row][col] = roughMap.get(row).charAt(col);
            }
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

        public Pos sub(Pos other) {
            return new Pos(row - other.row, col - other.col);
        }

        public Pos norm() {
            int length = Math.abs(row) + Math.abs(col);
            return new Pos(row / length, col / length);
        }

        public boolean equals(Pos other) {
            return row == other.row && col == other.col;
        }
    }
}
