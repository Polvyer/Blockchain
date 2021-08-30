package blockchain.util;

import java.util.concurrent.TimeUnit;

public final class StopWatch {

    private long startTimeInNanoseconds;
    private long stopTimeInNanoseconds;
    private boolean running;

    public StopWatch() {
        this.running = false;
    }

    public void start() {
        startTimeInNanoseconds = getTimeInNanoseconds();
        setRunning(true);
    }

    public void stop() {
        stopTimeInNanoseconds = getTimeInNanoseconds();
        setRunning(false);
    }

    private void setRunning(boolean running) {
        this.running = running;
    }

    public long getElapsedTimeInSeconds() {
        if (running) {
            final long creationTimeInNanoseconds = getTimeInNanoseconds() - startTimeInNanoseconds;
            return convertNanosecondsToSeconds(creationTimeInNanoseconds);
        }

        final long creationTimeInNanoseconds = stopTimeInNanoseconds - startTimeInNanoseconds;
        return convertNanosecondsToSeconds(creationTimeInNanoseconds);
    }

    private long getTimeInNanoseconds() {
        return System.nanoTime();
    }

    private synchronized static long convertNanosecondsToSeconds(Long timeInNanoseconds) {
        return TimeUnit.SECONDS
                .convert(timeInNanoseconds, TimeUnit.NANOSECONDS);
    }
}
