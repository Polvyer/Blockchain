package blockchain.impl;

import blockchain.dto.Message;
import blockchain.util.SerializationUtil;

import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static blockchain.util.BlockchainConstants.*;

public final class Blockchain implements Serializable {

    private static final long serialVersionUID = 1L;

    private long nextAvailableId = 1;

    private final List<Block> blocks;
    private int zerosHashMustStartWith;
    private final Queue<Message> messages;
    private volatile String pendingMessages;

    public Blockchain() {
        blocks = new ArrayList<>();
        zerosHashMustStartWith = INITIAL_ZEROS_BLOCK_HASH_MUST_START_WITH;
        pendingMessages = BLOCK_DEFAULT_DATA;
        messages = new ConcurrentLinkedQueue<>();
        pendingMessages = BLOCK_DEFAULT_DATA;
    }

    public void print() {
        System.out.println(this);
    }

    public synchronized long getUniqueId() {
        return nextAvailableId++;
    }

    public synchronized long getHeight() {
        return size() + 1;
    }

    public void saveBlockchainToFile() {
        try {
            SerializationUtil.serialize(this, BLOCKCHAIN_FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getZerosHashMustStartWith() {
        return zerosHashMustStartWith;
    }

    public String getPendingMessages() {
        return pendingMessages;
    }

    public synchronized void addMessage(Message message) {
        if (!isValidMessage(message)) {
            throw new RuntimeException("Message is invalid");
        }

        messages.add(message);
    }

    private boolean isValidMessage(Message message) {
        try {
            final Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            final PublicKey messagePublicKey = message.getPublicKey();
            final String messageData = message.toString();
            final byte[] messageSignature = message.getSignature();
            sig.initVerify(messagePublicKey);
            sig.update(messageData.getBytes());
            return sig.verify(messageSignature);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException();
        }
    }

    public void acceptBlock(Block block) {
        try {
            addBlock(block);
            block.print();
            adjustZerosHashMustStartWith();
            updatePendingMessages();
            clearMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBlock(Block block) throws Exception {
        if (!isValidBlock(block)) {
            throw new Exception("Block is invalid");
        }

        blocks.add(block);
    }

    private boolean isValidBlock(Block block) {
        final String blockPreviousHash = block.getPreviousHash();
        return blockPreviousHash.equals(getPreviousHash());
    }

    public synchronized String getPreviousHash() {
        return isEmpty()
                ? "0"
                : getLastBlock().generateHash();
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    private void adjustZerosHashMustStartWith() {
        final Block lastBlock = getLastBlock();
        final long lastBlockCreationTimeInSeconds = lastBlock.getCreationTimeInSeconds();
        if (lastBlockCreationTimeInSeconds > UPPER_BLOCK_CREATION_TIME_LIMIT_IN_SECONDS) {
            decrementZerosHashMustStartWith();
        } else if (lastBlockCreationTimeInSeconds < LOWER_BLOCK_CREATION_TIME_LIMIT_IN_SECONDS) {
            incrementZerosHashMustStartWith();
        } else {
            System.out.println("N stays the same");
        }
    }

    private Block getLastBlock() {
        return blocks.get(size() - 1);
    }

    private int size() {
        return blocks.size();
    }

    private void incrementZerosHashMustStartWith() {
        zerosHashMustStartWith += 1;
        System.out.println("N was increased to " + zerosHashMustStartWith);
    }

    private void decrementZerosHashMustStartWith() {
        zerosHashMustStartWith -= 1;
        System.out.println("N was decreased to " + zerosHashMustStartWith);
    }

    private void updatePendingMessages() {
        final String newPendingMessages = getAllMessagesAsString();
        setPendingMessages(newPendingMessages);
    }

    private String getAllMessagesAsString() {
        if (!hasMessages()) {
            return BLOCK_DEFAULT_DATA;
        }

        final StringBuilder sb = new StringBuilder();
        messages.forEach(message -> {
            sb.append(System.lineSeparator());
            sb.append(message.toString());
        });

        return sb.toString();
    }

    private boolean hasMessages() {
        return !messages.isEmpty();
    }

    private void setPendingMessages(String pendingMessages) {
        this.pendingMessages = pendingMessages;
    }

    private void clearMessages() {
        messages.clear();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator());

        for (final Block block : blocks) {
            stringBuilder.append(block)
                    .append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}
