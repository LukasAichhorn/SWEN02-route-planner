package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.mapAPI.MapAPI;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.DirectionServiceResponse;

public class DirectionServiceImpl implements DirectionService {
    private final MapAPI mapAPI;

    public DirectionServiceImpl(MapAPI mapAPI) {
        this.mapAPI = mapAPI;
    }

    @Override
    public DirectionServiceResponse queryDirection(String start, String end) {
        System.out.println("DirectionServiceImp -- start: " + start +" end: " + end);
        return mapAPI.queryDirection(start,end);
    }
}
