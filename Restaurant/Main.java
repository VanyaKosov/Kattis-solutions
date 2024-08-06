import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        boolean isFirst = true;
        int commandCount;
        while ((commandCount = Integer.parseInt(READER.readLine())) != 0) {
            int firstPileSize = 0;
            int secondPileSize = 0;

            if (!isFirst) {
                WRITER.newLine();
            }
            isFirst = false;

            for (; commandCount > 0; commandCount--) {
                String[] line = READER.readLine().split(" ");
                String command = line[0];
                int count = Integer.parseInt(line[1]);

                if (command.equals("DROP")) {
                    firstPileSize += count;
                    WRITER.write("DROP 1 " + Integer.toString(count));
                    WRITER.newLine();
                    continue;
                }

                if (secondPileSize == 0) {
                    WRITER.write("MOVE 1->2 " + Integer.valueOf(firstPileSize));
                    WRITER.newLine();
                    secondPileSize += firstPileSize;
                    firstPileSize = 0;
                }

                if (secondPileSize >= count) {
                    WRITER.write("TAKE 2 " + Integer.toString(count));
                    WRITER.newLine();
                    secondPileSize -= count;
                    continue;
                }

                WRITER.write("TAKE 2 " + Integer.toString(secondPileSize));
                WRITER.newLine();
                count -= secondPileSize;
                secondPileSize = 0;
                WRITER.write("MOVE 1->2 " + Integer.valueOf(firstPileSize));
                WRITER.newLine();
                secondPileSize += firstPileSize;
                firstPileSize = 0;
                WRITER.write("TAKE 2 " + Integer.toString(count));
                WRITER.newLine();
                secondPileSize -= count;
            }
        }

        WRITER.close();
    }
}
