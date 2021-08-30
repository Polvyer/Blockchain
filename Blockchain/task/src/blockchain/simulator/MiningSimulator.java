package blockchain.simulator;

import blockchain.client.Miner;
import blockchain.impl.Block;
import blockchain.impl.Blockchain;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static blockchain.util.BlockchainConstants.NUMBER_OF_MINERS;

public class MiningSimulator {

    private static MiningSimulator instance;

    private final Blockchain blockchain;

    private MiningSimulator(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public static MiningSimulator getInstance(Blockchain blockchain) {
        if (instance == null) {
            instance = new MiningSimulator(blockchain);
        }
        return instance;
    }

    public void startMiningBlocks(int numberOfBlocksToMine) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> IntStream.range(0, numberOfBlocksToMine)
                .forEach(i -> mineAndBroadcastBlock()));
        executorService.shutdown();
    }

    private void mineAndBroadcastBlock() {
        final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_MINERS);
        final List<Callable<Block>> miners = createMiners();
        try {
            final Block newBlock = executor.invokeAny(miners);
            broadcastBlock(newBlock);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException();
        } finally {
            executor.shutdownNow();
        }
    }

    private List<Callable<Block>> createMiners() {
        return IntStream.rangeClosed(1, NUMBER_OF_MINERS)
                .mapToObj(index -> new Miner(blockchain))
                .collect(Collectors.toList());
    }

    private void broadcastBlock(Block block) {
        blockchain.acceptBlock(block);
    }
}
