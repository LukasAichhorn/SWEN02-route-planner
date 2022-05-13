package at.fh.tourplanner.repositories;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class MapAPI {
    private static final MapAPI mapApi = new MapAPI();
    private final String apiKey = "AGCjhc68CSMlcIhD0GQG7ERF263ldMdT";
    private final String staticMapUrl = "https://www.mapquestapi.com/staticmap/v5/map?key=" + apiKey;
    private final String size = "@2x";
    private final String and = "&";
    private final String locations = "locations=";
    private final String to = "||";

    private MapAPI() {
    }

    public static MapAPI getInstance() {
        return mapApi;
    }

    private String buildStaticMapUrl(String start, String end) {
        return staticMapUrl + and + size + and + locations + and + "start=" + start + and + "end=" + end;
    }

    public void requestStaticMap(String start, String end) {
        try {
            saveImage(buildStaticMapUrl(start, end), "image.jpg");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

}
