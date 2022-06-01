package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LogListViewModel {
    ObservableList<Log> logs = FXCollections.observableArrayList();

    public ObservableList<Log> getLogs() {
        return logs;
    }
}
