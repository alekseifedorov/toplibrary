package assignment.toplibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

class Worker implements Callable<String> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
    public String call() throws IOException {
        try {
            return doCall();
        } catch(Exception e) {
            logger.error("Unexpected exception: " + e);
            return null;
        }
    }

    private String doCall() throws IOException {
        logger.info("Processing " + url);
        URL resultUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) resultUrl.openConnection();
        urlConnection.setRequestProperty("User-Agent", Util.USER_AGENT);
        String jsLibrary = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {

            // todo: it's better to use a web crawler e.g. jsoup
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
                    jsLibrary = s.substring(start + "src=\"".length(), end + 3);

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
        }
        return jsLibrary;
    }
}
