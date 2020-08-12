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
    private Queue<String> queue = new ConcurrentLinkedQueue<>(Arrays.asList("1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10"));
    private final PoohJms jms = new PoohJms(new Storage(queue));
    private final Decryption decryption = mock(Decryption.class);
    private final Socket socket = mock(Socket.class);

    @Test
    public void getRequestTest() throws IOException {
        ServerSocket serverSocket = Mockito.mock(ServerSocket.class);
        Socket expected = new Socket();
        when(serverSocket.accept()).thenReturn(expected);
        Socket out = new PoohJms(new Storage()).getRequest(serverSocket);
        assertThat(expected, is(out));
    }

    @Test
    public void whenTopicModeExecuteTest() throws IOException, InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Decryption decryption = mock(Decryption.class);
        Socket socket = mock(Socket.class);
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
        assertThat(queue.size(), is(10));
    }

    @Test
    public void whenQueueModeExecuteTest() throws IOException, InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Decryption decryption = mock(Decryption.class);
        Socket socket = mock(Socket.class);
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
        assertThat(queue.size(), is(8));
    }
}