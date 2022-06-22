package at.fh.tourplanner.DataAccessLayer.mapAPI;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Route;
import javafx.scene.image.Image;
import okhttp3.ResponseBody;

import java.awt.image.BufferedImage;

public interface MapAPI {
    Route queryDirection(String start, String end);
    BufferedImage queryRouteImage(String start, String end);
}
