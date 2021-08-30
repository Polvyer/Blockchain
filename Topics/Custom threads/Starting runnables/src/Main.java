class Starter {

    public static void startRunnables(Runnable[] runnables) {
        for (Runnable r : runnables) {
            new Thread(r).start();
        }
    }
}