package at.fh.tourplanner.viewmodels.Logs;

import at.fh.tourplanner.ControllerFactory;
import at.fh.tourplanner.Main;
import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

public class LogsFormViewModel {
    private UUID logUUID = null;
    private final ObjectProperty date = new SimpleObjectProperty();
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty comment = new SimpleStringProperty("");
    private final ObservableList<DifficultyTier> difficultyTierObservableList = FXCollections.observableArrayList(DifficultyTier.values());
    private final ObjectProperty<DifficultyTier> difficultyTierObjectProperty = new SimpleObjectProperty<>();
    private final ObservableList<Integer> ratingObservableList = FXCollections.observableArrayList(1, 2, 3, 4);
    private final ObjectProperty<Integer> ratingObjectProperty = new SimpleObjectProperty<>();


    public StringProperty getDuration() {
        return duration;
    }

    public StringProperty getComment() {
        return comment;
    }

    public ObjectProperty getDate() {
        return date;
    }

    public ObservableList<Integer> getRatings() {
        return this.ratingObservableList;
    }

    public ObservableList<DifficultyTier> getDifficultyTiers() {
        return this.difficultyTierObservableList;
    }
    public void openFormInWindow(String buttonName) {
       // actionButtonNameProperty().set(buttonName);

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

    public void fillFormWithSelection(Log log) {
        if (log != null) {

            date.set(log.getTimeStamp());
            duration.set(log.getDuration().toString());
            difficultyTierObjectProperty.setValue(log.getDifficulty());
            ratingObjectProperty.setValue(log.getRating());
            comment.set(log.getComment());
        } else {
            clearForm();
        }
    }

    public void clearForm() {
        logUUID = null;
        date.set(LocalDate.now());
        duration.set("");
        difficultyTierObjectProperty.setValue(null);
        ratingObjectProperty.setValue(null);
        comment.set("");

    }

    public Property<DifficultyTier> getSelectedDifficulty() {
        return this.difficultyTierObjectProperty;

    }

    public Property<Integer> getSelectedRating() {
        return this.ratingObjectProperty;
    }
}

