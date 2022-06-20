package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.Direction;
import at.fh.tourplanner.DataAccessLayer.RemoteMapAPI;
import at.fh.tourplanner.DataAccessLayer.Route;

public class DirectionServiceImpl implements DirectionService {
    private final RemoteMapAPI remoteMapAPI;

    public DirectionServiceImpl(RemoteMapAPI remoteMapAPI) {
        this.remoteMapAPI = remoteMapAPI;
    }

    @Override
    public Route queryDirection(String start, String end) {
        return remoteMapAPI.queryDirection(start,end);
    }
}
