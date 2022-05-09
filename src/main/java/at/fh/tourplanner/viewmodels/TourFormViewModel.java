package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.enums.FormEventType;
import at.fh.tourplanner.listenerInterfaces.FormActionCreateListener;
import at.fh.tourplanner.listenerInterfaces.FormActionEditListener;
import at.fh.tourplanner.listenerInterfaces.FormActionListener;
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

    private List<FormActionListener> changeListeners = new ArrayList<>();

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


    public void addCreateListener(FormActionListener formActionListener) {
        this.changeListeners.add(formActionListener);
    }

    public void publishFormButtonEvent(FormEventType type, Tour tour) {
        for(var listener : changeListeners){
            if(listener instanceof FormActionCreateListener && type.equals(FormEventType.CREATE)){
                ((FormActionCreateListener) listener).handleCreateAction(tour);
            }
            if(listener instanceof FormActionEditListener && type.equals(FormEventType.EDIT)){
                ((FormActionEditListener) listener).handleEditAction(tour);
            }

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
