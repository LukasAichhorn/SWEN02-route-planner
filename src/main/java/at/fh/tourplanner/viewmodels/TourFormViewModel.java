package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.repositories.TourRepository;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TourFormViewModel {

    //private List<FocusChangedListener> focusChangedListenerList = new ArrayList<FocusChangedListener>();

    private final StringProperty tourName = new SimpleStringProperty("");

    private final StringProperty start = new SimpleStringProperty("");

    private final StringProperty destination = new SimpleStringProperty("");

    private final StringProperty description = new SimpleStringProperty("");

    //private final ObservableList<Tour> data = FXCollections.observableArrayList();

    public StringProperty getTourName() { return tourName;}

    public StringProperty getStart() { return start;}

    public StringProperty getDestination() { return destination;}

    public StringProperty getDescription() { return description;}

    public void saveTourToList(){
        TourRepository.getInstance().create(new Tour(tourName.get(), start.get(), destination.get(), description.get()));
        clearForm();
    }

    public void clearForm(){
        tourName.set("");
        start.set("");
        destination.set("");
        description.set("");
    }



}
