package at.fh.tourplanner.businessLayer.mapApiService;

import at.fh.tourplanner.DataAccessLayer.mapAPI.MapAPI;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.DirectionServiceResponse;
import at.fh.tourplanner.businessLayer.mapApiService.DirectionService;

public class DirectionServiceImpl implements DirectionService {
    private final MapAPI mapAPI;

    public DirectionServiceImpl(MapAPI mapAPI) {
        this.mapAPI = mapAPI;
    }

    @Override
    public DirectionServiceResponse queryDirection(String start, String end) {
        return mapAPI.queryDirection(start,end);
    }
}
