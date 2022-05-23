package at.fh.tourplanner;

import at.fh.tourplanner.controller.*;
import at.fh.tourplanner.viewmodels.*;

public class ControllerFactory {

    private final TourListViewModel tourListViewModel;

    private final TourFormViewModel tourFormViewModel;

    private final SearchBarViewModel searchBarViewModel;
    private final LogsViewModel logsViewModel;

    private final MainWindowViewModel mainWindowViewModel;


    public ControllerFactory() {
        tourFormViewModel = new TourFormViewModel();
        tourListViewModel = new TourListViewModel();
        searchBarViewModel = new SearchBarViewModel();
        logsViewModel = new LogsViewModel();
        mainWindowViewModel = new MainWindowViewModel(tourFormViewModel,tourListViewModel, searchBarViewModel,logsViewModel);
    }

    public Object create(Class<?> controllerClass) {
        if (controllerClass == TourFormController.class){
            return new TourFormController(tourFormViewModel);
        } else if (controllerClass == TourListController.class) {
            return new TourListController(tourListViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        }else if (controllerClass == LogsController.class) {
            return new LogsController(logsViewModel);
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
