package at.fh.tourplanner.controller.Log;

import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.viewmodels.LogListViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class LogListController implements Initializable {
    private final LogListViewModel logListViewModel;
    public TableView<Log> logList;

    public LogListController(LogListViewModel logListViewModel) {
        this.logListViewModel = logListViewModel;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
