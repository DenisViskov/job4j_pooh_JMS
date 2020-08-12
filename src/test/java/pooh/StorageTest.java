package pooh;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class StorageTest {

    @Test
    public void getTest() throws InterruptedException {
        Store store = new Storage();
        Thread first = new Thread(() -> {
            store.add("Hi there");
            store.add("123");
            store.add("It's i am");
        });
        Thread second = new Thread(() -> {
            store.get();
            store.get();
        });
        first.start();
        second.start();
        first.join();
        second.join();
        String out = (String) store.get();
        boolean result = out.equals("Hi there")
                || out.equals("123")
                || out.equals("It's i am");
        assertThat(result, is(true));
    }
}