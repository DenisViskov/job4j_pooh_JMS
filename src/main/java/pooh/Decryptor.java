package pooh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Класс реализует ...
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class Decryptor implements Decryption {

    private final Socket socket;
    private final String content;
    private final String json;
    private final String mode;
    private final String sender;

    public Decryptor(Socket socket) {
        this.socket = socket;
        this.content = decryptContentSocket();
        this.json = getJson();
        this.mode = getMode();
        this.sender = getSender();
    }

    @Override
    public String getJson() {
        if (json == null) {
            String[] splitLine = content.split("/");
            return splitLine[2];
        }
        return json;
    }

    @Override
    public String getMode() {
        if (mode == null) {
            String[] splitLine = content.split("/");
            return splitLine[1];
        }
        return mode;
    }

    @Override
    public String getSender() {
        if (sender == null) {
            String[] splitLine = content.split("/");
            return splitLine[0].equals("POST") ? "Publisher" : "Subscriber";
        }
        return sender;
    }

    private String decryptContentSocket() {
        StringBuilder tmp = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines()
                    .forEach(tmp::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp.toString();
    }
}
