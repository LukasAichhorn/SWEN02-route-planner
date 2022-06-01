package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.DataAccessLayer.TourRepository;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LogListViewModel {
    ObservableList<Log> logs = FXCollections.observableArrayList();

    public LogListViewModel(){}
    public ObservableList<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        System.out.println("setting obs list in View model logs");
        System.out.println(logs);
        this.logs.setAll(logs);
    }
}
