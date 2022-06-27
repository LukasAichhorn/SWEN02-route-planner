package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.ControllerFactory;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.DirectionServiceResponse;
import at.fh.tourplanner.Main;
import at.fh.tourplanner.businessLayer.*;
import at.fh.tourplanner.listenerInterfaces.FormActionListener;
import at.fh.tourplanner.model.FormDataNewTour;
import at.fh.tourplanner.model.FormMode;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import at.fh.tourplanner.viewmodels.UiServices.UiServiceCreateTour;
import at.fh.tourplanner.viewmodels.UiServices.UiServiceUpdateTour;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TourFormViewModel {

    // --Fields

    // -- Binding Properties
    private final StringProperty tourUUID = new SimpleStringProperty("");
    private final StringProperty tourName = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty destination = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty actionButtonName = new SimpleStringProperty("");
    private final ObjectProperty<TransportType> selectedTransportType = new SimpleObjectProperty<>();
    private final ObservableList<TransportType> transportTypeObservableList = FXCollections.observableArrayList(TransportType.values());

    // -- Listener lists
    private final List<FormActionListener> formActionListeners = new ArrayList<>();

    // -- Services
    private final TourService tourService;
    private final TourImageService tourImageService;
    private final FormValidationService formValidationService;
    private final DirectionService directionService;

    // --Worker Task
    private Service<String> uiServiceQueryMapAPI;

    public TourFormViewModel(DirectionService directionService, TourService tourService,
                             TourImageService tourImageService) {

        this.directionService = directionService;
        this.tourService = tourService;
        this.tourImageService = tourImageService;
        this.formValidationService = new FormValidationServiceImp();

    }
    public StringProperty actionButtonNameProperty() {
        return actionButtonName;
    }
    public String getTourUUID() {
        return tourUUID.get();
    }
    public StringProperty tourUUIDProperty() {
        return tourUUID;
    }
    public void setTourUUID(String tourUUID) {
        this.tourUUID.set(tourUUID);
    }

    public ReadOnlyBooleanProperty runningTaskProperty() {
        return uiServiceQueryMapAPI.runningProperty();
    }

    public StringProperty getTourName() {
        return tourName;
    }

    public StringProperty getStart() {
        return start;
    }

    public StringProperty getDestination() {
        return destination;
    }

    public StringProperty getDescription() {
        return description;
    }


    public void clearForm() {
        tourUUID.set("");
        tourName.set("");
        start.set("");
        destination.set("");
        description.set("");
        selectedTransportType.setValue(null);
    }

    public void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void openFormInWindow(FormMode formMode) {
        if(formMode == FormMode.CREATE){
            actionButtonNameProperty().set("create");
            this.uiServiceQueryMapAPI = new UiServiceCreateTour(tourName, start, destination, description, selectedTransportType,
                    directionService,tourImageService,tourService);
        } if(formMode == FormMode.UPDATE){
            actionButtonNameProperty().set("update");
            this.uiServiceQueryMapAPI = new UiServiceUpdateTour(tourUUID,tourName, start, destination, description, selectedTransportType,
                    directionService,tourImageService,tourService);
        }
        uiServiceQueryMapAPI.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                publishFormEvent();
            }
        });
        uiServiceQueryMapAPI.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, newValue.toString());
                alert.showAndWait();
            }
        });

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/at/fh/tourplanner/tourForm_2.fxml"));
            fxmlLoader.setControllerFactory(controller -> ControllerFactory.getInstance().create(controller));
            Pane gridPane = fxmlLoader.load();
            Stage modal_stage = new Stage();
            modal_stage.setScene(new Scene(gridPane, 600, 300));
            modal_stage.setTitle("modal");
            modal_stage.initModality(Modality.APPLICATION_MODAL);
            modal_stage.initOwner(modal_stage.getOwner());
            modal_stage.setResizable(false);
            modal_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void addFormActionListener(FormActionListener formActionListener) {
        this.formActionListeners.add(formActionListener);
    }

    public void publishFormEvent() {
        System.out.println("publish form Event from Tour Form");
        for (var listener : formActionListeners) {
            listener.onAction();
        }
    }
    public void fillFormWithSelection(Tour tour) {
        if (tour != null) {
            tourUUID.set(tour.getUUID().toString());
            tourName.set(tour.getName());
            start.set(tour.getStart());
            destination.set(tour.getDestination());
            description.set(tour.getDescription());
            selectedTransportType.setValue(tour.getTransportType());
        } else {
            clearForm();
        }
    }

    public ObservableList<TransportType> getTransportTypes() {
        return this.transportTypeObservableList;
    }

    public Property<TransportType> getSelectedTransportType() {
        return this.selectedTransportType;
    }

    public void formAction(FormDataNewTour tour) {
        if (formValidationService.noEmptyValues(tour)) {
            System.out.println("calling APi " + tour);
            uiServiceQueryMapAPI.restart();
        } else {
            System.out.println("error while creating new Tour ");
        }
    }


}


