package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.model.DifficultyTier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

public class LogsViewModel {
    private UUID logUUID = null;
    private final ObjectProperty date = new SimpleObjectProperty(LocalDate.now());
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty comment = new SimpleStringProperty("");
    private final ObservableList<DifficultyTier> difficultyTierObservableList =FXCollections.observableArrayList(DifficultyTier.values());
    private final ObservableList<String> ratingObservableList = FXCollections.observableArrayList("❤",
            "❤❤",
            "❤❤❤",
            "❤❤❤❤");

    public StringProperty getDuration(){return duration;}
    public StringProperty  getComment(){return comment;}
    public ObjectProperty getDate() {return date;}
    public ObservableList<String> getRatings() {
        return this.ratingObservableList;
    }
    public ObservableList<DifficultyTier> getDifficultyTiers() {
        return this.difficultyTierObservableList;
    }
}
