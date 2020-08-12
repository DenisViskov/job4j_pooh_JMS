package pooh;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс реализует ...
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class PoohJms implements Service<Socket, ServerSocket> {

    private final ExecutorService service = Executors
            .newFixedThreadPool(Runtime
                    .getRuntime()
                    .availableProcessors());
    private final Store store;

    public PoohJms(Store store) {
        this.store = store;
    }

    @Override
    public Socket getRequest(ServerSocket server) {
        Socket socket = null;
        try {
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    @Override
    public void execute(Decryption decryption, Socket socket) {
        Sender sender = getSenderByMode(decryption, socket);
        service.submit(sender);
    }

    private Sender getSenderByMode(Decryption decryption, Socket socket) {
        if (decryption.getMode().equals("queue")
                && decryption.getSender().equals("Subscriber")) {
            return new Subscriber(socket, store);
        }
        if (decryption.getMode().equals("TOPIC")
                && decryption.getSender().equals("Subscriber")) {
            return new Subscriber(socket, store.copyStore());
        }
        return new Publisher(store, decryption.getJson());
    }

    public static void main(String[] args) throws IOException {
        Service poo = new PoohJms(new Storage());
        ServerSocket server = new ServerSocket(9000, 0, InetAddress.getLocalHost());
        while (!server.isClosed()) {
            Socket socket = (Socket) poo.getRequest(server);
            Decryption decryption = new Decryptor(socket);
            poo.execute(decryption, socket);
        }
    }
}
