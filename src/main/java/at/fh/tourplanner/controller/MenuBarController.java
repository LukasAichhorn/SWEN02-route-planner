package at.fh.tourplanner.controller;

import at.fh.tourplanner.viewmodels.MenuBarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {

    private final MenuBarViewModel menuBarViewModel;
    public javafx.scene.control.MenuItem statisticalReportItem;
    public javafx.scene.control.MenuItem tourReportMenuItem;


    public MenuBarController(MenuBarViewModel menuBarViewModel) {
        this.menuBarViewModel = menuBarViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourReportMenuItem.visibleProperty().bindBidirectional(menuBarViewModel.getIsTourSelected());
    }

    public void statisticalReportAction(ActionEvent actionEvent) {
        menuBarViewModel.createStatisticalReport();
    }

    public void tourReportAction(ActionEvent actionEvent) {
        menuBarViewModel.createTourReport();
    }
}
