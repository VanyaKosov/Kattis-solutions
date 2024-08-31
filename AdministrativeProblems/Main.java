import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numTests = Integer.parseInt(READER.readLine());
        for (; numTests > 0; numTests--) {
            int numCars;
            int numEvents;
            {
                String[] info = READER.readLine().split(" ");
                numCars = Integer.parseInt(info[0]);
                numEvents = Integer.parseInt(info[1]);
            }
            HashMap<String, Car> cars = readCars(numCars);
            HashMap<String, Long> spyDebts = new HashMap<>(500);
            HashMap<String, String> spyCars = new HashMap<>(numCars);
            HashSet<String> inconsistentSpies = new HashSet<>(500);

            for (; numEvents > 0; numEvents--) { // Can a spy attempt pickup when car is used by other spy?
                String[] event = READER.readLine().split(" ");
                //int time = Integer.parseInt(event[0]);
                String spyName = event[1];
                char type = event[2].charAt(0);

                if (inconsistentSpies.contains(spyName)) {
                    continue;
                }

                if (spyDebts.get(spyName) == null) {
                    spyDebts.put(spyName, 0L);
                }

                switch (type) {
                    case 'p': // Pickup
                    {
                        String carName = event[3];
                        String car = spyCars.get(spyName);
                        if (car == null && !cars.get(carName).isTaken) {
                            cars.get(carName).isTaken = true;
                            spyCars.put(spyName, carName);
                            incrementDebt(spyName, cars.get(carName).pickupCost, spyDebts);
                            break;
                        }

                        inconsistentSpies.add(spyName);
                        break;
                    }
                    case 'r': // Return
                    {
                        int distance = Integer.parseInt(event[3]);
                        String carName = spyCars.get(spyName);
                        if (carName != null) {
                            cars.get(carName).isTaken = false;
                            incrementDebt(spyName, cars.get(carName).costPerKM * distance, spyDebts);
                            spyCars.remove(spyName);
                            break;
                        }

                        inconsistentSpies.add(spyName);
                        break;
                    }
                    case 'a': // Accident
                    {
                        int severity = Integer.parseInt(event[3]);
                        String carName = spyCars.get(spyName);
                        if (carName != null) {
                            int cost = cars.get(carName).catalogPrice * severity;
                            if (cost % 100 != 0) {
                                cost = cost / 100 + 1;
                            } else {
                                cost = cost / 100;
                            }
                            //double cost = Math.ceil(severity * cars.get(carName).catalogPrice / 100.0);
                            incrementDebt(spyName, cost, spyDebts);
                            break;
                        }

                        inconsistentSpies.add(spyName);
                        break;
                    }
                }
            }

            List<String> spiesInOrder = spyDebts.keySet().stream().sorted((a, b) -> a.compareTo(b)).toList();
            for (String spy : spiesInOrder) {
                WRITER.write(spy);
                WRITER.write(' ');

                if (spyCars.containsKey(spy) || inconsistentSpies.contains(spy)) {
                    WRITER.write("INCONSISTENT");
                } else {
                    WRITER.write(Long.toString(spyDebts.get(spy)));
                }
                WRITER.newLine();
            }
        }

        WRITER.close();
    }

    private static void incrementDebt(String spyName, int amount, HashMap<String, Long> spyDebts) {
        Long spyDebt = spyDebts.get(spyName);
        spyDebts.put(spyName, Long.valueOf(amount) + spyDebt);
    }

    private static HashMap<String, Car> readCars(int numCars) throws IOException {
        HashMap<String, Car> cars = new HashMap<>(numCars);

        for (; numCars > 0; numCars--) {
            String[] info = READER.readLine().split(" ");
            cars.put(info[0], new Car(Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[3])));
        }

        return cars;
    }

    private static class Car {
        public final int catalogPrice;
        public final int pickupCost;
        public final int costPerKM;
        public boolean isTaken = false;

        public Car(int catalogPrice, int pickupCost, int costPerKM) {
            this.catalogPrice = catalogPrice;
            this.pickupCost = pickupCost;
            this.costPerKM = costPerKM;
        }
    }
}
