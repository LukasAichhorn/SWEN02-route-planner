package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.businessLayer.TourService;
import at.fh.tourplanner.listenerInterfaces.*;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.viewmodels.Logs.LogListViewModel;
import at.fh.tourplanner.viewmodels.Logs.LogsFormViewModel;
import at.fh.tourplanner.viewmodels.Tours.StaticTourInfoViewModel;
import at.fh.tourplanner.viewmodels.Tours.TourFormViewModel;
import at.fh.tourplanner.viewmodels.Tours.TourListViewModel;

public class MainWindowViewModel {

    private final TourFormViewModel tourFormViewModel;

    private final TourListViewModel tourListViewModel;

    private final SearchBarViewModel searchBarViewModel;
    private final LogsFormViewModel logsFormViewModel;
    private final LogListViewModel logListViewModel;
    private final StaticTourInfoViewModel staticTourInfoViewModel;


    public MainWindowViewModel(StaticTourInfoViewModel staticTourInfoViewModel,
                               TourFormViewModel tourFormViewModel,
                               TourListViewModel tourListViewModel,
                               SearchBarViewModel searchBarViewModel,
                               LogsFormViewModel logsFormViewModel,
                               LogListViewModel logListViewModel, TourService tourService) {
        this.tourFormViewModel = tourFormViewModel;
        this.tourListViewModel = tourListViewModel;
        this.searchBarViewModel = searchBarViewModel;
        this.logsFormViewModel = logsFormViewModel;
        this.logListViewModel = logListViewModel;
        this.staticTourInfoViewModel = staticTourInfoViewModel;


        //Section - listener
        this.tourFormViewModel.addFormActionListener(new FormActionListener() {
            @Override
            public void onAction() {
                tourListViewModel.refreshListView();
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectionListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                tourListViewModel.setEditIsDisabled(false);
                tourFormViewModel.fillFormWithSelection(tour);
            }
        });
        this.tourListViewModel.addOpenBlankFormListener(new OpenBlankTourFormListener() {
            @Override
            public void handleEvent() {
                tourFormViewModel.clearForm();
                tourFormViewModel.openFormInWindow("create");
            }
        });
        this.logListViewModel.addOpenBlankLogFormListener(new OpenBlankLogFormListener(){
            public void handleEvent(){
                logsFormViewModel.clearForm();
                logsFormViewModel.openFormInWindow("create");
            }
        });

        this.tourListViewModel.addOpenFilledFormListener(new OpenFilledTourFormListener() {
            @Override
            public void handleEvent() {
                tourFormViewModel.openFormInWindow("update");
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectionListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                staticTourInfoViewModel.fillTourInfo(tour);
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectionListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                System.out.println("published Tour:");
                System.out.println(tour);
                if (tour != null) {
                    logListViewModel.setLogs(tour.getLogs());
                }
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectionListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                System.out.println("Clearing Form:");
                logsFormViewModel.clearForm();
            }
        });
        this.logListViewModel.addListener(new ListItemSelectionListener<Log>() {
            @Override
            public void fillForm(Log log) {
                logsFormViewModel.fillFormWithSelection(log);
            }
        });

        this.searchBarViewModel.addSearchListener(new SearchListener() {
            @Override
            public void handleSearch(String searchString) {
                tourListViewModel.searchTours(searchString);
            }
        });
    }
}
