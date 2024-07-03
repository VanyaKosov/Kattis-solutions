import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final HashMap<String, String> winner = new HashMap<>() {
        {
            put("rock", "paper");
            put("paper", "scissors");
            put("scissors", "rock");
        }
    };

    public static void main(String[] args) throws IOException {
        boolean firstRound = true;

        while (true) {
            String[] info = reader.readLine().split(" ");
            if (info[0].equals("0")) {
                break;
            }
            int playerCount = Integer.parseInt(info[0]);
            int gameCount = Integer.parseInt(info[1]);
            gameCount = gameCount * playerCount * (playerCount - 1) / 2;
            Player[] players = getPlayers(playerCount);

            for (int i = 0; i < gameCount; i++) {
                String[] game = reader.readLine().split(" ");
                int firstPlayer = Integer.parseInt(game[0]);
                int secondPlayer = Integer.parseInt(game[2]);

                if (game[1].equals(game[3])) {
                    continue;
                }

                if (winner.get(game[1]).equals(game[3])) {
                    players[firstPlayer].lost();
                    players[secondPlayer].won();
                } else {
                    players[firstPlayer].won();
                    players[secondPlayer].lost();
                }
            }

            if (!firstRound) {
                writer.newLine();
                writer.newLine();
            }
            firstRound = false;
            printResults(players);
        }

        writer.close();
    }

    private static void printResults(Player[] players) throws IOException {
        for (int i = 1; i < players.length; i++) {
            if (i > 1) {
                writer.newLine();
            }
            writer.write(players[i].toString());
        }
    }

    private static Player[] getPlayers(int playerCount) {
        Player[] players = new Player[playerCount + 1];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }

        return players;
    }

    private static class Player {
        private Integer wins;
        private Integer losses;

        public Player() {
            wins = null;
            losses = null;
        }

        public void won() {
            if (wins == null) {
                wins = 1;
                losses = 0;
                return;
            }
            wins++;
        }

        public void lost() {
            if (losses == null) {
                wins = 0;
                losses = 1;
                return;
            }
            losses++;
        }

        @Override
        public String toString() {
            if (wins == null || losses == null) {
                return "-";
            }

            return String.format("%.3f", (double) wins / (wins + losses));
        }
    }
}
