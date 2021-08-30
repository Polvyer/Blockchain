import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list1 = Arrays.asList(scanner.nextLine().split("\\s+"));
        List<String> list2 = Arrays.asList(scanner.nextLine().split("\\s+"));
        int startingIndex = Collections.indexOfSubList(list1, list2);
        int lastIndex = Collections.lastIndexOfSubList(list1, list2);
        System.out.println(startingIndex + " " + lastIndex);
    }
}