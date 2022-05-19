package at.fh.tourplanner.model;

public enum TransportType {
    BICYCLE("Bicycle"),
    PEDESTRIAN("Pedestrian"),
    ;

    private String type;

    TransportType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
