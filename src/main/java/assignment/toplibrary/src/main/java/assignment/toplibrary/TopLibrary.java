package assignment.toplibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TopLibrary {


    private static final String GOOGLE_SEARCH_URL = "http://www.google.com/search?q=";

    public static void main(String... args) {
        if (args.length < 1) {
            System.err.println("No url to scan");
            System.out.println("Usage: java -jar toplibrary.jar <keyword>");
            System.exit(-1);
        }

        System.out.print("Type keyword : ");
        String keyword = "";
        System.out.println("Search in progress... Please, wait 1 min. ");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            keyword = reader.readLine();
        } catch (IOException e) {
            System.out.println("Failed to read keyword: " + e);
            System.exit(-1);
        }

        try {
            URL website = new URL(GOOGLE_SEARCH_URL + URLEncoder.encode(keyword, "UTF-8"));
            HttpURLConnection urlConnection = (HttpURLConnection) website.openConnection();
            urlConnection.setRequestProperty("User-Agent", Util.USER_AGENT);

            WorkerManager manager = new WorkerManager();
            manager.start(urlConnection.getInputStream());
        } catch (Exception e) {
            System.err.println("Unexpected exception in JavaScript library scanner: " + e);
            System.exit(-1);
        }
    }
}
