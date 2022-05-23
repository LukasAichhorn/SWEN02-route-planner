package at.fh.tourplanner.controller;


import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import at.fh.tourplanner.viewmodels.TourFormViewModel;
import at.fh.tourplanner.viewmodels.TourListViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    public Button deleteButton;

    public Button editButton;


    public TourFormController(TourFormViewModel tourFormViewModel) {

        this.tourFormViewModel = tourFormViewModel;
        this.transportTypeChoiceBox = new ChoiceBox<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourNameTextField.textProperty().bindBidirectional(tourFormViewModel.getTourName());
        startTextField.textProperty().bindBidirectional(tourFormViewModel.getStart());
        destinationTextField.textProperty().bindBidirectional(tourFormViewModel.getDestination());
        descriptionTextArea.textProperty().bindBidirectional(tourFormViewModel.getDescription());
        estimatedTimeTextField.textProperty().bindBidirectional(tourFormViewModel.getEstimatedTime());
        tourDistanceTextField.textProperty().bindBidirectional(tourFormViewModel.getTourDistance());
        transportTypeChoiceBox.getItems().setAll(tourFormViewModel.getTransportTypes());
        transportTypeChoiceBox.valueProperty().bindBidirectional(tourFormViewModel.getSelectedTransportType());
    }

    public void saveAction() {
        Tour tour = new Tour(tourNameTextField.getText(), startTextField.getText(), destinationTextField.getText(), descriptionTextArea.getText(), TransportType.BICYCLE, "120 km", "2h");
        tourFormViewModel.publishCreateButtonEvent(tour);
        tourFormViewModel.clearForm();

    }

    public void editAction() {
        Tour tour = new Tour(tourNameTextField.getText(), startTextField.getText(), destinationTextField.getText(), descriptionTextArea.getText(), TransportType.BICYCLE, "120 km", "2h");
        tourFormViewModel.publishEditButtonEvent(tour);
    }
}
