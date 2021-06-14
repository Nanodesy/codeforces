import java.util.Scanner;

public class OmkarAndBadStory {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();

            for (int i = 0; i < testCases; i++) {
                int[] array = new int[scanner.nextInt()];

                for (int j = 0; j < array.length; j++) {
                    array[j] = scanner.nextInt();
                }

                System.out.println(solveTask(array));
            }
        }
    }

    /**
     * @param array array to check
     * @return if array is nice than returns String with answer "YES" and array contents, otherwise
     * returns "NO"
     */
    private static String solveTask(int[] array) {
        int maxNumber = 0;

        for (int number : array) {
            if (number < 0)
                return "NO";
            maxNumber = Math.max(maxNumber, number);
        }

        StringBuilder sb = new StringBuilder("YES\n");
        sb.append(maxNumber + 1).append("\n");
        for (int i = 0; i <= maxNumber; i++) {
            sb.append(i).append(" ");
        }
        return sb.toString().trim();
    }
}