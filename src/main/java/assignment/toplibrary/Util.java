package assignment.toplibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {
    public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7";

    public static String download(String url) throws IOException {
        URL website = new URL(url);
        HttpURLConnection myURLConnection = (HttpURLConnection) website.openConnection();
        myURLConnection.setRequestProperty("User-Agent", Util.USER_AGENT);
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()))) {
            String s;
            while ((s = br.readLine()) != null) {
                result.append(s);
            }
        }

        return result.toString();
    }
}
