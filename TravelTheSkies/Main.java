import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] startingInfo = READER.readLine().split(" ");
        int numAirports = Integer.parseInt(startingInfo[0]);
        int numDays = Integer.parseInt(startingInfo[1]);
        int numFlights = Integer.parseInt(startingInfo[2]);
        Arrivals[] allArrivals = new Arrivals[numAirports * numDays];
        int[] peopleAtAirports = new int[numAirports + 1];
        ArrayList<ArrayList<Flight>> allFlights = new ArrayList<>(numDays + 1);

        for (int i = 0; i <= numDays; i++) {
            allFlights.add(new ArrayList<>());
        }

        for (int i = 0; i < numFlights; i++) {
            String[] info = READER.readLine().split(" ");
            Flight flight = new Flight(Integer.parseInt(info[0]), Integer.parseInt(info[1]),
                    Integer.parseInt(info[2]), Integer.parseInt(info[3]));

            allFlights.get(flight.day).add(flight);
        }

        for (int i = 0; i < numAirports * numDays; i++) {
            String[] info = READER.readLine().split(" ");
            Arrivals arrivals = new Arrivals(Integer.parseInt(info[0]),
                    Integer.parseInt(info[1]), Integer.parseInt(info[2]));
            allArrivals[i] = arrivals;
        }
        Arrays.sort(allArrivals, (a, b) -> a.day - b.day);

        int i = 0;
        for (int day = 1; day <= numDays; day++) {
            while (i < allArrivals.length && allArrivals[i].day == day) {
                Arrivals arrivals = allArrivals[i];
                peopleAtAirports[arrivals.airport] += arrivals.numPeople;
                i++;
            }

            int[] flownPeople = new int[numAirports + 1];
            for (Flight flight : allFlights.get(day)) {
                peopleAtAirports[flight.from] -= flight.capacity;
                if (peopleAtAirports[flight.from] < 0) {
                    WRITER.write("suboptimal");
                    WRITER.close();
                    return;
                }

                flownPeople[flight.to] += flight.capacity;
            }

            for (int airport = 1; airport <= numAirports; airport++) {
                peopleAtAirports[airport] += flownPeople[airport];
            }
        }

        WRITER.write("optimal");
        WRITER.close();
    }

    private static class Arrivals {
        public final int airport;
        public final int day;
        public final int numPeople;

        public Arrivals(int airport, int day, int numPeople) {
            this.airport = airport;
            this.day = day;
            this.numPeople = numPeople;
        }
    }

    private static class Flight {
        public final int from;
        public final int to;
        public final int day;
        public final int capacity;

        public Flight(int from, int to, int day, int capacity) {
            this.from = from;
            this.to = to;
            this.day = day;
            this.capacity = capacity;
        }
    }
}
