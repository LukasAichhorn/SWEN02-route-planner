package at.fh.tourplanner.model;

public class FormDataNewTour {
    private String tourName;
    private TransportType transportType;
    private String start;
    private String end;
    private String description;

    public FormDataNewTour(String tourName, TransportType transportType, String start, String end, String description) {
        this.tourName = tourName;
        this.transportType = transportType;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public String getTourName() {
        return tourName;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
