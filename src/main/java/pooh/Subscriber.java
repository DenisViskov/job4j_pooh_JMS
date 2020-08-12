package pooh;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Class of subscriber
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class Subscriber implements Sender {

    /**
     * Socket
     */
    private final Socket socket;

    /**
     * Store
     */
    private final Store store;

    public Subscriber(Socket socket, Store store) {
        this.socket = socket;
        this.store = store;
    }

    /**
     * Method getting content and sending him to client
     */
    @Override
    public void doJobs() {
        String content = (String) store.get();
        try (BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())) {
            out.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doJobs();
    }
}
