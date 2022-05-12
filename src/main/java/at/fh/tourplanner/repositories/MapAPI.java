package at.fh.tourplanner.repositories;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
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
            URL url = new URL(buildStaticMapUrl(start, end));
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            var respCode = con.getResponseCode();
            System.out.println("Static Map request resulted in " + respCode);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
