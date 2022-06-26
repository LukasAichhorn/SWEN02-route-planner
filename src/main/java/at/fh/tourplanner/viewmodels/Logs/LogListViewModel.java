package at.fh.tourplanner.viewmodels.Logs;

import at.fh.tourplanner.businessLayer.LogService;
import at.fh.tourplanner.listenerInterfaces.*;
import at.fh.tourplanner.model.Log;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class LogListViewModel {
    ObservableList<Log> logs = FXCollections.observableArrayList();

    private final List<ListItemSelectionListener> eventListeners = new ArrayList<>();
    private final List<OpenBlankLogFormListener> openBlankLogFormListeners =
            new ArrayList<>();
    private final List<OpenFilledLogFormListener> openFilledLogFormListeners =
            new ArrayList<>();
    private final BooleanProperty createLogIsDisabled = new SimpleBooleanProperty(true);
    private final BooleanProperty editIsDisabled = new SimpleBooleanProperty(true);
    // -- services
    LogService logService;

    public LogListViewModel(LogService logService){
        this.logService = logService;

    }

    public boolean isEditIsDisabled() {
        return editIsDisabled.get();
    }

    public BooleanProperty editIsDisabledProperty() {
        return editIsDisabled;
    }

    public void setEditIsDisabled(boolean editIsDisabled) {
        this.editIsDisabled.set(editIsDisabled);
    }

    public ObservableList<Log> getLogs() {
        return logs;
    }

    public boolean isCreateLogIsDisabled() {
        return createLogIsDisabled.get();
    }

    public BooleanProperty createLogIsDisabledProperty() {
        return createLogIsDisabled;
    }

    public void setCreateLogIsDisabled(boolean createLogIsDisabled) {
        this.createLogIsDisabled.set(createLogIsDisabled);
    }
    public void clearLogsList(){
        this.logs.clear();
    }
    public void setLogs(int tourID) {
        this.logs.clear();
        this.logs.setAll(logService.getLogForTourFromDatabase(tourID));
    }
    public void addListener(ListItemSelectionListener listItemSelectionListener){
        this.eventListeners.add(listItemSelectionListener);
    }
    public void publishSelectionEvent(Log log) {
        for(var listener : eventListeners){
            listener.fillForm(log);
        }
    }
    public void addOpenBlankLogFormListener(OpenBlankLogFormListener openBlankLogFormListener) {
        this.openBlankLogFormListeners.add(openBlankLogFormListener);
    }
    public void addOpenFilledLogFormListener(OpenFilledLogFormListener openFilledLogFormListener) {
        this.openFilledLogFormListeners.add(openFilledLogFormListener);
    }
    public void publishOpenBlankLogFormEvent() {
        for (var listener : openBlankLogFormListeners) {
            listener.handleEvent();
        }
    }
    public void publishOpenFilledLogFormEvent() {
        for (var listener : openFilledLogFormListeners) {
            listener.handleEvent();
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
    public void openBlankLogFormButtonAction(){
        publishOpenBlankLogFormEvent();
    }
    public void openFilledLogFormButtonAction(){
        publishOpenFilledLogFormEvent();
    }


}
