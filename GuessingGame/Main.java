import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final HashMap<String, Integer> answerConverter = new HashMap<>() {
        {
            put("too high", 1);
            put("too low", -1);
        }
    };

    public static void main(String[] args) throws IOException {
        int guess;
        int[] answers = new int[12];
        while ((guess = Integer.valueOf(reader.readLine())) != 0) {
            String answer = reader.readLine();
            if (answer.equals("right on")) {
                if (answers[guess] != 0) {
                    writer.write("Stan is dishonest");
                    writer.newLine();
                    answers = new int[12];
                    continue;
                }

                writer.write("Stan may be honest");
                writer.newLine();
                answers = new int[12];
                continue;
            }

            for (int i = guess; i < answers.length && i >= 0; i += answerConverter.get(answer)) {
                answers[i] = answerConverter.get(answer);
            }
        }

        writer.close();
    }
}
