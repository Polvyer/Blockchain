import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Range implements Iterable<Long> {

    private long fromInclusive;
    private long toExclusive;

    public Range(long from, long to) {
        this.fromInclusive = from;
        this.toExclusive = to;
    }

    @Override
    public Iterator<Long> iterator() {
        List<Long> list = new ArrayList<>();
        for (Long i = fromInclusive; i < toExclusive; i++) {
            list.add(i);
        }
        return list.iterator();
    }
}