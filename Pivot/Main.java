import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int length = Integer.parseInt(reader.readLine());
        int[] nums = Arrays.stream(reader.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
        int[] largestFromLeft = getLargestFromLeft(nums, length);
        int[] smallestFromRight = getSmallestFromRight(nums, length);

        int answer = 0;
        for (int i = 0; i < length; i++) {
            if (largestFromLeft[i] <= nums[i] && smallestFromRight[i] >= nums[i]) {
                answer++;
            }
        }

        writer.write(Integer.toString(answer));
        writer.close();
    }

    private static int[] getLargestFromLeft(int[] nums, int length) {
        int[] result = new int[length];
        int largest = 0;
        for (int i = 0; i < length; i++) {
            largest = Math.max(nums[i], largest);
            result[i] = largest;
        }

        return result;
    }

    private static int[] getSmallestFromRight(int[] nums, int length) {
        int[] result = new int[length];
        int smallest = Integer.MAX_VALUE;
        for (int i = length - 1; i >= 0; i--) {
            smallest = Math.min(nums[i], smallest);
            result[i] = smallest;
        }

        return result;
    }
}
