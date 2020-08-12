package pooh;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class of poo JMS has main logic
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class PoohJms implements Service<Socket, ServerSocket> {

    /**
     * Executor service
     */
    private final ExecutorService service = Executors
            .newFixedThreadPool(Runtime
                    .getRuntime()
                    .availableProcessors());

    /**
     * Store
     */
    private final Store store;

    public PoohJms(Store store) {
        this.store = store;
    }

    /**
     * Method returns socket from client
     *
     * @param server
     * @return socket
     */
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

    /**
     * Method execute tasks
     *
     * @param decryption
     * @param socket
     */
    @Override
    public void execute(Decryption decryption, Socket socket) {
        Sender sender = getSenderByMode(decryption, socket);
        service.submit(sender);
    }

    /**
     * Method returns Sender by given mode from request
     *
     * @param decryption
     * @param socket
     * @return Sender
     */
    @Override
    public Sender getSenderByMode(Decryption decryption, Socket socket) {
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
            socket.getOutputStream().write(("GET /ip HTTP/1.0\n"
                    .getBytes(StandardCharsets.US_ASCII)));
            socket.getOutputStream().flush();
            Decryption decryption = new Decryptor(socket);
            poo.execute(decryption, socket);
        }
    }
}
