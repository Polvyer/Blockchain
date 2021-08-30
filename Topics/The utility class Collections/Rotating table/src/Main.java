import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        scanner.nextLine();

        List<String> table = new ArrayList<>(rows);

        for (int i = 0; i < rows; i++) {
            table.add(scanner.nextLine());
        }
        int distanceForRotating = scanner.nextInt();

        Collections.rotate(table, distanceForRotating);
        table.forEach(System.out::println);
    }
}