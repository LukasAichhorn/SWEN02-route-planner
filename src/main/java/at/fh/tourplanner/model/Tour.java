package at.fh.tourplanner.model;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Tour {
    private UUID   uuid;
    private String name;
    private String start;
    private String destination;
    private String description;
    private TransportType transportType;
    private String distance;
    private String estimatedTime;
    private List<Log> logs;
    private Image tourImage;

    @Override
    public String toString() {
        return "Tour{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                ", transportType=" + transportType +
                ", distance='" + distance + '\'' +
                ", estimatedTime='" + estimatedTime + '\'' +
                ", logs=" + logs +
                '}';
    }

    public Tour(String name, String start, String destination, String description,
                TransportType transportType, String distance, String estimatedTime,
                List<Log> logs,Image tourImage) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.start = start;
        this.destination = destination;
        this.description = description;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.logs = logs;
        this.tourImage = tourImage;
    }

    public void setTourImage(Image tourImage) {
        this.tourImage = tourImage;
    }

    public Image getTourImage() {
        return tourImage;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public List<Log> getLogs() {
        return logs;
    }
}
