package assignment.toplibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

class Worker implements Runnable {

    // google result url
    private String url;

    // number of JS references
    private Map<String, Integer> countersByName;

    // hash of JS files
    private Map<Integer, String> nameByHash;

    public Worker(String url, Map<String, Integer> countersByName, Map<Integer, String> nameByHash) {
        this.url = url;
        this.countersByName = countersByName;
        this.nameByHash = nameByHash;
    }

    @Override
    public void run() {
        URL website = null;
        try {
            website = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        HttpURLConnection myURLConnection = null;
        try {
            myURLConnection = (HttpURLConnection) website.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        myURLConnection.setRequestProperty("User-Agent", Util.USER_AGENT);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()))) {

            String s;
            int start = 0;
            while ((s = br.readLine()) != null) {
                while (true) {
                    start = s.indexOf("src=\"", start);
                    if (start == -1) {
                        break;
                    }

                    int end = s.indexOf(".js\">", start);
                    if (end == -1) {
                        break;
                    }
                    String jsLibrary = s.substring(start + "src=\"".length(), end + 3);

                    try {
                        String content = Util.download(url + '/' + jsLibrary);
                        String prevJsLibrary = nameByHash.putIfAbsent(content.hashCode(), jsLibrary);
                        countersByName.compute(Optional.ofNullable(prevJsLibrary).orElse(jsLibrary), (key, prev) -> prev == null ? 1 : prev + 1);
                    } catch (IOException e) {
                        countersByName.compute(jsLibrary, (key, prev) -> prev == null ? 1 : prev + 1);
                    }

                    start = end;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
