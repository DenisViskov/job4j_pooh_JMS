package pooh;

import java.net.Socket;

/**
 * Класс реализует ...
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class Subscriber implements Sender, Runnable {

    private Socket socket;
    private Store store;

    public Subscriber(Socket socket, Store store) {
        this.socket = socket;
        this.store = store;
    }

    public Subscriber() {
    }

    @Override
    public void doJobs() {

    }

    @Override
    public void run() {

    }
}
