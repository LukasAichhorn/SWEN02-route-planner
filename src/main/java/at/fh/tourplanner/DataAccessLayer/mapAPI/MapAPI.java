package at.fh.tourplanner.DataAccessLayer.mapAPI;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.DirectionServiceResponse;

import java.awt.image.BufferedImage;

public interface MapAPI {
    DirectionServiceResponse queryDirection(String start, String end);
    BufferedImage queryRouteImage(String start, String end);
}
