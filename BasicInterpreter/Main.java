import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static ArrayList<Line> allLines = new ArrayList<>();
    private static int[] vars = new int[26];
    private static HashMap<Integer, Integer> lineToIndex = new HashMap<>();

    public static void main(String[] args) throws IOException {
        readAllLines();
        allLines.sort((a, b) -> a.lineNumber - b.lineNumber);
        generateLineToIndexMap();

        int currentLineIndex = 0;
        while (currentLineIndex != allLines.size()) {
            var line = allLines.get(currentLineIndex).line;
            if (line.get(1).equals("LET")) {
                let(line);
            } else if (line.get(1).equals("PRINT")) {
                print(line);
            } else if (line.get(1).equals("PRINTLN")) {
                print(line);
                WRITER.newLine();
            } else {
                Integer result = ifThen(line);
                if (result != null) {
                    currentLineIndex = lineToIndex.get(result);
                    continue;
                }
            }

            currentLineIndex++;
        }

        WRITER.close();
    }

    private static Integer ifThen(ArrayList<String> line) { // The GOTO line is a number not a variable
        String operation = line.get(3);

        Integer first = isNumber(line.get(2));
        if (first == null) {
            first = vars[line.get(2).charAt(0) - 'A'];
        }
        Integer second = isNumber(line.get(4));
        if (second == null) {
            second = vars[line.get(4).charAt(0) - 'A'];
        }

        if (operation.equals("=")) {
            return first == second ? Integer.valueOf(line.get(7)) : null;
        } else if (operation.equals(">")) {
            return first > second ? Integer.valueOf(line.get(7)) : null;
        } else if (operation.equals("<")) {
            return first < second ? Integer.valueOf(line.get(7)) : null;
        } else if (operation.equals("<>")) {
            return first != second ? Integer.valueOf(line.get(7)) : null;
        } else if (operation.equals("<=")) {
            return first <= second ? Integer.valueOf(line.get(7)) : null;
        } else {
            return first >= second ? Integer.valueOf(line.get(7)) : null;
        }
    }

    private static void print(ArrayList<String> line) throws IOException {
        String text = line.get(2);
        if (line.get(line.size() - 1) != "\"") {
            text = Integer.toString(vars[text.charAt(0) - 'A']);
        }

        WRITER.write(text);
    }

    private static void let(ArrayList<String> line) {
        if (line.size() == 5) {
            vars[line.get(2).charAt(0) - 'A'] = Integer.parseInt(line.get(4));
            return;
        }

        char resultVariable = line.get(2).charAt(0);
        char operation = line.get(5).charAt(0);

        Integer first = isNumber(line.get(4));
        if (first == null) {
            first = vars[line.get(4).charAt(0) - 'A'];
        }
        Integer second = isNumber(line.get(6));
        if (second == null) {
            second = vars[line.get(6).charAt(0) - 'A'];
        }

        switch (operation) {
            case '+':
                vars[resultVariable - 'A'] = first + second;
                return;
            case '-':
                vars[resultVariable - 'A'] = first - second;
                return;
            case '*':
                vars[resultVariable - 'A'] = first * second;
                return;
            case '/':
                vars[resultVariable - 'A'] = first / second;
                return;
        }

    }

    private static Integer isNumber(String string) {
        if (string.charAt(0) <= '9') {
            return Integer.valueOf(string);
        }

        return null;
    }

    private static void generateLineToIndexMap() {
        for (int i = 0; i < allLines.size(); i++) {
            lineToIndex.put(allLines.get(i).lineNumber, i);
        }
    }

    private static void readAllLines() throws IOException {
        String line = null;
        while ((line = READER.readLine()) != null) {
            ArrayList<String> splitLine = splitBySpace(line);
            Integer lineLabel = Integer.valueOf(splitLine.get(0));

            allLines.add(new Line(lineLabel, splitLine));
        }
    }

    private static ArrayList<String> splitBySpace(String line) {
        ArrayList<String> splitLine = new ArrayList<>();

        //boolean isAString = false;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '"') {
                //isAString = !isAString;
                splitLine.add(line.substring(1, line.length() - 1));
                splitLine.add("\"");
                break;
            }

            if (line.charAt(i) != ' ') {
                continue;
            }

            splitLine.add(line.substring(0, i));
            line = line.substring(i + 1);
            i = -1;
        }

        if (line.charAt(line.length() - 1) != '"') {
            splitLine.add(line);
        }

        return splitLine;
    }

    private static class Line {
        public final Integer lineNumber;
        public final ArrayList<String> line;

        public Line(Integer lineNumber, ArrayList<String> line) {
            this.lineNumber = lineNumber;
            this.line = line;
        }
    }
}