package at.fh.tourplanner;

import at.fh.tourplanner.controller.Log.LogListController;
import at.fh.tourplanner.controller.Log.LogsFormController;
import at.fh.tourplanner.controller.SearchBarController;
import at.fh.tourplanner.controller.Tour.TourFormController;
import at.fh.tourplanner.controller.Tour.TourListController;
import at.fh.tourplanner.controller.*;
import at.fh.tourplanner.viewmodels.*;

public class ControllerFactory {

    private final TourListViewModel tourListViewModel;
    private final TourFormViewModel tourFormViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final LogsFormViewModel logsFormViewModel;
    private final LogListViewModel logListViewModel;

    private final MainWindowViewModel mainWindowViewModel;


    public ControllerFactory() {
        tourFormViewModel = new TourFormViewModel();
        tourListViewModel = new TourListViewModel();
        searchBarViewModel = new SearchBarViewModel();
        logsFormViewModel = new LogsFormViewModel();
        logListViewModel = new LogListViewModel();
        mainWindowViewModel = new MainWindowViewModel(tourFormViewModel,tourListViewModel, searchBarViewModel, logsFormViewModel,logListViewModel);
    }

    public Object create(Class<?> controllerClass) {
        if (controllerClass == TourFormController.class){
            return new TourFormController(tourFormViewModel);
        } else if (controllerClass == TourListController.class) {
            return new TourListController(tourListViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        }else if (controllerClass == LogsFormController.class) {
            return new LogsFormController(logsFormViewModel);
        }else if (controllerClass == LogListController.class) {
            return new LogListController(logListViewModel);
        } else if(controllerClass == MainWindowController.class){
            return new MainWindowController(mainWindowViewModel);
        }
        throw new IllegalArgumentException("Unknown Controller Class");
    }

    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }
}
