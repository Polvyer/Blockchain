import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        while (scanner.hasNext()) {
            int number = scanner.nextInt();
            executor.submit(new PrintIfPrimeTask(number));
        }

        executor.shutdown();
    }
}

class PrintIfPrimeTask implements Runnable {

    private final int number;

    public PrintIfPrimeTask(int number) {
        this.number = number;
    }

    public boolean isPrimeNumber() {

        if ((number <= 1)) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        if ((number % 2) == 0) {
            return false;
        }

        for (int i = 3; i < (number / 2); i+=2) {
            if ((number % i) == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void run() {
        if (isPrimeNumber()) {
            System.out.println(number);
        }
    }
}