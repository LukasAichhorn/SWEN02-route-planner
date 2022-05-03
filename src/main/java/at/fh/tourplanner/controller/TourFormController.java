package at.fh.tourplanner.controller;

import at.fh.tourplanner.viewmodels.TourFormViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TourFormController implements Initializable {

    private final TourFormViewModel viewModel = new TourFormViewModel();

    public TextField tourNameTextField;

    public TextField startTextField;

    public TextField destinationTextField;

    public TextArea descriptionTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tourNameTextField.textProperty().bindBidirectional(viewModel.getTourName());
        startTextField.textProperty().bindBidirectional(viewModel.getStart());
        destinationTextField.textProperty().bindBidirectional(viewModel.getDestination());
        descriptionTextArea.textProperty().bindBidirectional(viewModel.getDescription());
    }

    public void saveAction(ActionEvent actionEvent) {
        viewModel.saveTourToList();
    }
}
