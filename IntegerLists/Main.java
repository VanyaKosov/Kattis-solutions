import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int numTests = Integer.parseInt(READER.readLine());
        for (int i = 0; i < numTests; i++) {
            boolean deleteFirst = true;
            String program = READER.readLine();
            ArrayDeque<Integer> list = getList(); // Remember to use equals() for Integer

            boolean error = false;
            for (int j = 0; j < program.length(); j++) {
                char command = program.charAt(j);
                if (command == 'R') {
                    deleteFirst = deleteFirst == true ? false : true;
                    continue;
                }

                if (list.isEmpty()) {
                    error = true;
                    break;
                }

                if (deleteFirst) {
                    list.removeFirst();
                } else {
                    list.removeLast();
                }
            }

            if (!deleteFirst) {
                list = reverseList(list);
            }

            if (error) {
                WRITER.write("error");
                WRITER.newLine();
            } else {
                WRITER.write(listToString(list));
                WRITER.newLine();
            }
        }

        WRITER.close();
    }

    private static ArrayDeque<Integer> reverseList(ArrayDeque<Integer> list) {
        ArrayDeque<Integer> reversedList = new ArrayDeque<>();
        Iterator<Integer> iterator = list.descendingIterator();
        while (iterator.hasNext()) {
            reversedList.add(iterator.next());
        }

        return reversedList; // Returns new list
    }

    private static String listToString(ArrayDeque<Integer> list) {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;

        result.append("[");
        while (!list.isEmpty()) {
            if (isFirst) {
                isFirst = false;
            } else {
                result.append(",");
            }

            result.append(list.pollFirst());
        }
        result.append("]");

        return result.toString();
    }

    private static ArrayDeque<Integer> getList() throws IOException {
        READER.readLine();
        String line = READER.readLine();
        if (line.equals("[]")) {
            return new ArrayDeque<Integer>();
        }

        return new ArrayDeque<Integer>(
                Arrays.stream(line.substring(1, line.length() - 1).split(","))
                        .mapToInt(a -> Integer.parseInt(a))
                        .boxed()
                        .collect(Collectors.toList()));
    }
}
