package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.ControllerFactory;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.MapAPIDataWrapper;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Route;
import at.fh.tourplanner.Main;
import at.fh.tourplanner.businessLayer.*;
import at.fh.tourplanner.listenerInterfaces.FormActionCreateListener;
import at.fh.tourplanner.listenerInterfaces.FormActionEditListener;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
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

import java.awt.image.BufferedImage;
import java.io.IOException;
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

    private final ObjectProperty<TransportType> selectedTransportType = new SimpleObjectProperty<>();

    private final ObservableList<TransportType> transportTypeObservableList = FXCollections.observableArrayList(TransportType.values());
    // -- Services
    private final TourService tourService;
    private final TourImageService tourImageService;
    private final FormValidationService formValidationService;
    private final DirectionService directionService;
    private final UiServiceQueryMapAPI uiServiceQueryMapAPI;

    public TourFormViewModel(DirectionService directionService, TourService tourService,
                             TourImageService tourImageService) {
        this.directionService = directionService;
        this.tourService = tourService;
        this.tourImageService = tourImageService;
        this.formValidationService = new FormValidationServiceImp();
        this.uiServiceQueryMapAPI = new UiServiceQueryMapAPI();
        uiServiceQueryMapAPI.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                Tour newTour = new Tour(
                        getTourName().get(),
                        getStart().get(),
                        getDestination().get(),
                        getDescription().get(),
                        getSelectedTransportType().getValue(),
                        String.valueOf(newVal.getRoute().getRoute().getDistance()),
                        newVal.getRoute().getRoute().getFormattedTime(),
                        new ArrayList<>(),
                        newVal.getRouteMap()
                );
                tourService.addNewTourToDatabase(newTour);
            }

        });
        uiServiceQueryMapAPI.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, newValue.toString());
                alert.showAndWait();
            }
        });
    }

    public UUID getTourUUID() {
        return tourUUID;
    }

    public ReadOnlyBooleanProperty runningProperty() {
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
        tourUUID = null;
        tourName.set("");
        start.set("");
        destination.set("");
        description.set("");
        selectedTransportType.setValue(null);
    }

    public void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void openFormInWindow() {
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


    public void addCreateActionListener(FormActionCreateListener formActionCreateListener) {
        this.createButtonListeners.add(formActionCreateListener);
    }

    public void addEditActionListener(FormActionEditListener formActionEditListener) {
        this.editButtonListeners.add(formActionEditListener);
    }

    public void publishCreateButtonEvent(Tour tour) {
        for (var listener : createButtonListeners) {
            listener.handleCreateAction(tour);
        }
    }

    public void publishEditButtonEvent(Tour tour) {
        for (var listener : editButtonListeners) {
            listener.handleEditAction(tour);
        }
    }

    public void fillFormWithSelection(Tour tour) {
        if (tour != null) {
            tourUUID = tour.getUUID();
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

    public void addNewTourAction(Tour tour) {
        if (formValidationService.noEmptyValues(tour)) {
            System.out.println("calling APi " + tour);
            uiServiceQueryMapAPI.restart();
        } else {
            System.out.println("error while creating new Tour ");
        }
    }

    public class UiServiceQueryMapAPI extends Service<MapAPIDataWrapper> {
        @Override
        protected Task<MapAPIDataWrapper> createTask() {
            return new Task<>() {
                protected MapAPIDataWrapper call() throws Exception {
                    return new MapAPIDataWrapper(
                            directionService.queryDirection(getStart().get(),
                                    getDestination().get()),
                            tourImageService.queryTourImage(getStart().get(),
                                    getDestination().get())
                    );
                }
            };
        }
    }

}


