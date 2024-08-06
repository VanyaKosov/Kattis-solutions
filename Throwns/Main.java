import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] info = READER.readLine().split(" ");
        int numChildren = Integer.parseInt(info[0]);
        int numCommands = Integer.parseInt(info[1]);
        String[] commands = READER.readLine().split(" ");
        int currentChild = 0;
        Stack<Integer> pastCommands = new Stack<>();

        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals("undo")) {
                i++;
                int turnsToUndo = Integer.parseInt(commands[i]);
                for (; turnsToUndo > 0; turnsToUndo--) {
                    currentChild = nextChild(currentChild, numChildren, -pastCommands.pop());
                }

                continue;
            }

            int command = Integer.parseInt(commands[i]);
            currentChild = nextChild(currentChild, numChildren, command);
            pastCommands.push(command);
        }

        WRITER.write(Integer.toString(currentChild));
        WRITER.close();
    }

    private static int nextChild(int currentChild, int numChildren, int action) {
        action = action % numChildren;
        currentChild += action;
        if (currentChild < 0) {
            currentChild += numChildren;
        }
        if (currentChild >= numChildren) {
            currentChild -= numChildren;
        }

        return currentChild;
    }
}
