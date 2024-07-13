import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        var jimRatt = readLine();
        var people = IntStream.range(0, 10)
                .mapToObj(i -> readLine())
                .toList();
        var peopleState = new int[10];
        for (int i = 0; i < people.size(); i++) {
            peopleState[i] = people.get(i).get(2);
        }

        int time = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int jimUsage = jimRatt.get(j * 2);
                int jimRecovery = jimRatt.get(j * 2 + 1);
                var person = people.get(j);
                int usage = person.get(0);
                int recovery = person.get(1);

                int personUsageStart = peopleState[j] + (Math.max(time - peopleState[j], 0) / (usage + recovery))
                        * (usage + recovery);
                int personUsageEnd = personUsageStart + usage;

                if (personUsageStart > time) {
                    time += jimUsage;
                    peopleState[j] = Math.max(time, peopleState[j]);
                    time += jimRecovery;

                    continue;
                }

                if (personUsageStart == time) {
                    time += usage + jimUsage;
                } else if (personUsageEnd <= time) {
                    time += jimUsage;
                } else if (personUsageEnd > time) {
                    time += personUsageEnd - time + jimUsage;
                }

                peopleState[j] = Math.max(time, personUsageEnd + recovery);
                time += jimRecovery;
            }
        }

        time -= jimRatt.get(19);
        writer.write(Integer.toString(time));

        writer.close();
    }

    private static List<Integer> readLine() {
        try {
            return Arrays.stream(reader.readLine().split(" "))
                    .map(a -> Integer.parseInt(a))
                    .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
