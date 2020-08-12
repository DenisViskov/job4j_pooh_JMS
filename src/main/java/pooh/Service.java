package pooh;

import java.net.Socket;

/**
 * Interface of Service
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public interface Service<T, V> {

    /**
     * Method should return request from sender
     *
     * @param server
     * @return T
     */
    T getRequest(V server);

    /**
     * Method should do main logic
     *
     * @param decryption
     * @param from
     */
    void execute(Decryption decryption, T from);

    /**
     * Method should return sender in dependency on mode
     *
     * @param decryption
     * @param to
     * @return Sender
     */
    Sender getSenderByMode(Decryption decryption, T to);
}
