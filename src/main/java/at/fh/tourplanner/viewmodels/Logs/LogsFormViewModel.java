package at.fh.tourplanner.viewmodels.Logs;

import at.fh.tourplanner.ControllerFactory;
import at.fh.tourplanner.Main;
import at.fh.tourplanner.businessLayer.FormValidationService;
import at.fh.tourplanner.businessLayer.LogService;
import at.fh.tourplanner.listenerInterfaces.FormActionListener;
import at.fh.tourplanner.model.*;
import at.fh.tourplanner.viewmodels.Tours.TourFormViewModel;
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

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LogsFormViewModel {
    // -- Properties
    private final IntegerProperty tourID = new SimpleIntegerProperty();
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

    public void addNewLogAction(LogFormData logformData) {
        if (formValidationService.noEmptyValues(logformData)) {
            System.out.println("calling APi " + logformData);
            System.out.println("Owned by Tour:  " + getTourID());
            uiServiceQueryAPI.restart();

        } else {
            System.out.println("error while creating new Tour ");
        }
    }
    public void updateLogAction(LogFormData logFormData){}

    public void addFormActionListener(FormActionListener formActionListener) {
        this.formActionListeners.add(formActionListener);
    }
    public void publishFormEvent() {
        System.out.println("publish form Event from Tour Form");
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
                    System.out.println("starting task adding log");
                    Log newLog = new Log(
                            date.get(),
                            ratingObjectProperty.getValue(),
                            difficultyTierObjectProperty.getValue(),
                            Integer.parseInt(duration.get()),
                            comment.get(),
                            getTourID()
                    );
                    logService.addNewLogToDatabase(newLog);
                    return "completed delete Task";
                }
            };
        }
    }

}

