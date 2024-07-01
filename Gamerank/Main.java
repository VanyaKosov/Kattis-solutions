import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static HashMap<Integer, Integer> rankStars = new HashMap<>() {
        {
            put(25, 2);
            put(24, 2);
            put(23, 2);
            put(22, 2);
            put(21, 2);
            put(20, 3);
            put(19, 3);
            put(18, 3);
            put(17, 3);
            put(16, 3);
            put(15, 4);
            put(14, 4);
            put(13, 4);
            put(12, 4);
            put(11, 4);
            put(10, 5);
            put(9, 5);
            put(8, 5);
            put(7, 5);
            put(6, 5);
            put(5, 5);
            put(4, 5);
            put(3, 5);
            put(2, 5);
            put(1, 5);
        }
    };
    private static int rank = 25;
    private static int stars = 0;
    private static int winStreak = 0;

    public static void main(String[] args) throws IOException {
        String games = reader.readLine();
        for (int i = 0; i < games.length(); i++) {
            char game = games.charAt(i);
            if (game == 'W') {
                win();
            } else {
                loss();
            }
        }

        if (rank == 0) {
            writer.write("Legend");
        } else {
            writer.write(Integer.toString(rank));
        }

        writer.close();
    }

    private static void win() {
        if (rank == 0) {
            return;
        }

        winStreak++;

        if (winStreak >= 3 && rank >= 6) {
            addStar();
        }

        addStar();
    }

    private static void addStar() {
        stars++;
        if (stars > rankStars.get(rank)) {
            rank--;
            stars = 1;
        }
    }

    private static void loss() {
        if (rank == 0) {
            return;
        }

        winStreak = 0;
        if (rank > 20) {
            return;
        }

        stars--;
        if (stars >= 0) {
            return;
        }

        if (rank == 20 || rank == 25) {
            stars = 0;
            return;
        }

        rank++;
        stars = rankStars.get(rank) - 1;
    }
}
