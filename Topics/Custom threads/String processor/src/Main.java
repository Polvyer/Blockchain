import java.util.Scanner;

class StringProcessor extends Thread {

    final Scanner scanner = new Scanner(System.in); // use it to read string from the standard input

    @Override
    public void run() {
        while (true) {
            String str = scanner.nextLine();

            if (str.matches("[A-Z]*")) {
                System.out.println("FINISHED");
                break;
            }

            System.out.println(str.toUpperCase());
        }
    }
}