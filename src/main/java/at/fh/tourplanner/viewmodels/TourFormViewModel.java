package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.listenerInterfaces.FormActionListener;
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
import java.util.List;

public class TourFormViewModel {

    //private List<FocusChangedListener> focusChangedListenerList = new ArrayList<FocusChangedListener>();

    private final StringProperty tourName = new SimpleStringProperty("");

    private final StringProperty start = new SimpleStringProperty("");

    private final StringProperty destination = new SimpleStringProperty("");

    private final StringProperty description = new SimpleStringProperty("");

    private List<FormActionListener> changeListeners = new ArrayList<>();

    public StringProperty getTourName() { return tourName;}

    public StringProperty getStart() { return start;}

    public StringProperty getDestination() { return destination;}

    public StringProperty getDescription() { return description;}

    public Tour getFormData(){
       Tour tour =  new Tour(tourName.get(), start.get(), destination.get(), description.get());
        clearForm();
        return tour;
    }

    public void clearForm(){
        tourName.set("");
        start.set("");
        destination.set("");
        description.set("");
    }


    public void addListener(FormActionListener formActionListener) {
        this.changeListeners.add(formActionListener);

    }

    public void publishFormButtonEvent(Tour tour) {
        for(var listener : changeListeners){
            listener.handleFormAction(tour);
        }
    }
}
