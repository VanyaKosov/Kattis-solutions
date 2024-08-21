import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final ArrayList<Long> primes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        primes.add(2L);

        long numCandies = Long.parseLong(READER.readLine());
        ArrayList<Long> factors = new ArrayList<>();
        for (int i = 0;; i++) {
            if (i == primes.size()) {
                calculateNextPrime();
            }

            if (primes.get(i) * primes.get(i) > numCandies) {
                factors.add(numCandies);
                break;
            }

            while (numCandies % primes.get(i) == 0) {
                factors.add(primes.get(i));
                numCandies = numCandies / primes.get(i);
            }
        }

        if (!factors.get(factors.size() - 1).equals(1L)) {
            factors.add(1L);
        }

        WRITER.close();
    }

    private static long calculateNextPrime() {
        for (long num = primes.get(primes.size() - 1) + 1;; num++) {
            boolean found = true;
            for (Long prime : primes) {
                if (num % prime == 0) {
                    found = false;
                    break;
                }
            }

            if (found) {
                primes.add(num);
                return num;
            }
        }
    }
}
