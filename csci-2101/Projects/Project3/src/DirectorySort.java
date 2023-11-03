import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Rafael Almeida
 * CSCI 2101 C
 * 10/03/2023
 * Project 3: Call to Order
 * 
 * Handles file reading, building main program data structure, and generating
 * the final output
 * 
 * @param fileName   input file name
 * @param NAME       this and the following parameters are used to avoid magic
 *                   numbers when parsing the file. I decided to define these
 *                   here because it is easier to access and change in case the
 *                   file structure were to ever change.
 * @param FIRST_NAME
 * @param LAST_NAME
 * @param ADDRESS
 * @param PHONE
 * @param EMAIL
 * @param sUBox
 */
public class DirectorySort {

    private static final String fileName = "directory.txt";
    // file structure
    private static final int NAME = 0;
    private static final int FIRST_NAME = 0;
    private static final int LAST_NAME = 1;
    private static final int ADDRESS = 1;
    private static final int PHONE = 2;
    private static final int EMAIL = 3;
    private static final int sUBox = 4;

    public static void main(String[] args) throws Exception {
        // creating list of Student objects that contain directory info
        SortableArrayList<Student> students = new SortableArrayList<>();
        // reading file and creating Student objects
        try {
            for (String line : Files.readAllLines(Path.of(fileName))) {
                String[] s = line.split(" \\| ");
                String[] sName = s[NAME].split(" ");
                students.add(new Student(sName[FIRST_NAME], sName[LAST_NAME],
                        s[ADDRESS], s[PHONE], s[EMAIL], Integer.valueOf(s[sUBox])));

            }
        } catch (IOException ioe) {
            System.out.printf("Could not read file '%s'", fileName);
            return;
        }

        // creating arrays to facilitate printing of output
        String[] questions = { "a", "b", "c", "d", "e", "f", "g" };
        // using .reversed() where appropriate
        Comparator[] comparators = {
                new SUBoxComparator(), new SUBoxComparator().reversed(),
                new LastNameComparator(), new LastNameComparator().reversed(),
                new VowelCountComparator().reversed(), new VowelCountComparator(),
                new PhoneDigitCountComparator().reversed()
        };

        // printing output
        // this setup allows me to just print the first student after every re-sort
        for (int i = 0; i < questions.length; i++) {
            students.sort(comparators[i]);
            System.out.printf("(%s) %s\n", questions[i], students.get(0));
        }
    }

    /**
     * Compares the suBox attribute of Student objects
     */
    public static class SUBoxComparator implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            return a.getsUBox() - b.getsUBox();
        }
    }

    /**
     * Compares the lastName attribute of Student objects
     */
    public static class LastNameComparator implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            return a.getLastName().compareTo(b.getLastName());
        }
    }

    /**
     * Compares the vowel frequency in the firstName and lastName attribute of
     * Student objects
     */
    public static class VowelCountComparator implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            return countVowels(a.getFirstName() + a.getLastName())
                    - countVowels(b.getFirstName() + b.getLastName());
        }

        /**
         * Counts vowel frequency by looping through all letters in name and checking if
         * they are in he vowels array. Converts name to lower case before checking
         * 
         * @param name
         * @return the frequency of vowels in name
         */
        private int countVowels(String name) {
            List<String> vowels = Arrays.asList("a", "e", "i", "o", "u");
            int count = 0;
            for (String letter : name.toLowerCase().split("")) {
                if (vowels.contains(letter)) {
                    count++;
                }
            }

            return count;
        }
    }

    /**
     * Compares the frequency of most frequent digit in the phone attribute of
     * Student objects
     */
    public static class PhoneDigitCountComparator implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            return frequencyOfMostFrequentDigit(a.getPhone())
                    - frequencyOfMostFrequentDigit(b.getPhone());
        }

        /**
         * Counts frequency of digits in phone by storing the frequency in an array
         * where each index corresponds to a digit
         * 
         * @param phone Student object phone attribute
         * @return the frequency of the most frequent digit in phone
         */
        private int frequencyOfMostFrequentDigit(String phone) {
            int[] frequencies = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            int max = 0;
            for (String digit : phone.split("")) {
                try {
                    int parsedDigit = Integer.parseInt(digit);
                    frequencies[parsedDigit]++;
                    max = Math.max(frequencies[parsedDigit], max);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            return max;
        }
    }

}
