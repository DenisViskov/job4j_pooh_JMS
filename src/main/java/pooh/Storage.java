package pooh;

import net.jcip.annotations.ThreadSafe;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Класс реализует ...
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
@ThreadSafe
public class Storage implements Store<String> {

    private final Queue<String> store;

    public Storage(Queue<String> store) {
        this.store = store;
    }

    public Storage() {
        this.store = new ConcurrentLinkedQueue<>();
    }

    @Override
    public String get() {
        return store.poll();
    }

    @Override
    public void add(String some) {
        store.offer(some);
    }

    @Override
    public Store copyStore() {
        Queue<String> queue = new ConcurrentLinkedQueue<>(store);
        return new Storage(queue);
    }
}
