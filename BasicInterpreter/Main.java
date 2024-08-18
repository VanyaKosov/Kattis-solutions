import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final int[] vars = new int[26];
    private static ArrayList<Command> commands;
    private static HashMap<Integer, Integer> lineToIndex;
    private static int currentLineIndex;

    public static void main(String[] args) throws IOException {
        commands = readCommands();
        commands.sort((a, b) -> a.getLine() - b.getLine());
        lineToIndex = generateLineToIndexMap();

        currentLineIndex = 0;
        while (currentLineIndex != commands.size()) {
            commands.get(currentLineIndex).execute();
        }

        WRITER.close();
    }

    private static HashMap<Integer, Integer> generateLineToIndexMap() {
        HashMap<Integer, Integer> lineToIndex = new HashMap<>(commands.size());

        for (int i = 0; i < commands.size(); i++) {
            lineToIndex.put(commands.get(i).getLine(), i);
        }

        return lineToIndex;
    }

    private static ArrayList<Command> readCommands() throws IOException {
        ArrayList<Command> commands = new ArrayList<>();

        ArrayList<String> nextLine = null;
        while ((nextLine = readNextLine()) != null) {
            int lineNumber = Integer.parseInt(nextLine.get(0));
            String type = nextLine.get(1);

            if (type.equals("LET")) {
                commands.add(generateLet(nextLine, lineNumber));
            } else if (type.equals("IF")) {
                commands.add(generateIf(nextLine, lineNumber));
            } else if (type.equals("PRINT")) {
                commands.add(generatePrint(nextLine, lineNumber));
            } else {
                commands.add(generatePrintln(nextLine, lineNumber));
            }
        }

        return commands;
    }

    private static Command generatePrintln(ArrayList<String> line, int lineNumber) {
        String value = line.get(2);

        if (line.get(2).charAt(0) == '"') {
            return new Println(lineNumber, new ConstPrint(value.substring(1, value.length() - 1)));
        }

        return new Println(lineNumber, new VarPrint(value.charAt(0)));
    }

    private static Command generatePrint(ArrayList<String> line, int lineNumber) {
        String value = line.get(2);

        if (line.get(2).charAt(0) == '"') {
            return new Print(lineNumber, new ConstPrint(value.substring(1, value.length() - 1)));
        }

        return new Print(lineNumber, new VarPrint(value.charAt(0)));
    }

    private static Command generateIf(ArrayList<String> line, int lineNumber) {
        //int gotoLine = Integer.parseInt(line.get(7));
        Value gotoLine;
        Integer number = tryNumber(line.get(7));
        if (number == null) {
            gotoLine = new VarValue(line.get(7).charAt(0));
        } else {
            gotoLine = new ConstValue(Integer.parseInt(line.get(7)));
        }

        Value v1;
        Integer first = tryNumber(line.get(2));
        if (first == null) {
            v1 = new VarValue(line.get(2).charAt(0));
        } else {
            v1 = new ConstValue(first);
        }

        Value v2;
        Integer second = tryNumber(line.get(4));
        if (second == null) {
            v2 = new VarValue(line.get(4).charAt(0));
        } else {
            v2 = new ConstValue(second);
        }

        String action = line.get(3);
        if (action.equals("=")) {
            return new If(lineNumber, new Comparison(v1, v2, (a, b) -> a == b), gotoLine);
        } else if (action.equals(">")) {
            return new If(lineNumber, new Comparison(v1, v2, (a, b) -> a > b), gotoLine);
        } else if (action.equals("<")) {
            return new If(lineNumber, new Comparison(v1, v2, (a, b) -> a < b), gotoLine);
        } else if (action.equals("<>")) {
            return new If(lineNumber, new Comparison(v1, v2, (a, b) -> a != b), gotoLine);
        } else if (action.equals("<=")) {
            return new If(lineNumber, new Comparison(v1, v2, (a, b) -> a <= b), gotoLine);
        } else {
            return new If(lineNumber, new Comparison(v1, v2, (a, b) -> a >= b), gotoLine);
        }
    }

    private static Command generateLet(ArrayList<String> line, int lineNumber) {
        char variable = line.get(2).charAt(0);

        if (line.size() == 5) {
            Integer number = tryNumber(line.get(4));
            if (number == null) {
                char varName = line.get(4).charAt(0);
                return new Let(lineNumber, variable, new VarValue(varName));
            } else {
                int value = Integer.parseInt(line.get(4));
                return new Let(lineNumber, variable, new ConstValue(value));
            }
            //return new Let(lineNumber, variable, new ConstValue(value));
        }

        Value v1;
        Integer first = tryNumber(line.get(4));
        if (first == null) {
            v1 = new VarValue(line.get(4).charAt(0));
        } else {
            v1 = new ConstValue(first);
        }

        Value v2;
        Integer second = tryNumber(line.get(6));
        if (second == null) {
            v2 = new VarValue(line.get(6).charAt(0));
        } else {
            v2 = new ConstValue(second);
        }

        String action = line.get(5);
        if (action.equals("-")) {
            return new Let(lineNumber, variable, new Arithmetic(v1, v2, (a, b) -> a - b));
        } else if (action.equals("+")) {
            return new Let(lineNumber, variable, new Arithmetic(v1, v2, (a, b) -> a + b));
        } else if (action.equals("*")) {
            return new Let(lineNumber, variable, new Arithmetic(v1, v2, (a, b) -> a * b));
        } else {
            return new Let(lineNumber, variable, new Arithmetic(v1, v2, (a, b) -> a / b));
        }
    }

    private static Integer tryNumber(String string) {
        if (string.charAt(0) <= '9') {
            return Integer.valueOf(string);
        }

        return null;
    }

    private static ArrayList<String> readNextLine() throws IOException {
        String line = READER.readLine();
        if (line == null) {
            return null;
        }

        ArrayList<String> splitLine = new ArrayList<>();

        while (line.length() != 0) {
            String[] split = line.split(" ", 2);
            splitLine.add(split[0]);
            line = split.length > 1 ? split[1] : null;

            if (line == null) {
                break;
            }

            if (line.charAt(0) == '"') {
                splitLine.add(line);
                break;
            }
        }

        return splitLine;
    }

    private static class Let implements Command {
        private final int line;
        private final char variable;
        private final ArithmeticStatement statement;

        public Let(int line, char variable, ArithmeticStatement statement) {
            this.line = line;
            this.variable = variable;
            this.statement = statement;
        }

        public void execute() {
            vars[variable - 'A'] = statement.result();

            currentLineIndex++;
        }

        public int getLine() {
            return line;
        }
    }

    private static class If implements Command {
        private final int line;
        private final Comparison condition;
        private final Value gotoLine;

        public If(int line, Comparison condition, Value gotoLine) {
            this.line = line;
            this.condition = condition;
            this.gotoLine = gotoLine;
        }

        public void execute() {
            if (condition.result()) {
                currentLineIndex = lineToIndex.get(gotoLine.get());
            } else {
                currentLineIndex++;
            }
        }

        public int getLine() {
            return line;
        }
    }

    private static class Print implements Command {
        private final int line;
        private final PrintStatement value;

        public Print(int line, PrintStatement value) {
            this.line = line;
            this.value = value;
        }

        public void execute() throws IOException {
            WRITER.write(value.get());

            currentLineIndex++;
        }

        public int getLine() {
            return line;
        }
    }

    private static class Println implements Command {
        private final int line;
        private final PrintStatement value;

        public Println(int line, PrintStatement value) {
            this.line = line;
            this.value = value;
        }

        public void execute() throws IOException {
            WRITER.write(value.get());
            WRITER.newLine();

            currentLineIndex++;
        }

        public int getLine() {
            return line;
        }
    }

    private static class VarValue implements Value, ArithmeticStatement {
        private final char name;

        public VarValue(char name) {
            this.name = name;
        }

        public int get() {
            return vars[name - 'A'];
        }

        public int result() {
            return vars[name - 'A'];
        }
    }

    private static class ConstValue implements Value, ArithmeticStatement {
        private final int value;

        public ConstValue(int value) {
            this.value = value;
        }

        public int get() {
            return value;
        }

        public int result() {
            return value;
        }
    }

    private static class Arithmetic implements ArithmeticStatement {
        private final ArithmeticComparator comparator;
        private final Value v1;
        private final Value v2;

        public Arithmetic(Value v1, Value v2, ArithmeticComparator comparator) {
            this.v1 = v1;
            this.v2 = v2;
            this.comparator = comparator;
        }

        public int result() {
            return comparator.result(v1.get(), v2.get());
        }
    }

    private static class Comparison {
        public final Value v1;
        public final Value v2;
        private final ConditionCompare comparator;

        public Comparison(Value v1, Value v2, ConditionCompare comparator) {
            this.v1 = v1;
            this.v2 = v2;
            this.comparator = comparator;
        }

        public boolean result() {
            return comparator.result(v1.get(), v2.get());
        }
    }

    private static class VarPrint implements PrintStatement {
        public final char name;

        public VarPrint(char name) {
            this.name = name;
        }

        public String get() {
            return Integer.toString(vars[name - 'A']);
        }
    }

    private static class ConstPrint implements PrintStatement {
        public final String value;

        public ConstPrint(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }

    private interface PrintStatement {
        public String get();
    }

    @FunctionalInterface
    private interface ConditionCompare {
        boolean result(int a, int b);
    }

    @FunctionalInterface
    private interface ArithmeticComparator {
        int result(int a, int b);
    }

    private interface ArithmeticStatement {
        public int result();
    }

    private interface Command {
        public void execute() throws IOException;

        public int getLine();
    }

    private interface Value {
        public int get();
    }
}
