package assignment.toplibrary;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkerManagerTest {

    @Test
    public void shouldHaveMandatoryWeapons() throws IOException, InterruptedException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("results.html");
        WorkerManager manager = new WorkerManager();
        manager.start(is);
        assertThat(manager.getCounterByName());
        //assertThat(console.gameModel.weapons).contains(Paper.getInstance(), Rock.getInstance(), Scissors.getInstance());
    }
}
