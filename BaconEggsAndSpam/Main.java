import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numCustomers = 0;
        while ((numCustomers = Integer.parseInt(READER.readLine())) != 0) {
            TreeMap<String, PriorityQueue<String>> dishOrders = new TreeMap<>((a, b) -> a.compareTo(b));
            for (int i = 0; i < numCustomers; i++) {
                String[] order = READER.readLine().split(" ");
                String customer = order[0];
                for (int j = 1; j < order.length; j++) {
                    String dish = order[j];
                    if (dishOrders.get(dish) == null) {
                        dishOrders.put(dish, new PriorityQueue<>((a, b) -> a.compareTo(b)));
                    }
                    dishOrders.get(dish).add(customer);
                }
            }

            for (var orderedDish : dishOrders.entrySet()) {
                WRITER.write(orderedDish.getKey());
                var value = orderedDish.getValue();
                while (!value.isEmpty()) {
                    WRITER.write(' ');
                    WRITER.write(value.poll());
                }

                WRITER.newLine();
            }
            WRITER.newLine();
        }

        WRITER.close();
    }
}