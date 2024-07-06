import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        char[][] map = new char[8][8];
        for (int row = 0; row < 8; row++) {
            String line = reader.readLine();
            for (int col = 0; col < 8; col++) {
                map[row][col] = line.charAt(col);
                if (map[row][col] == 'T') {
                    map[row][col] = '.';
                }
            }
        }
        String program = reader.readLine();

        Rotation rotation = getRotationList();
        Pos turtlePos = new Pos(7, 0);
        for (int i = 0; i < program.length(); i++) {
            char command = program.charAt(i);

            if (command == 'F') {
                Pos newPos = turtlePos.add(rotation.offset);
                if (newPos.row < 0 || newPos.row > 7 || newPos.col < 0 || newPos.col > 7 ||
                        (map[newPos.row][newPos.col] != '.' && map[newPos.row][newPos.col] != 'D')) {
                    writer.write("Bug!");
                    writer.close();
                    return;
                }
                turtlePos = newPos;
                continue;
            }

            if (command == 'L') {
                rotation = rotation.left;
                continue;
            }

            if (command == 'R') {
                rotation = rotation.right;
                continue;
            }

            if (command == 'X') {
                Pos firePos = turtlePos.add(rotation.offset);
                if (firePos.row < 0 || firePos.row > 7 || firePos.col < 0 || firePos.col > 7 ||
                        map[firePos.row][firePos.col] != 'I') {
                    writer.write("Bug!");
                    writer.close();
                    return;
                }
                map[firePos.row][firePos.col] = '.';
                continue;
            }
        }

        if (map[turtlePos.row][turtlePos.col] == 'D') {
            writer.write("Diamond!");
        } else {
            writer.write("Bug!");
        }

        writer.close();
    }

    public static Rotation getRotationList() {
        Rotation right = new Rotation(new Pos(0, 1));
        Rotation down = new Rotation(new Pos(1, 0));
        Rotation left = new Rotation(new Pos(0, -1));
        Rotation up = new Rotation(new Pos(-1, 0));
        right.left = up;
        right.right = down;
        down.left = right;
        down.right = left;
        left.left = down;
        left.right = up;
        up.left = left;
        up.right = right;

        return right;
    }

    private static class Rotation {
        public final Pos offset;
        public Rotation left;
        public Rotation right;

        public Rotation(Pos o) {
            offset = o;
        }
    }

    private static class Pos {
        public final int row;
        public final int col;

        public Pos(int r, int c) {
            row = r;
            col = c;
        }

        public Pos add(Pos other) {
            return new Pos(row + other.row, col + other.col);
        }
    }
}
