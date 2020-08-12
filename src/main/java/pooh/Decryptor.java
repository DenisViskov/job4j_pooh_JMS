package pooh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringJoiner;

/**
 * Класс реализует ...
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class Decryptor implements Decryption<Sender> {

    private final Socket socket;
    private final String inSocket;
    private final String content;
    private final String mode;
    private final Sender sender;

    public Decryptor(Socket socket) {
        this.socket = socket;
        this.inSocket = decryptContentSocket();
        this.content = getContent();
        this.mode = getMode();
        this.sender = getSender();
    }

    @Override
    public String getContent() {
        if (content == null) {
            String[] splitLine = inSocket.split("/");
            return splitLine[2];
        }
        return content;
    }

    @Override
    public String getMode() {
        if (mode == null) {
            String[] splitLine = inSocket.split("/");
            return splitLine[1];
        }
        return mode;
    }

    @Override
    public Sender getSender() {
        if (sender == null) {
            String[] splitLine = inSocket.split("/");
            return splitLine[0].equals("POST") ? new Publisher() : new Subscriber();
        }
        return sender;
    }

    private String decryptContentSocket() {
        StringBuilder tmp = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            reader.lines()
                    .forEach(tmp::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp.toString();
    }
}
