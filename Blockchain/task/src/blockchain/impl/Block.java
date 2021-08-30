package blockchain.impl;

import blockchain.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public final class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String creator;
    private final long id;
    private final long timestamp;
    private int magicNumber;
    private final String previousHash;
    private final String data;
    private long creationTimeInSeconds;

    public Block(String creator, long id, String previousHash, String data) {
        this.creator = creator;
        this.id = id;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.data = data;
    }

    public void print() {
        System.out.println(this);
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public void setCreationTimeInSeconds(long creationTimeInSeconds) {
        this.creationTimeInSeconds = creationTimeInSeconds;
    }

    public long getCreationTimeInSeconds() {
        return creationTimeInSeconds;
    }

    public String generateHash() {
        return generateHashUsingMagicNumber(magicNumber);
    }

    public String generateHashUsingMagicNumber(int magicNumber) {
        final StringBuilder fieldsOfBlock = new StringBuilder();
        fieldsOfBlock.append(id)
                .append(timestamp)
                .append(magicNumber)
                .append(previousHash)
                .append(data);
        return StringUtil.applySha256(fieldsOfBlock.toString());
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append("Block:")
                .append(System.lineSeparator())
                .append("Created by miner #")
                .append(creator)
                .append(System.lineSeparator())
                .append("Id: ")
                .append(id)
                .append(System.lineSeparator())
                .append("Timestamp: ")
                .append(timestamp)
                .append(System.lineSeparator())
                .append("Magic number: ")
                .append(magicNumber)
                .append(System.lineSeparator())
                .append("Hash of the previous block:")
                .append(System.lineSeparator())
                .append(previousHash)
                .append(System.lineSeparator())
                .append("Hash of the block:")
                .append(System.lineSeparator())
                .append(generateHash())
                .append(System.lineSeparator())
                .append("Block data: ")
                .append(data)
                .append(System.lineSeparator())
                .append("Block was generating for ")
                .append(creationTimeInSeconds)
                .append(creationTimeInSeconds == 1 ? " second" : " seconds");
        return stringBuilder.toString();
    }
}
