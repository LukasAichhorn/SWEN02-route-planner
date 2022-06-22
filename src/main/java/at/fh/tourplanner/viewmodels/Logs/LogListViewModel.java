package at.fh.tourplanner.viewmodels.Logs;

import at.fh.tourplanner.listenerInterfaces.ListItemSelectionListener;
import at.fh.tourplanner.model.Log;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class LogListViewModel {
    ObservableList<Log> logs = FXCollections.observableArrayList();
    private List<ListItemSelectionListener> eventListeners = new ArrayList<>();


    public LogListViewModel(){}
    public ObservableList<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        System.out.println("setting obs list in View model logs");
        System.out.println(logs);
        this.logs.clear();
        this.logs.setAll(logs);
    }
    public void addListener(ListItemSelectionListener listItemSelectionListener){
        this.eventListeners.add(listItemSelectionListener);
    }
    public void publishSelectionEvent(Log log) {
        for(var listener : eventListeners){
            listener.fillForm(log);
        }
    }

    public void addChangeListener(TableView tableView){
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Log>() {
            @Override
            public void changed(ObservableValue<? extends Log> observableValue, Log o, Log t1) {
                publishSelectionEvent(t1);
                System.out.println("changeListener triggered with " + t1);
            }
        });
    }

}
