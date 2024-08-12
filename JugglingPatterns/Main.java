import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String line;
        while ((line = READER.readLine()) != null) {
            int[] pattern = line.chars().map(a -> a - '0').toArray();

            int patternSum = Arrays.stream(pattern).sum();
            if (patternSum % pattern.length != 0) {
                WRITER.write(line + ": invalid # of balls");
                WRITER.newLine();
                continue;
            }
            int ballCount = patternSum / pattern.length;

            PriorityQueue<Throw> thrownBalls = new PriorityQueue<>(10, (a, b) -> a.timeLands - b.timeLands);
            char throwingHand = 'L';
            thrownBalls.add(new Throw(pattern[0], pattern[0] % 2 == 0 ? 'L' : 'R'));
            throwingHand = 'R';

            boolean isValid = true;
            int beat = 0;
            while (!thrownBalls.isEmpty()) {
                beat++;

                if (thrownBalls.peek().timeLands == beat) {
                    Throw caughtBall = thrownBalls.poll();
                    thrownBalls.add(new Throw(beat + pattern[beat],
                            pattern[beat] % 2 == 0 ? throwingHand : throwingHand == 'L' ? 'R' : 'L'));
                    throwingHand = throwingHand == 'L' ? 'R' : 'L';

                    if (thrownBalls.peek().timeLands == beat) {
                        isValid = false;
                        break;
                    }
                }
            }

            if (isValid) {
                WRITER.write(line + ": valid with " + ballCount + " balls");
                WRITER.newLine();
            } else {
                WRITER.write(line + ": invalid pattern");
                WRITER.newLine();
            }

            /*for (int i = 0; i < pattern.length; i++) {
                char throwingHand = i % 2 == 0 ? 'L' : 'R';
                char receivingHand = throwingHand;
                if (pattern[i] % 2 != 0) {
                    receivingHand = receivingHand == 'L' ? 'R' : 'L';
                }
                ballThrows.add(new Throw(pattern[i] + i, receivingHand));
            }
            
            // TODO: check hands, account for the empty hands,
            // don't add all the balls to the priority queue at the beginning, 
            // zero means that no ball is thrown
            boolean isValid = true;
            while (!ballThrows.isEmpty()) {
                Throw currentCatch = ballThrows.poll();
                if (ballThrows.peek() != null && currentCatch.timeLands == ballThrows.peek().timeLands) {
                    isValid = false;
                    break;
                }
            }
            
            if (isValid) {
                WRITER.write(line + ": valid with " + ballCount + " balls");
                WRITER.newLine();
            } else {
                WRITER.write(line + ": invalid pattern");
                WRITER.newLine();
            }*/
        }

        WRITER.close();
    }

    private static class Throw {
        public final int timeLands;
        public final char hand;

        public Throw(int timeLands, char hand) {
            this.timeLands = timeLands;
            this.hand = hand;
        }
    }
}
