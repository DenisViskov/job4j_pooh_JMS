package pooh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Class of Decryptor
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public class Decryptor implements Decryption {

    /**
     * Socket
     */
    private final Socket socket;

    /**
     * Content
     */
    private final String content;

    /**
     * JSON
     */
    private final String json;

    /**
     * Mode
     */
    private final String mode;

    /**
     * Who's Sender
     */
    private final String sender;

    public Decryptor(Socket socket) {
        this.socket = socket;
        this.content = decryptContentSocket();
        this.json = getJson();
        this.mode = getMode();
        this.sender = getSender();
    }

    /**
     * Method check json on null and return him
     *
     * @return JSON
     */
    @Override
    public String getJson() {
        if (json == null) {
            String[] splitLine = content.split("/");
            return splitLine[2];
        }
        return json;
    }

    /**
     * Method check Mode on null and return him
     *
     * @return
     */
    @Override
    public String getMode() {
        if (mode == null) {
            String[] splitLine = content.split("/");
            return splitLine[1];
        }
        return mode;
    }

    /**
     * Method check Sender on null and return him
     *
     * @return
     */
    @Override
    public String getSender() {
        if (sender == null) {
            String[] splitLine = content.split("/");
            return splitLine[0].equals("POST") ? "Publisher" : "Subscriber";
        }
        return sender;
    }

    /**
     * Method of decrypt content from socket
     *
     * @return content
     */
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
