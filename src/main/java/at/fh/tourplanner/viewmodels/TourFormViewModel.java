package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.listenerInterfaces.FormActionCreateListener;
import at.fh.tourplanner.listenerInterfaces.FormActionEditListener;
import at.fh.tourplanner.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TourFormViewModel {
    private UUID tourUUID = null;
    private final StringProperty tourName = new SimpleStringProperty("");

    private final StringProperty start = new SimpleStringProperty("");

    private final StringProperty destination = new SimpleStringProperty("");

    private final StringProperty description = new SimpleStringProperty("");

    private final List<FormActionCreateListener> createButtonListeners = new ArrayList<>();
    private final List<FormActionEditListener> editButtonListeners = new ArrayList<>();

    public UUID getTourUUID() {return tourUUID;}
    public StringProperty getTourName() { return tourName;}

    public StringProperty getStart() { return start;}

    public StringProperty getDestination() { return destination;}

    public StringProperty getDescription() { return description;}


    public void clearForm(){
        tourUUID = null;
        tourName.set("");
        start.set("");
        destination.set("");
        description.set("");
    }


    public void addCreateActionListener(FormActionCreateListener formActionCreateListener) {
        this.createButtonListeners.add(formActionCreateListener);
    }
    public void addEditActionListener(FormActionEditListener formActionEditListener) {
        this.editButtonListeners.add(formActionEditListener);
    }

    public void publishCreateButtonEvent(Tour tour) {
        for(var listener : createButtonListeners){
            listener.handleCreateAction(tour);
        }
    }
    public void publishEditButtonEvent(Tour tour) {
        for(var listener : editButtonListeners){
            listener.handleEditAction(tour);
        }
    }

    public void fillFormWithSelection(Tour tour) {
        if(tour != null) {
            tourUUID = tour.getUUID();
            tourName.set(tour.getName());
            start.set(tour.getStart());
            destination.set(tour.getDestination());
            description.set(tour.getDescription());
        }
        else {
            clearForm();
        }
    }
}
