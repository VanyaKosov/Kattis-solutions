import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final HashMap<String, Integer> CLASS_VALUE = new HashMap<>() {
        {
            put("upper", 3);
            put("middle", 2);
            put("lower", 1);
        }
    };

    public static void main(String[] args) throws IOException {
        int numCases;
        numCases = Integer.parseInt(READER.readLine());
        for (; numCases > 0; numCases--) {
            Person[] currentCase = getNextCase();
            Arrays.sort(currentCase, (a, b) -> compareClass(b, a));
            printCase(currentCase);
        }

        WRITER.close();
    }

    private static void printCase(Person[] currentCase) throws IOException {
        for (int i = 0; i < currentCase.length; i++) {
            WRITER.write(currentCase[i].name);
            WRITER.newLine();
        }
        WRITER.write("==============================");
        WRITER.newLine();
    }

    private static Person[] getNextCase() throws IOException {
        int numPeople = Integer.parseInt(READER.readLine());
        Person[] people = new Person[numPeople];

        for (int i = 0; i < people.length; i++) {
            String[] nameAndClass = READER.readLine().split(": ");
            String name = nameAndClass[0];
            String[] socialClass = nameAndClass[1].substring(0, nameAndClass[1].length() - 6).split("-");
            people[i] = new Person(name, convertSocialClass(socialClass));
        }

        return people;
    }

    private static long convertSocialClass(String[] originalClass) { // Returned class should be read from left to right
        int[] tempSocialClass = new int[10];
        Arrays.fill(tempSocialClass, 2);
        for (int i = 0; i < originalClass.length; i++) {
            tempSocialClass[i] = CLASS_VALUE.get(originalClass[originalClass.length - 1 - i]);
        }

        long socialClass = 0;
        for (int i = 0; i < tempSocialClass.length; i++) {
            socialClass = socialClass * 10 + tempSocialClass[i];
        }

        return socialClass;
    }

    private static int compareClass(Person person1, Person person2) {
        if (person1.socialClass > person2.socialClass) {
            return 1;
        }
        if (person2.socialClass > person1.socialClass) {
            return -1;
        }
        return person2.name.compareTo(person1.name);
    }

    /*private static int compareClass(int[] class1, int[] class2) {
        int i = 0;
        for (; i < 10; i++) {
            int class1value = class1[i];
            int class2value = class2[i];
    
            if (class1value == class2value) {
                continue;
            }
            if (class1value > class2value) {
                return 1;
            }
            if (class2value > class1value) {
                return -1;
            }
        }
    
        return 0;
    }*/

    private static class Person {
        public final String name;
        public final long socialClass;

        public Person(String name, long socialClass) {
            this.name = name;
            this.socialClass = socialClass;
        }
    }
}
