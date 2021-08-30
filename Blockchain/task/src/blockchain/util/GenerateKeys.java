package blockchain.util;

import java.security.*;

import static blockchain.util.BlockchainConstants.KEY_LENGTH;

public class GenerateKeys {

    private static KeyPairGenerator keyGen;

    static {
        try {
            keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            final SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(KEY_LENGTH, random);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException();
        }
    }

    public static KeyPair createKeys() {
        return keyGen.generateKeyPair();
    }
}
