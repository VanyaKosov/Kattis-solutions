import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        READER.readLine();
        int[] mountains = Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
        int[] leftLargest = getLeftLargest(mountains);
        int[] rightLargest = getRightLargest(mountains);

        int answer = 0;
        for (int i = 0; i <= mountains.length - 1 - 2; i++) {
            int minHeight = Math.min(leftLargest[i], rightLargest[i + 2]);
            if (minHeight <= mountains[i + 1]) { // Probably not necessary
                continue;
            }
            answer = Math.max(answer, minHeight - mountains[i + 1]);
        }

        WRITER.write(Integer.toString(answer));
        WRITER.close();
    }

    private static int[] getLeftLargest(int[] mountains) {
        int[] leftLargest = new int[mountains.length];
        leftLargest[0] = mountains[0];
        for (int i = 1; i < mountains.length; i++) {
            leftLargest[i] = Math.max(leftLargest[i - 1], mountains[i]);
        }

        return leftLargest;
    }

    private static int[] getRightLargest(int[] mountains) {
        int[] rightLargest = new int[mountains.length];
        rightLargest[rightLargest.length - 1] = mountains[mountains.length - 1];
        for (int i = mountains.length - 2; i >= 0; i--) {
            rightLargest[i] = Math.max(rightLargest[i + 1], mountains[i]);
        }

        return rightLargest;
    }
}
