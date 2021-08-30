package blockchain.simulator;

import blockchain.client.Messenger;
import blockchain.impl.Blockchain;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static blockchain.util.BlockchainConstants.PERIOD_TO_SEND_MESSAGE_IN_MILLISECONDS;

public class ChatSimulator {

    private static ChatSimulator instance;
    private final static List<String> LIST_OF_NAMES = List.of("Tom", "Nick", "Sarah");

    private final Timer timer;
    private final Blockchain blockchain;

    private ChatSimulator(Blockchain blockchain) {
        this.timer = new Timer();
        this.blockchain = blockchain;
    }

    public static ChatSimulator getInstance(Blockchain blockchain) {
        if (instance == null) {
            instance = new ChatSimulator(blockchain);
        }
        return instance;
    }

    public void stopSendingMessages() {
        timer.cancel();
    }

    public void startSendingMessages() {
        for (String name : LIST_OF_NAMES) {
            startMessenger(name);
        }
    }

    private void startMessenger(String name) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            final TimerTask messenger = new Messenger(name, blockchain);
            timer.schedule(messenger, 0, PERIOD_TO_SEND_MESSAGE_IN_MILLISECONDS);
        });
        executorService.shutdown();
    }
}
