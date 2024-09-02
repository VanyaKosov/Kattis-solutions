import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        HashMap<String, String> students = new HashMap<>(10000);
        HashSet<String> badStudents = new HashSet<>(10000);
        HashMap<String, Integer> projects = new HashMap<>(100);
        String currentProject = "";
        while (true) {
            String line = READER.readLine();
            if (line.length() == 1) {
                if (line.charAt(0) == '0') {
                    break;
                }
                if (line.charAt(0) == '1') {
                    printResults(students, badStudents, projects);
                    students = new HashMap<>(10000);
                    badStudents = new HashSet<>(10000);
                    projects = new HashMap<>(100);
                    continue;
                }
            }

            if (Character.isUpperCase(line.charAt(0))) {
                currentProject = line;
                projects.put(currentProject, 0);
                continue;
            }

            if (badStudents.contains(line)) {
                continue;
            }

            if (students.get(line) != null && !students.get(line).equals(currentProject)) {
                badStudents.add(line);
                continue;
            }

            students.put(line, currentProject);
        }

        WRITER.close();
    }

    private static void printResults(HashMap<String, String> students, HashSet<String> badStudents,
            HashMap<String, Integer> projects) throws IOException {
        for (var student : students.entrySet()) {
            if (badStudents.contains(student.getKey())) {
                continue;
            }

            Integer numSignUps = projects.remove(student.getValue());
            projects.put(student.getValue(), numSignUps + 1);
        }

        var sortedProjects = projects.entrySet().stream()
                .sorted((a, b) -> {
                    if (a.getValue() == b.getValue()) {
                        return a.getKey().compareTo(b.getKey());
                    }

                    return b.getValue() - a.getValue();
                }).toList();
        for (var project : sortedProjects) {
            WRITER.write(project.getKey());
            WRITER.write(' ');
            WRITER.write(Integer.toString(project.getValue()));
            WRITER.newLine();
        }
    }

    // public static class Project {
    //     public final String name;
    //     public int numParticipants = 0;

    //     public Project(String name) {
    //         this.name = name;
    //     }
    // }
}
