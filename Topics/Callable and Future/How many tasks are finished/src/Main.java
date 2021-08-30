import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


class FutureUtils {

    public static int howManyIsDone(List<Future> items) {
        return (int) items.stream()
                .filter(Future::isDone)
                .count();
    }

}