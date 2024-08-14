import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numTests = Integer.parseInt(READER.readLine());
        for (; numTests > 0; numTests--) {
            PriorityQueue<int[]> maxBuying = new PriorityQueue<>((a, b) -> b[1] - a[1]);
            PriorityQueue<int[]> minSelling = new PriorityQueue<>((a, b) -> a[1] - b[1]);

            int lastDeal = 0;

            int numOrders = Integer.parseInt(READER.readLine());
            for (; numOrders > 0; numOrders--) {
                String[] info = READER.readLine().split(" ");
                int amount = Integer.parseInt(info[1]);
                int price = Integer.parseInt(info[4]);

                if (info[0].equals("buy")) {
                    maxBuying.add(new int[] { amount, price });
                } else {
                    minSelling.add(new int[] { amount, price });
                }

                while (minSelling.peek() != null && maxBuying.peek() != null
                        && minSelling.peek()[1] <= maxBuying.peek()[1]) {

                    lastDeal = minSelling.peek()[1];

                    if (minSelling.peek()[0] > maxBuying.peek()[0]) {
                        minSelling.peek()[0] -= maxBuying.peek()[0];
                        maxBuying.poll();
                        continue;
                    }

                    if (minSelling.peek()[0] == maxBuying.peek()[0]) {
                        minSelling.poll();
                        maxBuying.poll();
                        continue;
                    }

                    maxBuying.peek()[0] -= minSelling.peek()[0];
                    minSelling.poll();
                }

                String ask = minSelling.peek() == null ? "-" : Integer.toString(minSelling.peek()[1]);
                String bid = maxBuying.peek() == null ? "-" : Integer.toString(maxBuying.peek()[1]);
                String stockPrice = lastDeal == 0 ? "-" : Integer.toString(lastDeal);
                WRITER.write(ask + " " + bid + " " + stockPrice);
                WRITER.newLine();
            }
        }

        WRITER.close();
    }
}
