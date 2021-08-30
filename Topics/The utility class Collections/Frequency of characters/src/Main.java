import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> chars = Arrays.asList(scanner.nextLine().split(" "));
        String character = scanner.nextLine();
        System.out.println(Collections.frequency(chars, character));
    }
}