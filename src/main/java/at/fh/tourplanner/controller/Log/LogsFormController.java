package at.fh.tourplanner.controller.Log;

import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.viewmodels.Logs.LogsFormViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogsFormController implements Initializable {
    private final LogsFormViewModel logsFormViewModel;
    public DatePicker datePickerField;
    public ChoiceBox<Integer> ratingChoiceBox;
    public ChoiceBox<DifficultyTier> difficultyTierChoiceBox;
    public TextField durationTextField;
    public TextField commentTextField;


    public LogsFormController(LogsFormViewModel logsFormViewModel) {
        this.logsFormViewModel = logsFormViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        durationTextField.textProperty().bindBidirectional(logsFormViewModel.getDuration());
        commentTextField.textProperty().bindBidirectional(logsFormViewModel.getComment());
         difficultyTierChoiceBox.getItems().setAll(logsFormViewModel.getDifficultyTiers());
         difficultyTierChoiceBox.valueProperty().bindBidirectional(logsFormViewModel.getSelectedDifficulty());
         ratingChoiceBox.getItems().setAll(logsFormViewModel.getRatings());
        ratingChoiceBox.valueProperty().bindBidirectional(logsFormViewModel.getSelectedRating());

         datePickerField.valueProperty().bindBidirectional(logsFormViewModel.getDate());
    }
}
