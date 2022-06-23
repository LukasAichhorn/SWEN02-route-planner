package at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Direction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionServiceResponse {
    @JsonProperty("route")
    private Direction route;

    public Direction getRoute() {
        return route;
    }
}
