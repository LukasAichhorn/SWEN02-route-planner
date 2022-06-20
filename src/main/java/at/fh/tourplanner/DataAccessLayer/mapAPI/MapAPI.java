package at.fh.tourplanner.DataAccessLayer.mapAPI;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Route;

public interface MapAPI {
    Route queryDirection(String start, String end);
}
