import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numOwners;
        int teamSize;
        {
            String[] info = READER.readLine().split(" ");
            numOwners = Integer.parseInt(info[0]);
            teamSize = Integer.parseInt(info[1]);
        }
        ArrayList<ArrayDeque<String>> ownerPreferences = readOwnerPreferences(numOwners);
        ArrayDeque<String> players = readPlayers();

        ArrayList<ArrayList<String>> teams = new ArrayList<>();
        for (int i = 0; i < numOwners; i++) {
            teams.add(new ArrayList<String>(teamSize));
        }
        HashSet<String> chosenPlayers = new HashSet<>(numOwners * teamSize);
        while (teams.get(numOwners - 1).size() < teamSize) {
            for (int teamNumber = 0; teamNumber < numOwners; teamNumber++) {
                String nextPlayer = getNextPlayer(players, ownerPreferences.get(teamNumber), chosenPlayers);
                chosenPlayers.add(nextPlayer);
                teams.get(teamNumber).add(nextPlayer);
            }
        }

        for (ArrayList<String> team : teams) {
            for (int i = 0; i < team.size(); i++) {
                if (i != 0) {
                    WRITER.write(' ');
                }

                WRITER.write(team.get(i));
            }

            WRITER.newLine();
        }

        WRITER.close();
    }

    private static String getNextPlayer(ArrayDeque<String> players, ArrayDeque<String> preferences,
            HashSet<String> chosenPlayers) {
        while (true) {
            String possiblePlayer = preferences.pollFirst();
            if (possiblePlayer == null) {
                possiblePlayer = players.pollFirst();
            }

            if (chosenPlayers.contains(possiblePlayer)) {
                continue;
            }

            return possiblePlayer;
        }
    }

    private static ArrayDeque<String> readPlayers() throws IOException {
        int numPlayers = Integer.parseInt(READER.readLine());
        ArrayDeque<String> players = new ArrayDeque<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(READER.readLine());
        }

        return players;
    }

    private static ArrayList<ArrayDeque<String>> readOwnerPreferences(int numOwners) throws IOException {
        ArrayList<ArrayDeque<String>> ownerPreferences = new ArrayList<>(numOwners);
        for (; numOwners > 0; numOwners--) {
            String[] info = READER.readLine().split(" ");
            ArrayDeque<String> currentPreferences = new ArrayDeque<String>();

            for (int i = 1; i < info.length; i++) {
                currentPreferences.addLast(info[i]);
            }

            ownerPreferences.add(currentPreferences);
        }

        return ownerPreferences;
    }
}
