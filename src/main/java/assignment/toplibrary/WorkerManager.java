package assignment.toplibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkerManager {

    private static final String GOOGLE_RESULT_LINK_PREFIX = "href=\"/url?q=";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ConcurrentHashMap<String, Integer> counterByName = new ConcurrentHashMap<>();

    private ConcurrentHashMap<Integer, String> nameByHash = new ConcurrentHashMap<>();

    protected ExecutorService service;

    public WorkerManager(ExecutorService service) {
        this.service = service;
    }

    public void start(InputStream inputStream) throws InterruptedException, IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String s;
            while ((s = br.readLine()) != null) {
                int start = s.indexOf(GOOGLE_RESULT_LINK_PREFIX);
                if (start == -1) {
                    continue;
                }

                int end = s.indexOf("&amp;sa=", start);
                if (end == -1) {
                    continue;
                }

                String resultLink = s.substring(start + GOOGLE_RESULT_LINK_PREFIX.length(), end);
                resultLink = java.net.URLDecoder.decode(resultLink, "UTF-8");
                resultLink = resultLink.replaceAll("&amp;", "&");
                service.submit(new Worker(resultLink, counterByName, nameByHash));
            }

            service.awaitTermination(1, TimeUnit.MINUTES);

            counterByName.entrySet().stream()
                    .sorted(Comparator.comparing((Map.Entry e) -> (Integer) e.getValue()).reversed())
                    .limit(5)
                    .forEach(e -> logger.info(e.getKey() + " : " + e.getValue()));
        }
    }

    public Map<String, Integer> getCounterByName() {
        return new HashMap(counterByName);
    }
}
