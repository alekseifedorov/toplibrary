package assignment.toplibrary;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class WorkerManagerTest {

    @Test
    public void shouldCallSubmit() throws IOException, InterruptedException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("results.html");
        ExecutorService executorService = mock(ExecutorService.class);
        WorkerManager manager = new WorkerManager(executorService);
        manager.start(is);
        verify(executorService, times(9)).submit(any(Worker.class));
    }
}
