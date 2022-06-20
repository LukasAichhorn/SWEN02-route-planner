package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StaticTourInfoViewModel {
    private final StringProperty tourName = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty destination = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty tourDistance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");
    private final StringProperty selectedTransportType = new SimpleStringProperty("");

    public StaticTourInfoViewModel() {
    }

    public StringProperty getTourName() { return tourName;}

    public StringProperty getStart() { return start;}

    public StringProperty getDestination() { return destination;}

    public StringProperty getDescription() { return description;}

    public StringProperty getTourDistance() { return tourDistance;}

    public StringProperty getEstimatedTime() { return estimatedTime;}
    public StringProperty getSelectedTransportType() { return selectedTransportType;}

    public void fillTourInfo(Tour tour) {
        if(tour == null){
            System.out.println("fillTourInfo was called with an Empty Tour");
            return;
        }
        start.set(tour.getStart());
        destination.set(tour.getDestination());
        description.set(tour.getDescription());
        selectedTransportType.setValue(tour.getTransportType().toString());
        estimatedTime.set(tour.getEstimatedTime());
        tourDistance.set(tour.getDistance());



    }
}

