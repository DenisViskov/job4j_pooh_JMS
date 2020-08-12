package pooh;

import net.jcip.annotations.ThreadSafe;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class of storage
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
@ThreadSafe
public class Storage implements Store<String> {

    /**
     * Store
     */
    private final Queue<String> store;

    public Storage(Queue<String> store) {
        this.store = store;
    }

    public Storage() {
        this.store = new ConcurrentLinkedQueue<>();
    }

    /**
     * Method return content from queue
     *
     * @return string
     */
    @Override
    public String get() {
        return store.poll();
    }

    /**
     * Method put in queue given content
     *
     * @param some
     */
    @Override
    public void add(String some) {
        store.offer(some);
    }

    /**
     * Method of copy storage
     *
     * @return new Store
     */
    @Override
    public Store copyStore() {
        Queue<String> queue = new ConcurrentLinkedQueue<>(store);
        return new Storage(queue);
    }
}
