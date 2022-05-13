package at.fh.tourplanner;

import at.fh.tourplanner.controller.SearchBarController;
import at.fh.tourplanner.controller.TourFormController;
import at.fh.tourplanner.controller.TourListController;
import at.fh.tourplanner.viewmodels.MainWindowViewModel;
import at.fh.tourplanner.viewmodels.SearchBarViewModel;
import at.fh.tourplanner.viewmodels.TourFormViewModel;
import at.fh.tourplanner.viewmodels.TourListViewModel;

public class ControllerFactory {

    private final TourListViewModel tourListViewModel;

    private final TourFormViewModel tourFormViewModel;

    private final SearchBarViewModel searchBarViewModel;

    private final MainWindowViewModel mainWindowViewModel;


    public ControllerFactory() {
        tourFormViewModel = new TourFormViewModel();
        tourListViewModel = new TourListViewModel();
        searchBarViewModel = new SearchBarViewModel();
        mainWindowViewModel = new MainWindowViewModel(tourFormViewModel,tourListViewModel, searchBarViewModel);
    }

    public Object create(Class<?> controllerClass) {
        if (controllerClass == TourFormController.class){
            return new TourFormController(tourFormViewModel);
        } else if (controllerClass == TourListController.class) {
            return new TourListController(tourListViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        }
        throw new IllegalArgumentException("Unknown Controller Class");
    }

    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }
}
