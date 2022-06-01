package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public void fillFormWithSelection(Log log) {
        if (log != null) {
            logUUID = log.getUuid();
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

