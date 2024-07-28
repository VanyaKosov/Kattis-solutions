import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Student[] names;
        while ((names = getStudents()) != null) {
            Arrays.sort(names);
            outputNames(names);
        }

        WRITER.close();
    }

    private static void outputNames(Student[] names) throws IOException {
        for (int i = 0; i < names.length; i++) {
            WRITER.write(names[i].name);
            WRITER.newLine();
        }
        WRITER.newLine();
    }

    private static Student[] getStudents() throws IOException {
        int numNames = Integer.parseInt(READER.readLine());
        if (numNames == 0) {
            return null;
        }

        Student[] names = new Student[numNames];
        for (int i = 0; i < numNames; i++) {
            names[i] = new Student(READER.readLine());
        }

        return names;
    }

    private static class Student implements Comparable<Student> {
        public final String name;
        private final String prefix;

        public Student(String name) {
            this.name = name;
            prefix = name.substring(0, 2);
        }

        @Override
        public int compareTo(Student other) {
            return prefix.compareTo(other.prefix);
        }

    }
}
