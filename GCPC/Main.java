import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] startingInfo = READER.readLine().split(" ");
        int teamCount = Integer.parseInt(startingInfo[0]);
        int eventCount = Integer.parseInt(startingInfo[1]);
        Team[] teams = new Team[teamCount + 1];
        TreeSet<Team> sortedTeams = new TreeSet<>((a, b) -> {
            if (a.solvedProblems == b.solvedProblems) {
                return a.penalty - b.penalty;
            }

            return b.solvedProblems - a.solvedProblems;
        });

        for (; eventCount > 0; eventCount--) {
            String[] info = READER.readLine().split(" ");
            int teamNum = Integer.parseInt(info[0]);
            int penalty = Integer.parseInt(info[1]);

            if (teams[teamNum] == null) {
                Team team = new Team(teamNum, 1, penalty);
                teams[teamNum] = team;
                sortedTeams.add(team);
                displayRank(teams, sortedTeams);
                continue;
            }

            Team team = teams[teamNum];
            sortedTeams.remove(team);
            team.solvedProblems++;
            team.penalty += penalty;
            sortedTeams.add(team);
            displayRank(teams, sortedTeams);
        }

        WRITER.close();
    }

    private static void displayRank(Team[] teams, TreeSet<Team> sortedTeams) throws IOException {
        if (teams[1] == null) {
            WRITER.write(Integer.toString(sortedTeams.size() + 1));
            WRITER.newLine();
            return;
        }

        var lowerTeams = sortedTeams.headSet(teams[1]);
        WRITER.write(Integer.toString(lowerTeams.size() + 1));
        WRITER.newLine();
    }

    private static class Team {
        public final int teamNum;
        public int solvedProblems;
        public int penalty;

        public Team(int teamNum, int solvedProblems, int penalty) {
            this.teamNum = teamNum;
            this.solvedProblems = solvedProblems;
            this.penalty = penalty;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Team other) {
                return teamNum == other.teamNum;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return teamNum;
        }
    }
}
