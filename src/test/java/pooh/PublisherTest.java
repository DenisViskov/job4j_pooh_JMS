package pooh;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class PublisherTest {

    @Test
    public void doJobsTest() {
        Store store = new Storage();
        Publisher publisher = new Publisher(store, "Hi all");
        publisher.doJobs();
        assertThat(store.get(), is("Hi all"));
    }
}