package pooh;

import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
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
    private final Queue<String> store = new ConcurrentLinkedQueue<>();

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
        Storage newStore = new Storage();
        Iterator<String> it = store.iterator();
        while (it.hasNext()) {
            newStore.add(it.next());
        }
        return newStore;
    }

    /**
     * Method return size queue
     *
     * @return size
     */
    @Override
    public int size() {
        return store.size();
    }
}
