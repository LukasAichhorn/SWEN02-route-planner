package at.fh.tourplanner.viewmodels.Logs;

import at.fh.tourplanner.ControllerFactory;
import at.fh.tourplanner.Main;
import at.fh.tourplanner.businessLayer.validationService.FormValidationService;
import at.fh.tourplanner.businessLayer.logService.LogService;
import at.fh.tourplanner.listenerInterfaces.FormActionListener;
import at.fh.tourplanner.model.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class LogsFormViewModel {
    // -- Properties
    private final IntegerProperty tourID = new SimpleIntegerProperty();
    private final IntegerProperty logID = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty comment = new SimpleStringProperty("");
    private final ObjectProperty<DifficultyTier> difficultyTierObjectProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> ratingObjectProperty = new SimpleObjectProperty<>();
    private final StringProperty actionButtonName = new SimpleStringProperty("");

    // -- Lists
    private final ObservableList<DifficultyTier> difficultyTierObservableList = FXCollections.observableArrayList(DifficultyTier.values());
    private final ObservableList<Integer> ratingObservableList = FXCollections.observableArrayList(1, 2, 3, 4);

    // -- Listener lists
    private final List<FormActionListener> formActionListeners = new ArrayList<>();

    //-- Services
    private final FormValidationService formValidationService;
    private final LogService logService;

    // -- UI Worker
    private final UiServiceQueryAPI uiServiceQueryAPI;


    public LogsFormViewModel(FormValidationService formValidationService, LogService logService) {
        this.formValidationService = formValidationService;
        this.logService = logService;
        this.uiServiceQueryAPI = new UiServiceQueryAPI();
        uiServiceQueryAPI.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                publishFormEvent();
            }
        });
    }
    public StringProperty actionButtonNameProperty() {
        return actionButtonName;
    }
    public ReadOnlyBooleanProperty runningTaskProperty() {
        return uiServiceQueryAPI.runningProperty();
    }
    public int getTourID() {
        return tourID.get();
    }

    public IntegerProperty tourIDProperty() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID.set(tourID);
    }

    public int getLogID() {
        return logID.get();
    }

    public IntegerProperty logIDProperty() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID.set(logID);
    }

    public StringProperty getDuration() {
        return duration;
    }

    public StringProperty getComment() {
        return comment;
    }

    public ObjectProperty<LocalDate> getDate() {
        return date;
    }

    public Property<DifficultyTier> getSelectedDifficulty() {
        return this.difficultyTierObjectProperty;

    }

    public Property<Integer> getSelectedRating() {
        return this.ratingObjectProperty;
    }

    public ObservableList<Integer> getRatings() {
        return this.ratingObservableList;
    }

    public ObservableList<DifficultyTier> getDifficultyTiers() {
        return this.difficultyTierObservableList;
    }


    public void openFormInWindow(String buttonName) {
        actionButtonNameProperty().set(buttonName);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/at/fh/tourplanner/logForm_2" +
                    ".fxml"));
            fxmlLoader.setControllerFactory(controller -> ControllerFactory.getInstance().create(controller));
            Pane gridPane = fxmlLoader.load();
            Stage modal_stage = new Stage();
            modal_stage.setScene(new Scene(gridPane, 620, 300));
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

    public void fillFormWithSelection(Log log, int selectedTourId) {
        if (log != null) {
            logID.set(log.getId());
            tourID.set(selectedTourId);
            date.set(log.getTimeStamp());
            duration.set(String.valueOf(log.getDuration()));
            difficultyTierObjectProperty.setValue(log.getDifficulty());
            ratingObjectProperty.setValue(log.getRating());
            comment.set(log.getComment());
        } else {
            clearForm();
        }
    }

    public void clearForm() {
        tourID.set(0);
        date.set(LocalDate.now());
        duration.setValue(null);
        difficultyTierObjectProperty.setValue(null);
        ratingObjectProperty.setValue(null);
        comment.set("");

    }

    public void addNewLogAction(LogFormData logFormData) {
        if (formValidationService.noEmptyValues(logFormData)) {
            log.info("New Log created data: {}", logFormData);
            uiServiceQueryAPI.restart();

        } else {
            log.error("Error: Incorrect data submitted {}", logFormData.toString());
        }
    }
    public void updateLogAction(LogFormData logFormData){
        if (formValidationService.noEmptyValues(logFormData)) {
            log.info("Updating log data: {}", logFormData.toString());
            uiServiceQueryAPI.restart();

        } else {
            log.error("Error: Incorrect data submitted for update {}", logFormData.toString());
        }
    }

    public void addFormActionListener(FormActionListener formActionListener) {
        this.formActionListeners.add(formActionListener);
    }
    public void publishFormEvent() {
        for (var listener : formActionListeners) {
            listener.onAction();
        }
    }

    @AllArgsConstructor
    @Setter
    public class UiServiceQueryAPI extends Service<String> {


        @Override
        protected Task<String> createTask() {
            return new Task<>() {
                protected String call() {

                    Log newLog = new Log(
                            logID.get(),
                            date.get(),
                            ratingObjectProperty.getValue(),
                            difficultyTierObjectProperty.getValue(),
                            Integer.parseInt(duration.get()),
                            comment.get(),
                            getTourID()
                    );
                    if(actionButtonNameProperty().get().equals("create")){
                        logService.addNewLogToDatabase(newLog);
                    }
                    if(actionButtonNameProperty().get().equals("update")){
                        logService.updateLogInDatabase(newLog);
                    }

                    return "completed delete Task";
                }
            };
        }
    }

}

