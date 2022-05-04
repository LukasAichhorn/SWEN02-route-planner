package at.fh.tourplanner.controller;

import at.fh.tourplanner.viewmodels.TourFormViewModel;
import at.fh.tourplanner.viewmodels.TourListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TourFormController implements Initializable {

    private final TourListViewModel tourListViewModel;
    private final TourFormViewModel tourFormViewModel;


    public TextField tourNameTextField;

    public TextField startTextField;

    public TextField destinationTextField;

    public TextArea descriptionTextArea;

    public TourFormController(TourListViewModel tourListViewModel, TourFormViewModel tourFormViewModel) {
        this.tourListViewModel = tourListViewModel;
        this.tourFormViewModel = tourFormViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tourNameTextField.textProperty().bindBidirectional(tourFormViewModel.getTourName());
        startTextField.textProperty().bindBidirectional(tourFormViewModel.getStart());
        destinationTextField.textProperty().bindBidirectional(tourFormViewModel.getDestination());
        descriptionTextArea.textProperty().bindBidirectional(tourFormViewModel.getDescription());
    }

    public void saveAction(ActionEvent actionEvent) {
        tourListViewModel.saveTourToList(tourFormViewModel.getFormData());
    }
}
