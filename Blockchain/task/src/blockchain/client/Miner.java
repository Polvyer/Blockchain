package blockchain.client;

import blockchain.impl.Blockchain;
import blockchain.impl.Block;
import blockchain.util.RandomUtil;
import blockchain.util.StopWatch;

import java.util.concurrent.Callable;

public final class Miner implements Callable<Block> {

    private final Blockchain blockchain;
    private final StopWatch stopWatch;

    public Miner(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.stopWatch = new StopWatch();
    }

    @Override
    public Block call() {
        stopWatch.start();
        final Block newBlock = mineBlock();
        stopWatch.stop();
        final long creationTimeInSeconds = stopWatch.getElapsedTimeInSeconds();
        newBlock.setCreationTimeInSeconds(creationTimeInSeconds);
        return newBlock;
    }

    private Block mineBlock() {
        final Block newBlock = createBlock();
        final int magicNumber = findMagicNumberForBlock(newBlock);
        newBlock.setMagicNumber(magicNumber);
        return newBlock;
    }

    private Block createBlock() {
        final String creator = getCreator();
        final long id = blockchain.getHeight();
        final String previousHash = blockchain.getPreviousHash();
        final String data = blockchain.getPendingMessages();
        return new Block(creator, id, previousHash, data);
    }

    private String getCreator() {
        final String name = Thread.currentThread().getName();
        return name.split("")[name.length() - 1];
    }

    private int findMagicNumberForBlock(Block block) {
        int magicNumber = getNewMagicNumber();
        while(!isInterrupted() && !magicNumberMakeBlockValid(magicNumber, block)) {
            magicNumber = getNewMagicNumber();
        }
        return magicNumber;
    }

    private int getNewMagicNumber() {
        return RandomUtil.getRandomNumber();
    }

    private boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }

    private boolean magicNumberMakeBlockValid(int magicNumber, Block block) {
        final String hash = block.generateHashUsingMagicNumber(magicNumber);
        final int zerosHashMustStartWith = blockchain.getZerosHashMustStartWith();
        final String validHashRegex = String.format("[0]{%d}\\w*", zerosHashMustStartWith);
        return hash.matches(validHashRegex);
    }
}
