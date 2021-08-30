package blockchain.util;

public final class BlockchainConstants {

    public final static String BLOCK_DEFAULT_DATA = "no messages";
    public final static String BLOCKCHAIN_FILENAME = "blockchain.data";
    public final static int INITIAL_ZEROS_BLOCK_HASH_MUST_START_WITH = 0;
    public final static int NUMBER_OF_MINERS = Runtime.getRuntime().availableProcessors();
    public final static int UPPER_BLOCK_CREATION_TIME_LIMIT_IN_SECONDS = 60;
    public final static int LOWER_BLOCK_CREATION_TIME_LIMIT_IN_SECONDS = 5;
    public final static int NUMBER_OF_BLOCKS_TO_MINE = 5;
    public final static int PERIOD_TO_SEND_MESSAGE_IN_MILLISECONDS = 100;
    public final static int KEY_LENGTH = 1024;
}
