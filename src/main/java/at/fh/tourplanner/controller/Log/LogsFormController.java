package at.fh.tourplanner.controller.Log;

import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.FormDataNewTour;
import at.fh.tourplanner.model.LogFormData;
import at.fh.tourplanner.viewmodels.Logs.LogsFormViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LogsFormController implements Initializable {
    private final LogsFormViewModel logsFormViewModel;
    public DatePicker datePickerField;
    public ChoiceBox<Integer> ratingChoiceBox;
    public ChoiceBox<DifficultyTier> difficultyTierChoiceBox;
    public TextField durationTextField;
    public TextField commentTextField;
    public ProgressIndicator progressIndicator;
    public StackPane stackPane;
    public Button actionButton;


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
        progressIndicator.visibleProperty().bind(logsFormViewModel.runningTaskProperty());
        stackPane.visibleProperty().bind(logsFormViewModel.runningTaskProperty());
        actionButton.textProperty().bind(logsFormViewModel.actionButtonNameProperty());
        if(logsFormViewModel.actionButtonNameProperty().get().equals("create")) {
            actionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    addNewLogAction();
                }
            });
        }
        if(logsFormViewModel.actionButtonNameProperty().get().equals("update")) {
            actionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    updateLogAction();
                }
            });
        }
    }
    private LogFormData collectFormData(){
        return new LogFormData(
                datePickerField.getValue(),
                ratingChoiceBox.getValue(),
                difficultyTierChoiceBox.getValue(),
                Integer.parseInt(durationTextField.getText()),
                commentTextField.getText());
    }
    public void addNewLogAction(){
        logsFormViewModel.addNewLogAction(collectFormData());
    }
    public void updateLogAction(){
        logsFormViewModel.updateLogAction(collectFormData());
    }
}
