package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.ControllerFactory;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.DirectionServiceResponse;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.MapAPIDataWrapper;
import at.fh.tourplanner.Main;
import at.fh.tourplanner.businessLayer.*;
import at.fh.tourplanner.listenerInterfaces.FormActionCreateListener;
import at.fh.tourplanner.listenerInterfaces.FormActionEditListener;
import at.fh.tourplanner.model.FormDataNewTour;
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
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TourFormViewModel {

    // --Fields


    // -- Binding Properties
    private final  StringProperty tourUUID = new SimpleStringProperty("");
    private final StringProperty tourName = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty destination = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty actionButtonName = new SimpleStringProperty("");
    private final ObjectProperty<TransportType> selectedTransportType = new SimpleObjectProperty<>();
    private final ObservableList<TransportType> transportTypeObservableList = FXCollections.observableArrayList(TransportType.values());

    // -- Listener lists
    private final List<FormActionCreateListener> createButtonListeners = new ArrayList<>();
    private final List<FormActionEditListener> editButtonListeners = new ArrayList<>();

    // -- Services
    private final TourService tourService;
    private final TourImageService tourImageService;
    private final FormValidationService formValidationService;
    private final DirectionService directionService;

    // --Worker
    private  final UiServiceQueryMapAPI uiServiceQueryMapAPI;
    private final UiServiceUpdateMapAPI uiServiceUpdateMapAPI;

    public TourFormViewModel(DirectionService directionService, TourService tourService,
                             TourImageService tourImageService) {

        this.directionService = directionService;
        this.tourService = tourService;
        this.tourImageService = tourImageService;
        this.formValidationService = new FormValidationServiceImp();
        this.uiServiceQueryMapAPI =  new UiServiceQueryMapAPI(tourName,start,
            destination,description,selectedTransportType);
        this.uiServiceUpdateMapAPI = new UiServiceUpdateMapAPI(tourUUID,tourName,start,
                destination,description,selectedTransportType);

        uiServiceQueryMapAPI.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                publishCreateButtonEvent();
            }
        });
        uiServiceUpdateMapAPI.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                publishEditButtonEvent();
            }
        });
        uiServiceQueryMapAPI.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, newValue.toString());
                alert.showAndWait();
            }
        });
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

    public ReadOnlyBooleanProperty runningCreateProperty() {
        return uiServiceQueryMapAPI.runningProperty();
    }
    public ReadOnlyBooleanProperty runningUpdateProperty() {
        return uiServiceUpdateMapAPI.runningProperty();
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

    public void openFormInWindow(String buttonName) {
        actionButtonNameProperty().set(buttonName);

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

    public void publishCreateButtonEvent() {
        System.out.println("publish create Button Event from Tour Form");
        for (var listener : createButtonListeners) {
            listener.handleCreateAction();
        }
    }

    public void publishEditButtonEvent() {
        System.out.println("publish Edit Button Event from Tour Form");
        for (var listener : editButtonListeners) {
            listener.handleEditAction();
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

    public void addNewTourAction(FormDataNewTour tour) {
        if (formValidationService.noEmptyValues(tour)) {
            System.out.println("calling APi " + tour);
            uiServiceQueryMapAPI.restart();
        } else {
            System.out.println("error while creating new Tour ");
        }
    }

    public void updateTourAction(FormDataNewTour tour) {
        if (formValidationService.noEmptyValues(tour)) {
            System.out.println("calling APi " + tour);
            uiServiceUpdateMapAPI.restart();
        } else {
            System.out.println("error while creating new Tour ");
        }
    }
    @Getter @AllArgsConstructor
    public class UiServiceQueryMapAPI extends Service<MapAPIDataWrapper> {
        private StringProperty name;
        private StringProperty start;
        private StringProperty end;
        private StringProperty description;
        private ObjectProperty<TransportType> selectedTransportType;

        @Override
        protected Task<MapAPIDataWrapper> createTask() {
            return new Task<>() {
                protected MapAPIDataWrapper call() {
                    DirectionServiceResponse dirR =
                            directionService.queryDirection(start.get(),
                                    end.get());
                    System.out.println("DirR is done");
                    ImageServiceResponse imgR =
                            tourImageService.queryTourImage(start.get(),
                                    getDestination().get());
                    System.out.println("imgR is done");
                    Tour newTour = new Tour(
                            imgR.getGeneratedId(),
                            name.get(),
                            start.get(),
                            end.get(),
                            description.get(),
                            selectedTransportType.getValue(),
                            String.valueOf(dirR.getRoute().getDistance()),
                            dirR.getRoute().getFormattedTime(),
                            new ArrayList<>(),
                            imgR.getImagePath()

                    );
                    tourService.addNewTourToDatabase(newTour);

                    return new MapAPIDataWrapper(dirR, imgR);
                }
            };
        }
    }
    @Getter @AllArgsConstructor
    public class UiServiceUpdateMapAPI extends Service<MapAPIDataWrapper> {
        private StringProperty id;
        private StringProperty name;
        private StringProperty start;
        private StringProperty end;
        private StringProperty description;
        private ObjectProperty<TransportType> selectedTransportType;

        @Override
        protected Task<MapAPIDataWrapper> createTask() {
            return new Task<>() {
                protected MapAPIDataWrapper call() {
                    System.out.println("Updating ID: "+ id + "in thread");
                    DirectionServiceResponse dirR =
                            directionService.queryDirection(start.get(),
                                    end.get());
                    System.out.println("DirR is done");
                    ImageServiceResponse imgR =
                            tourImageService.queryTourImage(start.get(),
                                    getDestination().get());
                    System.out.println("imgR is done");
                    Tour newTour = new Tour(
                            UUID.fromString(id.get()),
                            name.get(),
                            start.get(),
                            end.get(),
                            description.get(),
                            selectedTransportType.getValue(),
                            String.valueOf(dirR.getRoute().getDistance()),
                            dirR.getRoute().getFormattedTime(),
                            new ArrayList<>(),
                            imgR.getImagePath()

                    );
                    tourService.updateTourInDatabase(newTour);

                    return new MapAPIDataWrapper(dirR, imgR);
                }
            };
        }
    }

}


