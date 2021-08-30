class ThreadUtil {
    static void printNameOfTerminatedThread(Thread[] threads) {
        for (Thread thread : threads) {
            if (thread.getState() == Thread.State.TERMINATED) {
                System.out.println(thread.getName());
            }
        }
    }
}