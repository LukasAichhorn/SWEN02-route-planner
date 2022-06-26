package at.fh.tourplanner.model;

public enum TransportType {
    bicycle("bicycle"),
    pedestrian("pedestrian"),
    car("car"),
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
