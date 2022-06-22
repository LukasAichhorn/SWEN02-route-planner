package at.fh.tourplanner.controller;

import at.fh.tourplanner.viewmodels.Tours.StaticTourInfoViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class StaticTourInfoController implements Initializable {
    private final StaticTourInfoViewModel staticTourInfoViewModel;
    public Label tourNameText;
    public Label startText;
    public Label destinationText;
    public Label tourDistanceText;
    public Label estimatedTimeText;
    public Label descriptionText;
    public Label transportTypeText;
    public ImageView imageView;

    public StaticTourInfoController(StaticTourInfoViewModel staticTourInfoViewModel
    ) {
        this.staticTourInfoViewModel = staticTourInfoViewModel;
        imageView = new ImageView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourNameText.textProperty().bind(staticTourInfoViewModel.getTourName());
        startText.textProperty().bind(staticTourInfoViewModel.getStart());
        destinationText.textProperty().bind(staticTourInfoViewModel.getDestination());
        tourDistanceText.textProperty().bind(staticTourInfoViewModel.getTourDistance());
        estimatedTimeText.textProperty().bind(staticTourInfoViewModel.getEstimatedTime());
        descriptionText.textProperty().bind(staticTourInfoViewModel.getDescription());
        transportTypeText.textProperty().bind(staticTourInfoViewModel.getSelectedTransportType());
        imageView.imageProperty().bind(staticTourInfoViewModel.getImageView());


    }
}
