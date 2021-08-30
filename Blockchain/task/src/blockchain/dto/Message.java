package blockchain.dto;

import java.security.PublicKey;

public class Message {

    private final String messenger;
    private final String message;
    private byte[] signature;
    private final long uniqueIdentifier;
    private final PublicKey publicKey;

    public Message(String messenger, String message,
                   long uniqueIdentifier, PublicKey publicKey) {
        this.messenger = messenger;
        this.message = message;
        this.uniqueIdentifier = uniqueIdentifier;
        this.publicKey = publicKey;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getSignature() {
        return signature;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return String.format("%d. %s: %s", uniqueIdentifier, messenger, message);
    }
}
