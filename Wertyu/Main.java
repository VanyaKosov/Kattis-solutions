import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        HashMap<Character, Character> converter = getConverter();
        ArrayList<String> input = getInput();
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            StringBuilder answer = new StringBuilder();
            for (int j = 0; j < line.length(); j++) {
                answer.append(converter.get(line.charAt(j)));
            }

            if (i != 0) {
                writer.newLine();
            }
            writer.write(answer.toString());
        }
        writer.close();
    }

    private static HashMap<Character, Character> getConverter() {
        String[] lines = {
                "`1234567890-=",
                "QWERTYUIOP[]\\",
                "ASDFGHJKL;'",
                "ZXCVBNM,./"
        };

        HashMap<Character, Character> converter = new HashMap<>();
        converter.put(' ', ' ');
        for (String line : lines) {
            for (int i = 1; i < line.length(); i++) {
                converter.put(line.charAt(i), line.charAt(i - 1));
            }
        }

        return converter;
    }

    private static ArrayList<String> getInput() throws IOException {
        ArrayList<String> input = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            input.add(line);
        }

        return input;
    }
}
