import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int testCount = Integer.parseInt(READER.readLine());
        for (; testCount > 0; testCount--) {
            READER.readLine();
            int[] nums = Arrays.stream(READER.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
            TreeSet<Int> sortedNums = new TreeSet<>();
            HashMap<Integer, Integer> numCounts = new HashMap<>(sortedNums.size());

            if (nums.length == 1) {
                System.out.println(nums[0]);
                continue;
            }

            if (nums.length == 2) {
                System.out.println(nums[0] + ((nums[0] + nums[1]) / 2));
                continue;
            }

            long sum = 0;
            sum += nums[0];
            sum += (nums[0] + nums[1]) / 2;

            Int previousMiddle = nextInt(nums[0], numCounts);
            sortedNums.add(previousMiddle);
            sortedNums.add(nextInt(nums[1], numCounts));
            Int previousLeft = sortedNums.first();
            Int previousRight = sortedNums.last();

            for (int i = 2; i < nums.length; i++) {
                Int num = nextInt(nums[i], numCounts);
                sortedNums.add(num);

                if (sortedNums.size() % 2 != 0) {
                    if (num.compareTo(previousLeft) > 0 && num.compareTo(previousRight) < 0) {
                        previousMiddle = num;
                    } else if (num.compareTo(previousLeft) < 0) {
                        previousMiddle = previousLeft;
                    } else {
                        previousMiddle = previousRight;
                    }

                    sum += previousMiddle.value;
                } else {
                    if (num.compareTo(previousMiddle) < 0) {
                        previousLeft = sortedNums.lower(previousMiddle);
                        previousRight = previousMiddle;
                    } else {
                        previousRight = sortedNums.higher(previousMiddle);
                        previousLeft = previousMiddle;
                    }

                    sum += (previousLeft.value + previousRight.value) / 2;
                }
            }

            System.out.println(sum);
        }
    }

    private static Int nextInt(int desiredNum, HashMap<Integer, Integer> numCounts) {
        Integer count = numCounts.get(desiredNum);
        if (count == null) {
            numCounts.put(desiredNum, 1);
            return new Int(desiredNum, 1);
        }

        numCounts.put(desiredNum, count + 1);
        return new Int(desiredNum, count + 1);
    }

    private static class Int implements Comparable<Int> {
        public final int value;
        public final int order;

        public Int(int value, int order) {
            this.value = value;
            this.order = order;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Int otherInt) {
                return value == otherInt.value && order == otherInt.order;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return value * order + (value % order);
        }

        @Override
        public int compareTo(Int other) {
            if (value == other.value) {
                return order - other.order;
            }

            return value - other.value;
        }
    }
}