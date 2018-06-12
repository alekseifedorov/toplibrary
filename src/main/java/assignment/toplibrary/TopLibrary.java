package assignment.toplibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executors;

public class TopLibrary {

    private static final String GOOGLE_SEARCH_URL = "http://www.google.com/search?q=";

    public static void main(String... args) {
        System.out.println("Type search term : ");
        String keyword = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            keyword = reader.readLine();
        } catch (IOException e) {
            System.out.println("Failed to read search term: " + e);
            System.exit(-1);
        }
        System.out.println("Search in progress... Please, wait 1 min. ");
        try {
            URL website = new URL(GOOGLE_SEARCH_URL + URLEncoder.encode(keyword, "UTF-8"));
            HttpURLConnection urlConnection = (HttpURLConnection) website.openConnection();
            urlConnection.setRequestProperty("User-Agent", Util.USER_AGENT);

            // todo: create a ThreadPoolExecutor with appropriate parameters
            WorkerManager manager = new WorkerManager(Executors.newCachedThreadPool());
            manager.start(urlConnection.getInputStream());
        } catch (Exception e) {
            System.out.println("Unexpected exception in JavaScript library scanner: " + e);
            System.exit(-1);
        }
    }
}
