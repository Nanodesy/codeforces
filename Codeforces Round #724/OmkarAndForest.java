import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class OmkarAndForest {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(reader.readLine());
        int T = Integer.parseInt(st.nextToken());
        while (T-- > 0) {
            st = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            long counter = 0;
            for (int i = 0; i < n; i++) {
                String s = reader.readLine();
                for (char c : s.toCharArray()) {
                    if (c == '#') counter++;
                }
            }

            long result = modPow2(counter);
            if ((long) n * m == counter) {
                System.out.println(result - 1);
            } else {
                System.out.println(result);
            }
        }
    }

    private static long modPow2(long exponent) {
        long result = 1;
        while (exponent-- > 0) {
            result = (result * 2) % 1_000_000_007L;
        }
        return result;
    }
}
