package blockchain.ui;

import blockchain.factory.BlockchainFactory;
import blockchain.impl.Blockchain;
import blockchain.simulator.ChatSimulator;
import blockchain.simulator.MiningSimulator;

import static blockchain.util.BlockchainConstants.NUMBER_OF_BLOCKS_TO_MINE;

public final class UserInterface {

    public static void start() throws InterruptedException {
        final Blockchain blockchain = BlockchainFactory.getInstance().orderBlockchain();

        if (blockchain.isEmpty()) {
            final MiningSimulator miningSimulator = MiningSimulator.getInstance(blockchain);
            miningSimulator.startMiningBlocks(NUMBER_OF_BLOCKS_TO_MINE);
            final ChatSimulator chatSimulator = ChatSimulator.getInstance(blockchain);
            chatSimulator.startSendingMessages();
            Thread.sleep(10000);
            chatSimulator.stopSendingMessages();
        } else {
            blockchain.print();
        }
    }
}
