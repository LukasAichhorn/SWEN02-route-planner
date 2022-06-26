package at.fh.tourplanner.controller.Tour;


import at.fh.tourplanner.model.FormDataNewTour;
import at.fh.tourplanner.model.TransportType;
import at.fh.tourplanner.viewmodels.Tours.TourFormViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TourFormController implements Initializable {

    private final TourFormViewModel tourFormViewModel;
    public TextField tourNameTextField;
    public TextField startTextField;
    public TextField destinationTextField;
    public TextField tourDistanceTextField;
    public TextField estimatedTimeTextField;
    public TextArea descriptionTextArea;
    public ChoiceBox<TransportType> transportTypeChoiceBox;
    public ProgressIndicator progressIndicator;
    public StackPane stackPane;
    public Button addButton;
    public Button actionButton;


    public TourFormController(TourFormViewModel tourFormViewModel) {

        this.tourFormViewModel = tourFormViewModel;
        this.transportTypeChoiceBox = new ChoiceBox<>();
        this.stackPane = new StackPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourNameTextField.textProperty().bindBidirectional(tourFormViewModel.getTourName());
        startTextField.textProperty().bindBidirectional(tourFormViewModel.getStart());
        destinationTextField.textProperty().bindBidirectional(tourFormViewModel.getDestination());
        descriptionTextArea.textProperty().bindBidirectional(tourFormViewModel.getDescription());
        transportTypeChoiceBox.getItems().setAll(tourFormViewModel.getTransportTypes());
        transportTypeChoiceBox.valueProperty().bindBidirectional(tourFormViewModel.getSelectedTransportType());
        progressIndicator.visibleProperty().bind(tourFormViewModel.runningTaskProperty());
        stackPane.visibleProperty().bind(tourFormViewModel.runningTaskProperty());


        actionButton.textProperty().bind(tourFormViewModel.actionButtonNameProperty());
        if(tourFormViewModel.actionButtonNameProperty().get().equals("create")) {
            actionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    addNewTourAction();
                }
            });
        }
        if(tourFormViewModel.actionButtonNameProperty().get().equals("update")) {
            actionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    updateTourAction();
                }
            });
        }

    }
    private FormDataNewTour collectFormData(){
        return new FormDataNewTour(
                tourNameTextField.getText(),
                transportTypeChoiceBox.getValue(),
                startTextField.getText(),
                destinationTextField.getText(),
                descriptionTextArea.getText());
    }
    public void addNewTourAction() {
        tourFormViewModel.addNewTourAction(collectFormData());
    }
    public void updateTourAction() {
        tourFormViewModel.updateTourAction(collectFormData());
    }
    public void closeWindow(ActionEvent event){
        tourFormViewModel.closeWindow(event);
    }
//
//    public void editAction() {
//        Tour tour = new Tour(tourNameTextField.getText(), startTextField.getText(), destinationTextField.getText(), descriptionTextArea.getText(), TransportType.BICYCLE, "120 km", "2h");
//        tourFormViewModel.publishEditButtonEvent(tour);
//    }
}
