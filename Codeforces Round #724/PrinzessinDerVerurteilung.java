import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class PrinzessinDerVerurteilung {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int testCases = Integer.parseInt(reader.readLine());

            for (int i = 0; i < testCases; i++) {
                reader.readLine();
                System.out.println(solveTask(reader.readLine())); // String length less than 1000
            }
        }
    }


    /**
     * For this solution, we will search for substrings of no more than 3 characters, since based on
     * the Pigeonhole principle, having an input string length of less than 1000 characters, the
     * number of variants of a three-character string will be less than the number of substring
     * variations possible in a string of 1000 characters.
     * @param s length should be less than 1000;
     * @return the smallest lexicographic sequence that is not present in string <i>s</i>
     */
    private static String solveTask(String s) {
        HashSet<String> substrings = new HashSet<>(1000);
        for (int len = 1; len <= 3; len++) {
            for (int i = 0; i < s.length() - len + 1; i++) {
                substrings.add(s.substring(i, i + len));
            }

            String stringToFind = findString(substrings, len);
            if (stringToFind != null) return stringToFind;
        }

        throw new IllegalArgumentException("Input string should be less than 1000 characters");
    }

    private static String findString(HashSet<String> substrings, int len) {
        if (len == 1) {
            for (char a = 'a'; a <= 'z'; a++) {
                String stringToFind = String.valueOf(a);
                if (!substrings.contains(stringToFind)) {
                    return stringToFind;
                }
            }
        }

        if (len == 2) {
            for (char a = 'a'; a <= 'z'; a++) {
                for (char b = 'a'; b <= 'z'; b++) {
                    String stringToFind = a + "" + b;
                    if (!substrings.contains(stringToFind)) {
                        return stringToFind;
                    }
                }
            }
        }

        if (len == 3) {
            for (char a = 'a'; a <= 'z'; a++) {
                for (char b = 'a'; b <= 'z'; b++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        String stringToFind = a + "" + b + "" + c;
                        if (!substrings.contains(stringToFind)) {
                            return stringToFind;
                        }
                    }
                }
            }
        }

        return null;
    }
}
