package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.DirectionServiceResponse;

public interface DirectionService {
    DirectionServiceResponse queryDirection(String start, String end);
}
