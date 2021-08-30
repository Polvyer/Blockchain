import java.util.*;
import java.util.function.*;
import java.util.stream.*;


class MinMax {

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {

        // your implementation here
        ArrayDeque<T> arrayDeque = stream.sorted(order).collect(Collectors.toCollection(ArrayDeque::new));

        if (arrayDeque.isEmpty()) {
            minMaxConsumer.accept(null, null);
        } else {
            minMaxConsumer.accept(arrayDeque.getFirst(), arrayDeque.getLast());
        }
    }
}