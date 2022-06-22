package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Route;

public interface DirectionService {
    Route queryDirection(String start, String end);
}
