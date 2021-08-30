package blockchain.client;

import blockchain.dto.Message;
import blockchain.impl.Blockchain;
import blockchain.util.GenerateKeys;
import blockchain.util.RandomUtil;

import java.security.*;
import java.util.List;
import java.util.TimerTask;

public class Messenger extends TimerTask {

    private final static List<String> LIST_OF_MESSAGES = List.of("Hey, I'm first!",
            "It's not fair!",
            "You always will be first because it is your blockchain!",
            "Anyway, thank you for this amazing chat.",
            "You're welcome :)",
            "Hey Tom, nice chat");

    private final String name;
    private final Blockchain blockchain;
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public Messenger (String name, Blockchain blockchain) {
        this.name = name;
        this.blockchain = blockchain;
        final KeyPair pair = GenerateKeys.createKeys();
        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
    }

    @Override
    public void run() {
        final Message message = createMessage();
        final byte[] signature = signMessage(message);
        message.setSignature(signature);
        sendMessage(message);
    }

    public Message createMessage() {
        final String message = getRandomMessage();
        final long uniqueIdentifier = blockchain.getUniqueId();
        return new Message(name, message, uniqueIdentifier, publicKey);
    }

    private synchronized static String getRandomMessage() {
        return RandomUtil.getRandomElementFromList(LIST_OF_MESSAGES);
    }

    public byte[] signMessage(Message message) {
        try {
            final Signature rsa = Signature.getInstance("SHA1withDSA", "SUN");
            rsa.initSign(privateKey);
            rsa.update(message.toString().getBytes());
            return rsa.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to sign message");
        }
    }

    public void sendMessage(Message message) {
        blockchain.addMessage(message);
    }
}
