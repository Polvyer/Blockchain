import java.util.stream.*;

class QuadraticSum {
    public static long rangeQuadraticSum(int fromIncl, int toExcl) {
        return LongStream.range(fromIncl, toExcl).map(e -> e * e).reduce(0, (accum, elem) -> accum + elem);
    }
}