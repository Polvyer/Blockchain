package blockchain.util;

import java.util.List;
import java.util.Random;

public final class RandomUtil {

    private final static Random generator = new Random();

    public static int getRandomNumber() {
        return generator.nextInt();
    }

    public static <T> T getRandomElementFromList(List<T> list) {
        final int bound = list.size();
        final int randomIndex = getRandomNumberBetweenZeroAndBound(bound);
        return list.get(randomIndex);
    }

    private static int getRandomNumberBetweenZeroAndBound(int bound) {
        return generator.nextInt(bound);
    }
}
