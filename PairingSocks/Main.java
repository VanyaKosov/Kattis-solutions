import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Stack<Integer> socks = getSocks();
        Stack<Integer> extraSocks = new Stack<>();
        int stepCount = 0;

        while (!socks.isEmpty()) {
            stepCount++;

            if (!extraSocks.isEmpty() && extraSocks.peek().equals(socks.peek())) {
                socks.pop();
                extraSocks.pop();
                continue;
            }

            extraSocks.push(socks.pop());
        }

        if (extraSocks.isEmpty()) {
            WRITER.write(Integer.toString(stepCount));
        } else {
            WRITER.write("impossible");
        }
        WRITER.newLine();
        WRITER.close();
    }

    private static Stack<Integer> getSocks() throws IOException {
        READER.readLine();
        int[] reversedSocks = Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
        Stack<Integer> socks = new Stack<>();

        for (int i = reversedSocks.length - 1; i >= 0; i--) {
            socks.push(reversedSocks[i]);
        }

        return socks;
    }
}
