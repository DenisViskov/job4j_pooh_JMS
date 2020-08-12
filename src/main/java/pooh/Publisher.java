package pooh;

/**
 * Class of publisher
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class Publisher implements Sender {

    /**
     * Store
     */
    private final Store store;

    /**
     * JSON
     */
    private final String json;

    public Publisher(Store store, String json) {
        this.store = store;
        this.json = json;
    }

    /**
     * Method execute task
     */
    @Override
    public void doJobs() {
        store.add(json);
    }

    @Override
    public void run() {
        doJobs();
    }
}
