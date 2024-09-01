import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int median = Integer.parseInt(READER.readLine().split(" ")[1]);
        int[] nums = Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
        int medianIndex = IntStream.range(0, nums.length).filter(i -> nums[i] == median).findFirst().getAsInt();

        int rightDiff = 0;
        HashMap<Integer, Integer> rightDiffCounts = new HashMap<>(nums.length);
        rightDiffCounts.put(0, 1);
        for (int i = medianIndex + 1; i < nums.length; i++) {
            rightDiff += nums[i] < median ? -1 : 1;
            Integer diffCount = rightDiffCounts.get(rightDiff);
            if (diffCount == null) {
                rightDiffCounts.put(rightDiff, 1);
            } else {
                rightDiffCounts.put(rightDiff, diffCount + 1);
            }
        }

        int answer = rightDiffCounts.get(0);
        int leftDiff = 0;
        for (int i = medianIndex - 1; i >= 0; i--) {
            leftDiff += nums[i] < median ? -1 : 1;

            Integer compensationCount = rightDiffCounts.get(-leftDiff);
            if (compensationCount == null) {
                continue;
            }

            answer += compensationCount;
        }

        WRITER.write(Integer.toString(answer));
        WRITER.close();
    }
}
