package pooh;

/**
 * Класс реализует ...
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class Publisher implements Sender, Runnable {

    private final Store store;
    private final String json;

    public Publisher(Store store, String json) {
        this.store = store;
        this.json = json;
    }

    @Override
    public void doJobs() {
        store.add(json);
    }

    @Override
    public void run() {
        doJobs();
    }
}
