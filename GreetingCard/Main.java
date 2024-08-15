import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        //ArrayList<int[]> offsets = calculateAllOffsets();

        long answer = 0;

        int numPoints = Integer.parseInt(READER.readLine());
        HashSet<Long> points = new HashSet<>(numPoints);
        for (; numPoints > 0; numPoints--) {
            String[] pointInfo = READER.readLine().split(" ");
            int x = Integer.parseInt(pointInfo[0]);
            int y = Integer.parseInt(pointInfo[1]);

            Long point = packagePoint(x, y);

            for (int[] pos : getPointsAround(x, y)) {
                Long otherPoint = packagePoint(pos[0], pos[1]);
                if (points.contains(otherPoint)) {
                    answer++;
                }
            }

            points.add(point);
        }

        WRITER.write(Long.toString(answer));
        WRITER.close();
    }

    /*private static ArrayList<int[]> calculateAllOffsets() {
        ArrayList<int[]> offsets = new ArrayList<>();
        int squared2018 = 2018 * 2018;
        int y = 2017;
        for (int x = 0; x < 2017; x++) {
            int requiredSquaredY = squared2018 - x * x;
            int squaredY = y * y;
            while (squaredY > requiredSquaredY) {
                y--;
                squaredY = y * y;
            }
    
            if (squaredY == requiredSquaredY) {
                offsets.add(new int[] { x, y });
                continue;
            }
    
            if (squaredY < requiredSquaredY) {
                y++;
            }
        }
    
        return offsets;
    }*/

    private static ArrayList<int[]> getPointsAround(int x, int y) {
        ArrayList<int[]> pointsAround = new ArrayList<>(12);
        pointsAround.add(new int[] { x + 2018, y });
        pointsAround.add(new int[] { x - 2018, y });
        pointsAround.add(new int[] { x, y + 2018 });
        pointsAround.add(new int[] { x, y - 2018 });
        pointsAround.add(new int[] { x + 1680, y + 1118 });
        pointsAround.add(new int[] { x + 1118, y + 1680 });
        pointsAround.add(new int[] { x + 1118, y - 1680 });
        pointsAround.add(new int[] { x + 1680, y - 1118 });
        pointsAround.add(new int[] { x - 1680, y - 1118 });
        pointsAround.add(new int[] { x - 1118, y - 1680 });
        pointsAround.add(new int[] { x - 1118, y + 1680 });
        pointsAround.add(new int[] { x - 1680, y + 1118 });

        return pointsAround;
    }

    private static Long packagePoint(int x, int y) {
        long xLong = (long) x;
        long yLong = (long) y;
        xLong = xLong << 32;

        return xLong | yLong;
    }
}
