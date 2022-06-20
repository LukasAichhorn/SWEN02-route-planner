package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.Direction;
import at.fh.tourplanner.DataAccessLayer.Route;

public interface DirectionService {
    Route queryDirection(String start, String end);
}
