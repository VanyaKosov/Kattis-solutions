import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        TreeSet<String> maleNames = new TreeSet<>();
        TreeSet<String> femaleNames = new TreeSet<>();

        String[] info = READER.readLine().split(" ");
        while (info[0].charAt(0) != '0') {
            char command = info[0].charAt(0);
            switch (command) {
                case '1': // Add
                    if (info[2].charAt(0) == '1') {
                        maleNames.add(info[1]);
                    } else {
                        femaleNames.add(info[1]);
                    }

                    break;
                case '2': // Remove
                    if (!maleNames.remove(info[1])) { // May use HashSet to speed up
                        femaleNames.remove(info[1]);
                    }

                    break;
                case '3': // Query
                    char gender = info[3].charAt(0);
                    String from = info[1];
                    String to = info[2] + "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";
                    int numNames = 0;
                    if (gender == '1') {
                        numNames += maleNames.subSet(from, true, to, true).size();
                    } else if (gender == '2') {
                        numNames += femaleNames.subSet(from, true, to, true).size();
                    } else {
                        numNames += maleNames.subSet(from, true, to, true).size();
                        numNames += femaleNames.subSet(from, true, to, true).size();
                    }

                    WRITER.write(Integer.toString(numNames));
                    WRITER.newLine();

                    break;
            }

            info = READER.readLine().split(" ");
        }
        WRITER.close();
    }
}
