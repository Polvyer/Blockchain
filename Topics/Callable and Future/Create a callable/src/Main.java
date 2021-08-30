import java.util.Scanner;
import java.util.concurrent.Callable;

class CallableUtil {
    public static Callable<String> getCallable() {
        String input = new Scanner(System.in).nextLine();
        Callable<String> callable = new Callable<>() {

            @Override
            public String call() throws Exception {
                return input;
            }
        };
        return callable;
    }
}