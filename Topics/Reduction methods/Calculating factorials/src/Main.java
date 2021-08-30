import java.util.Scanner;
import java.util.stream.*;

class Main {

    /**
     * Calculates the factorial of the given number n
     *
     * @param n >= 0
     *
     * @return factorial value
     */
    public static long factorialRecursive(long n) {
        if (n == 0) {
            return 1;
        }

        return n * factorialRecursive(n - 1);
    }

    public static long factorial(long n) {
        if (n == 0) {
            return 1;
        }

        return LongStream.rangeClosed(1, n).reduce((accum, elem) -> accum * elem).getAsLong();
    }

    // Don't change the code below
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        long n = Integer.parseInt(scanner.nextLine().trim());

        System.out.println(factorial(n));
    }
}