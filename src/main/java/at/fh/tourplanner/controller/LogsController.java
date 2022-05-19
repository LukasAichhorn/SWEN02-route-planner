package at.fh.tourplanner.controller;

import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.viewmodels.LogsViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogsController implements Initializable {
    private final LogsViewModel logsViewModel;
    public DatePicker datePickerField;
    public ChoiceBox<String> ratingChoiceBox;
    public ChoiceBox<DifficultyTier> difficultyTierChoiceBox;
    public TextField durationTextField;
    public TextField commentTextField;


    public LogsController(LogsViewModel logsViewModel) {
        this.logsViewModel = logsViewModel;
        this.ratingChoiceBox = new ChoiceBox<>();
        this.difficultyTierChoiceBox = new ChoiceBox<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        durationTextField.textProperty().bindBidirectional(logsViewModel.getDuration());
        commentTextField.textProperty().bindBidirectional(logsViewModel.getComment());
        difficultyTierChoiceBox.getItems().setAll(logsViewModel.getDifficultyTiers());
        ratingChoiceBox.getItems().setAll(logsViewModel.getRatings());
        datePickerField.valueProperty().bindBidirectional(logsViewModel.getDate());
    }
}
