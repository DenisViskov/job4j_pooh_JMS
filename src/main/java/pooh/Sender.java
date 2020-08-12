package pooh;

/**
 * Interface of sender
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public interface Sender extends Runnable {
    /**
     * Method should execute task in dependency of given sender
     */
    void doJobs();
}
