package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.businessLayer.FormValidationService;
import at.fh.tourplanner.businessLayer.FormValidationServiceImp;
import at.fh.tourplanner.listenerInterfaces.FormActionCreateListener;
import at.fh.tourplanner.listenerInterfaces.FormActionEditListener;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TourFormViewModel {
    private UUID tourUUID = null;
    private final StringProperty tourName = new SimpleStringProperty("");

    private final StringProperty start = new SimpleStringProperty("");

    private final StringProperty destination = new SimpleStringProperty("");

    private final StringProperty description = new SimpleStringProperty("");

    private final StringProperty tourDistance = new SimpleStringProperty("");

    private final StringProperty estimatedTime = new SimpleStringProperty("");

    private final List<FormActionCreateListener> createButtonListeners = new ArrayList<>();
    private final List<FormActionEditListener> editButtonListeners = new ArrayList<>();

    private final ObjectProperty<TransportType> selectedTransportType = new SimpleObjectProperty<>();

    private final ObservableList<TransportType> transportTypeObservableList = FXCollections.observableArrayList(TransportType.values());
    private final FormValidationService formValidationService;

    public TourFormViewModel() {
        this.formValidationService = new FormValidationServiceImp();
    }

    public UUID getTourUUID() {return tourUUID;}
    public StringProperty getTourName() { return tourName;}

    public StringProperty getStart() { return start;}

    public StringProperty getDestination() { return destination;}

    public StringProperty getDescription() { return description;}

    public StringProperty getTourDistance() { return tourDistance;}

    public StringProperty getEstimatedTime() { return estimatedTime;}


    public void clearForm(){
        tourUUID = null;
        tourName.set("");
        start.set("");
        destination.set("");
        description.set("");
        tourDistance.set("");
        estimatedTime.set("");
        selectedTransportType.setValue(null);
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
            selectedTransportType.setValue(tour.getTransportType());
            estimatedTime.set(tour.getEstimatedTime());
            tourDistance.set(tour.getDistance());

        }
        else {
            clearForm();
        }
    }

    public ObservableList<TransportType> getTransportTypes() {
        return this.transportTypeObservableList;
    }

    public Property<TransportType> getSelectedTransportType() {
        return this.selectedTransportType;
    }

    public void addNewTourAction(Tour tour) {
        if(formValidationService.noEmptyValues(tour)){
            System.out.println("adding " + tour );
            publishCreateButtonEvent(tour);
            clearForm();
        }
        else {
            System.out.println("error while creating new Tour ");
        };

    }

    public void handleNewTourMode() {
    }
}
