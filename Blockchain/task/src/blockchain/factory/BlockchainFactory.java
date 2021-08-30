package blockchain.factory;

import blockchain.impl.Blockchain;
import blockchain.util.SerializationUtil;

import java.io.File;
import java.io.IOException;

import static blockchain.util.BlockchainConstants.BLOCKCHAIN_FILENAME;

public final class BlockchainFactory {

    private static BlockchainFactory instance;

    private BlockchainFactory() {
    }

    public static BlockchainFactory getInstance() {
        if (instance == null) {
            instance = new BlockchainFactory();
        }
        return instance;
    }

    public Blockchain orderBlockchain() {
        if (blockchainFileExists()) {
            return loadBlockchainFromFile();
        }

        return createBlockchain();
    }

    private boolean blockchainFileExists() {
        return new File(BLOCKCHAIN_FILENAME).exists();
    }

    private Blockchain loadBlockchainFromFile() {
        try {
            return (Blockchain) SerializationUtil.deserialize(BLOCKCHAIN_FILENAME);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize the blockchain from the file");
        }
    }

    private Blockchain createBlockchain() {
        return new Blockchain();
    }
}
