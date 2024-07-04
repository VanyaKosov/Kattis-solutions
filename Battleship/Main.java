import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Game[] games = getGames();
        for (int i = 0; i < games.length; i = i + 2) {
            Game game1 = games[i];
            Game game2 = games[i + 1];

            int playerTurn = 1;
            for (Pos shot : game1.shots) { //The shots are the same in game1 and game2
                if (playerTurn == 1) {
                    if (shoot(shot, game2) && game2.numShips > 0) {
                        continue;
                    }
                    playerTurn = 2;

                    continue;
                }

                if (shoot(shot, game1) && game1.numShips > 0) {
                    continue;
                }
                playerTurn = 1;

                if (game1.numShips == 0 || game2.numShips == 0) {
                    break;
                }
            }

            if ((game1.numShips == 0 && game2.numShips == 0) || (game1.numShips > 0 && game2.numShips > 0)) {
                writer.write("draw");
            } else if (game2.numShips > 0) {
                writer.write("player two wins");
            } else {
                writer.write("player one wins");
            }
            writer.newLine();
        }

        writer.close();
    }

    private static boolean shoot(Pos shotPos, Game game) {
        char result = game.map[shotPos.row][shotPos.col];
        game.map[shotPos.row][shotPos.col] = '_';

        if (result == '#') {
            game.numShips--;
            return true;
        }

        return false;
    }

    private static Game[] getGames() throws IOException {
        int numGames = Integer.parseInt(reader.readLine());
        Game[] games = new Game[numGames * 2];

        for (int i = 0; i < games.length; i = i + 2) {
            String[] params = reader.readLine().split(" ");
            int height = Integer.parseInt(params[1]);
            int numShots = Integer.parseInt(params[2]);
            Pos[] shots = new Pos[numShots];
            String[] map1 = new String[height];
            String[] map2 = new String[height];

            for (int j = 0; j < height; j++) {
                map1[j] = reader.readLine();
            }

            for (int j = 0; j < height; j++) {
                map2[j] = reader.readLine();
            }

            for (int j = 0; j < numShots; j++) {
                String[] roughShot = reader.readLine().split(" ");
                shots[j] = new Pos(Integer.parseInt(roughShot[1]), Integer.parseInt(roughShot[0])); //Reversed because x, y -> row, col
            }

            games[i] = new Game(shots, map1);
            games[i + 1] = new Game(shots, map2);
        }

        return games;
    }

    private static class Game {
        public final char[][] map;
        public final Pos[] shots;
        public int numShips;

        public Game(Pos[] shots, String[] roughMap) {
            this.shots = shots;
            map = translateMap(roughMap);
        }

        private char[][] translateMap(String[] roughMap) {
            char[][] map = new char[roughMap.length][roughMap[0].length()];
            int shipCounter = 0;

            for (int row = 0; row < roughMap.length; row++) {
                for (int col = 0; col < roughMap[0].length(); col++) {
                    map[row][col] = roughMap[roughMap.length - 1 - row].charAt(col);
                    if (map[row][col] == '#') {
                        shipCounter++;
                    }
                }
            }

            numShips = shipCounter;

            return map;
        }
    }

    private static class Pos {
        public final int row;
        public final int col;

        public Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
