import java.util.Arrays;
import java.util.Scanner;

public class FriendsAndCandies {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();

        for (int i = 0; i < testCases; i++) {
            int[] array = new int[scanner.nextInt()];

            for (int j = 0; j < array.length; j++) {
                array[j] = scanner.nextInt();
            }

            System.out.println(solveTask(array));
        }
    }

    private static int solveTask(int[] array) {
        double median = Arrays.stream(array).average().orElseThrow();

        if (Math.ceil(median) != median) {
            return -1;
        }

        int counter = 0;
        for (int n : array) {
            if (n > median)
                counter++;
        }

        return counter;
    }
}
