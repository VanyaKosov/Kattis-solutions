import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        READER.readLine();
        READER.readLine();
        int[] entryTimes = Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
        int[] exitTimes = Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
        HashMap<Integer, Count> possibleAnswers = new HashMap<>(entryTimes.length * exitTimes.length);
        for (int entryTime : entryTimes) {
            for (int exitTime : exitTimes) {
                int timeDiff = exitTime - entryTime;
                if (timeDiff < 0) {
                    continue;
                }

                Count diffCount = possibleAnswers.get(timeDiff);
                if (diffCount == null) {
                    possibleAnswers.put(timeDiff, new Count());
                } else {
                    diffCount.count++;
                }
            }
        }

        int mostCommonTime = 0;
        int mostCommonCount = 0;
        var sets = possibleAnswers.entrySet();
        for (Entry<Integer, Count> entry : sets) {
            if (entry.getValue().count == mostCommonCount) {
                mostCommonTime = Math.min(mostCommonTime, entry.getKey());
            } else if (entry.getValue().count > mostCommonCount) {
                mostCommonCount = entry.getValue().count;
                mostCommonTime = entry.getKey();
            }
        }

        WRITER.write(Integer.toString(mostCommonTime));
        WRITER.close();
    }

    private static class Count {
        public int count = 1;
    }
}
