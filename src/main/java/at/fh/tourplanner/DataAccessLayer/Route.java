package at.fh.tourplanner.DataAccessLayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {
    @JsonProperty("route")
    private Direction route;

    public Direction getRoute() {
        return route;
    }
}
