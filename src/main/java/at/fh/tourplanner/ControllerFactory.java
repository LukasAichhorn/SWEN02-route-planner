package at.fh.tourplanner;

import at.fh.tourplanner.DataAccessLayer.PostgresDAO;
import at.fh.tourplanner.DataAccessLayer.mapAPI.RemoteMapAPI;
import at.fh.tourplanner.businessLayer.logService.LogService;
import at.fh.tourplanner.businessLayer.mapApiService.DirectionServiceImpl;
import at.fh.tourplanner.businessLayer.mapApiService.TourImageServiceImpl;
import at.fh.tourplanner.businessLayer.pdfGenerationService.PdfGenerationServiceImpl;
import at.fh.tourplanner.businessLayer.searchService.SearchService;
import at.fh.tourplanner.businessLayer.tourService.TourService;
import at.fh.tourplanner.businessLayer.validationService.FormValidationServiceImp;
import at.fh.tourplanner.controller.Log.LogListController;
import at.fh.tourplanner.controller.Log.LogsFormController;
import at.fh.tourplanner.controller.SearchBarController;
import at.fh.tourplanner.controller.Tour.TourFormController;
import at.fh.tourplanner.controller.Tour.TourListController;
import at.fh.tourplanner.controller.*;
import at.fh.tourplanner.viewmodels.*;
import at.fh.tourplanner.viewmodels.Logs.LogListViewModel;
import at.fh.tourplanner.viewmodels.Logs.LogsFormViewModel;
import at.fh.tourplanner.viewmodels.Tours.StaticTourInfoViewModel;
import at.fh.tourplanner.viewmodels.Tours.TourFormViewModel;
import at.fh.tourplanner.viewmodels.Tours.TourListViewModel;

public class ControllerFactory {

    private final TourListViewModel tourListViewModel;
    private final TourFormViewModel tourFormViewModel;
    private final StaticTourInfoViewModel staticTourInfoViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final LogsFormViewModel logsFormViewModel;
    private final LogListViewModel logListViewModel;
    private final MainWindowViewModel mainWindowViewModel;
    private final MenuBarViewModel menuBarViewModel;

    public ControllerFactory() {
        tourFormViewModel =
                new TourFormViewModel(new DirectionServiceImpl(new RemoteMapAPI()),
                        new TourService(PostgresDAO.getInstance()),
                        new TourImageServiceImpl(new RemoteMapAPI()));
        tourListViewModel = new TourListViewModel(new TourService(PostgresDAO.getInstance()),new SearchService(PostgresDAO.getInstance()));
        searchBarViewModel = new SearchBarViewModel();
        logsFormViewModel = new LogsFormViewModel(new FormValidationServiceImp(),
                new LogService(PostgresDAO.getInstance()));
        logListViewModel = new LogListViewModel(new LogService(PostgresDAO.getInstance()));
        staticTourInfoViewModel = new StaticTourInfoViewModel();
        menuBarViewModel = new MenuBarViewModel(new PdfGenerationServiceImpl(PostgresDAO.getInstance()));
        mainWindowViewModel = new MainWindowViewModel(staticTourInfoViewModel,
                tourFormViewModel, tourListViewModel, searchBarViewModel,
                logsFormViewModel, logListViewModel, menuBarViewModel,
                new TourService(PostgresDAO.getInstance()));
    }

    public Object create(Class<?> controllerClass) {
        if (controllerClass == TourFormController.class) {
            return new TourFormController(tourFormViewModel);
        } else if (controllerClass == TourListController.class) {
            return new TourListController(tourListViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        } else if (controllerClass == LogsFormController.class) {
            return new LogsFormController(logsFormViewModel);
        } else if (controllerClass == LogListController.class) {
            return new LogListController(logListViewModel);
        } else if (controllerClass == MainWindowController.class) {
            return new MainWindowController(mainWindowViewModel);
        } else if (controllerClass == StaticTourInfoController.class) {
            return new StaticTourInfoController(staticTourInfoViewModel);
        } else if (controllerClass == MenuBarController.class) {
            return new MenuBarController(menuBarViewModel);
        }
        throw new IllegalArgumentException("Unknown Controller Class");
    }

    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }
}
