package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.mapAPI.RemoteMapAPI;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Route;

public class DirectionServiceImpl implements DirectionService {
    private final RemoteMapAPI remoteMapAPI;

    public DirectionServiceImpl(RemoteMapAPI remoteMapAPI) {
        this.remoteMapAPI = remoteMapAPI;
    }

    @Override
    public Route queryDirection(String start, String end) {
        System.out.println("DirectionServiceImp -- start: " + start +" end: " + end);
        return remoteMapAPI.queryDirection(start,end);
    }
}
