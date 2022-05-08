package at.fh.tourplanner;

import at.fh.tourplanner.controller.TourFormController;
import at.fh.tourplanner.controller.TourListController;
import at.fh.tourplanner.viewmodels.MainWindowViewModel;
import at.fh.tourplanner.viewmodels.TourFormViewModel;
import at.fh.tourplanner.viewmodels.TourListViewModel;

public class ControllerFactory {

    private final TourListViewModel tourListViewModel;

    private final TourFormViewModel tourFormViewModel;

    private final MainWindowViewModel mainWindowViewModel;


    public ControllerFactory() {
        tourFormViewModel = new TourFormViewModel();
        tourListViewModel = new TourListViewModel();
        mainWindowViewModel = new MainWindowViewModel(tourFormViewModel,tourListViewModel);
    }

    public Object create(Class<?> controllerClass) {
        if (controllerClass == TourFormController.class){
            return new TourFormController(tourFormViewModel);
        } else if (controllerClass == TourListController.class) {
            return new TourListController(tourListViewModel);
        }
        throw new IllegalArgumentException("Unknown Controller Class");
    }

    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }
}
