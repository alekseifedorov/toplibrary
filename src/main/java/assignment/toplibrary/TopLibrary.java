package assignment.toplibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopLibrary {

    private final static Logger logger = LoggerFactory.getLogger(TopLibrary.class);

    private static final String GOOGLE_SEARCH_URL = "http://www.google.com/search?q=";

    public static void main(String... args) {
        logger.info("Type search term : ");
        String keyword = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            keyword = reader.readLine();
        } catch (IOException e) {
            logger.error("Failed to read search term: " + e);
            System.exit(-1);
        }
        logger.info("Search in progress... Please, wait 1 min. ");
        try {
            URL website = new URL(GOOGLE_SEARCH_URL + URLEncoder.encode(keyword, "UTF-8"));
            HttpURLConnection urlConnection = (HttpURLConnection) website.openConnection();
            urlConnection.setRequestProperty("User-Agent", Util.USER_AGENT);

            // todo: create a ThreadPoolExecutor with appropriate parameters
            WorkerManager manager = new WorkerManager(Executors.newCachedThreadPool());
            manager.start(urlConnection.getInputStream());
        } catch (Exception e) {
            logger.error("Unexpected exception in JavaScript library scanner: " + e);
            System.exit(-1);
        }
    }
}
