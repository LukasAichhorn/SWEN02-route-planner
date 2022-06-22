package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.mapAPI.MapAPI;
import at.fh.tourplanner.DataAccessLayer.mapAPI.RemoteMapAPI;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Route;

public class DirectionServiceImpl implements DirectionService {
    private final MapAPI mapAPI;

    public DirectionServiceImpl(MapAPI mapAPI) {
        this.mapAPI = mapAPI;
    }

    @Override
    public Route queryDirection(String start, String end) {
        System.out.println("DirectionServiceImp -- start: " + start +" end: " + end);
        return mapAPI.queryDirection(start,end);
    }
}
