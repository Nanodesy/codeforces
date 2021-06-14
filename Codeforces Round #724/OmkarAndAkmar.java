import java.math.BigInteger;
import java.util.Scanner;

public class OmkarAndAkmar {
    public static long MOD = 1000000007L;
    public static long[] factorial;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        initFactorial(n);

        int start = (n + 1) / 2;
        if (start % 2 == 1) start++;

        long result = 0;
        for (int x = start; x <= n; x += 2) {
            long emptyCells = 1;
            if (x != n) {
                emptyCells = choose(x, n - x);
                emptyCells = (emptyCells + choose(x - 1, n - x - 1)) % MOD;
            }
            long withEndingStates = (emptyCells * 2L) % MOD;
            long total = (withEndingStates * factorial[x]) % MOD;
            result = (result + total) % MOD;
        }
        System.out.println(result);
    }

    private static void initFactorial(int n) {
        factorial = new long[n + 1];
        factorial[0] = 1;
        for (int i = 1; i < factorial.length; i++) {
            factorial[i] = (factorial[i - 1] * i) % MOD;
        }
    }

    public static long choose(int a, int b) {
        BigInteger numerator = BigInteger.valueOf(factorial[a]);
        BigInteger denominator = BigInteger.valueOf(factorial[b])
                .multiply(BigInteger.valueOf(factorial[a - b]))
                .modInverse(BigInteger.valueOf(MOD));
        BigInteger result = numerator.multiply(denominator);
        return result.mod(BigInteger.valueOf(MOD)).longValue();
    }
}
