import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static boolean isFirstCase = true;

    public static void main(String[] args) throws IOException {
        ArrayList<String> group;
        while (!(group = getNextGroup()).isEmpty()) {
            Collections.sort(group, (a, b) -> compareWords(a, b));
            printGroup(group);
        }

        WRITER.close();
    }

    private static void printGroup(ArrayList<String> group) throws IOException {
        int longest = group.stream().max((a, b) -> a.length() - b.length()).get().length();
        if (isFirstCase) {
            isFirstCase = false;
        } else {
            WRITER.newLine();
        }
        for (int i = 0; i < group.size(); i++) {
            if (i != 0) {
                WRITER.newLine();
            }
            addSpaces(longest - group.get(i).length());
            WRITER.write(group.get(i));
        }

        WRITER.newLine();
    }

    private static void addSpaces(int amount) throws IOException {
        String spaces = "";
        for (int i = 0; i < amount; i++) {
            spaces += ' ';
        }

        WRITER.write(spaces);
    }

    private static int compareWords(String a, String b) {
        for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
            char aLetter = a.charAt(a.length() - 1 - i);
            char bLetter = b.charAt(b.length() - 1 - i);

            if (aLetter == bLetter) {
                continue;
            }

            return aLetter - bLetter;
        }

        return a.length() < b.length() ? -1 : 1;
    }

    private static ArrayList<String> getNextGroup() throws IOException {
        ArrayList<String> words = new ArrayList<>();
        while (true) {
            String word = READER.readLine();
            if (word == null) {
                return words;
            }

            if (word.equals("")) {
                return words;
            }

            words.add(word);
        }
    }
}
