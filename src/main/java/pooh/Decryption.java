package pooh;

/**
 * Interface of Decryption
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public interface Decryption {
    /**
     * Method should return JSON
     *
     * @return JSON
     */
    String getJson();

    /**
     * Method should return Mode
     *
     * @return mode
     */
    String getMode();

    /**
     * Method should return sender
     *
     * @return
     */
    String getSender();
}
