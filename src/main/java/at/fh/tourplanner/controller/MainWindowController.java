package at.fh.tourplanner.controller;

import at.fh.tourplanner.viewmodels.MainWindowViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private final MainWindowViewModel mainWindowViewModel;

    public Button newTourButton;

    public MainWindowController(MainWindowViewModel mainWindowViewModel) {
        this.mainWindowViewModel = mainWindowViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void startNewTourModeAction(ActionEvent actionEvent) {
        mainWindowViewModel.publishNewTourModeEvent();
    }
    public void openFormWindow(){
        mainWindowViewModel.openFormWindow();
    }
}
