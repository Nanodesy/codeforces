import java.util.Scanner;

import static java.lang.Math.*;

public class StoneGame {
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

    public static int solveTask(int[] array) {
        int min = 0;
        int max = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[max] < array[i]) max = i;
            if (array[min] > array[i]) min = i;
        }

        int l = min(max, min);
        int r = max(max, min);

        int leftRightWay = (l + 1) + (array.length - r);
        int leftWay = r + 1;
        int rightWay = array.length - l;

        return min(leftRightWay, min(leftWay, rightWay));
    }
}