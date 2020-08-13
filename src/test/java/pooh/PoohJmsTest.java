package pooh;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.ranges.Range;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PoohJmsTest {

    private final Store store = new Storage();
    private final PoohJms jms = new PoohJms(store);
    private final Decryption decryption = mock(Decryption.class);
    private final Socket socket = mock(Socket.class);

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 10; i++) {
            store.add("Hello");
        }
    }

    @Test
    public void getRequestTest() throws IOException {
        ServerSocket serverSocket = Mockito.mock(ServerSocket.class);
        Socket expected = new Socket();
        when(serverSocket.accept()).thenReturn(expected);
        Socket out = new PoohJms(new Storage()).getRequest(serverSocket);
        assertThat(expected, is(out));
    }

    @Test
    public void whenTopicModeExecuteTest() throws InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thread first = new Thread(() -> {
            when(decryption.getMode()).thenReturn("TOPIC");
            when(decryption.getSender()).thenReturn("Subscriber");
            try {
                when(socket.getOutputStream()).thenReturn(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            jms.execute(decryption, socket);
        });
        Thread second = new Thread(() -> {
            when(decryption.getMode()).thenReturn("TOPIC");
            when(decryption.getSender()).thenReturn("Subscriber");
            try {
                when(socket.getOutputStream()).thenReturn(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            jms.execute(decryption, socket);
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(store.size(), is(10));
    }

    @Test
    public void whenQueueModeExecuteTest() throws InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thread first = new Thread(() -> {
            when(decryption.getMode()).thenReturn("queue");
            when(decryption.getSender()).thenReturn("Subscriber");
            try {
                when(socket.getOutputStream()).thenReturn(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            jms.execute(decryption, socket);
        });
        Thread second = new Thread(() -> {
            when(decryption.getMode()).thenReturn("queue");
            when(decryption.getSender()).thenReturn("Subscriber");
            try {
                when(socket.getOutputStream()).thenReturn(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            jms.execute(decryption, socket);
        });
        first.start();
        second.start();
        first.join();
        second.join();
        Thread.sleep(500);
        assertThat(store.size(), is(8));
    }
}