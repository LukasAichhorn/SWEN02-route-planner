package at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Direction {
    @JsonProperty("distance")
    private int distance;
    @JsonProperty("formattedTime")
    private String  formattedTime;

    public Direction() {
    }


    public int getDistance() {
        return distance;
    }


    public String getFormattedTime() {
        return formattedTime;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "duration=" + distance +
                ", formattedTime='" + formattedTime + '\'' +
                '}';
    }
}
