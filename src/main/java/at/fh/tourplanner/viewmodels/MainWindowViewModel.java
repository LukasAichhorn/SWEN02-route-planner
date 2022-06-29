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

import java.util.List;

public class MainWindowViewModel {

    private final TourFormViewModel tourFormViewModel;
    private final TourListViewModel tourListViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final LogsFormViewModel logsFormViewModel;
    private final LogListViewModel logListViewModel;
    private final StaticTourInfoViewModel staticTourInfoViewModel;
    private final MenuBarViewModel menuBarViewModel;


    public MainWindowViewModel(StaticTourInfoViewModel staticTourInfoViewModel,
                               TourFormViewModel tourFormViewModel,
                               TourListViewModel tourListViewModel,
                               SearchBarViewModel searchBarViewModel,
                               LogsFormViewModel logsFormViewModel,
                               LogListViewModel logListViewModel,
                               MenuBarViewModel menuBarViewModel,
                               TourService tourService) {
        this.tourFormViewModel = tourFormViewModel;
        this.tourListViewModel = tourListViewModel;
        this.searchBarViewModel = searchBarViewModel;
        this.logsFormViewModel = logsFormViewModel;
        this.logListViewModel = logListViewModel;
        this.staticTourInfoViewModel = staticTourInfoViewModel;
        this.menuBarViewModel = menuBarViewModel;


        //Section - listener
        this.tourFormViewModel.addFormActionListener(new FormActionListener() {
            @Override
            public void onAction() {
                tourListViewModel.refreshListView();
            }
        });
        this.logsFormViewModel.addFormActionListener(new FormActionListener() {
            @Override
            public void onAction() {
                var selectedTourID = tourListViewModel.getCurrentSelection().getPostgresID();
                logListViewModel.setLogs(selectedTourID);
            }
        });
        this.tourListViewModel.addSelectionListener(new ListItemSelectionListener<Tour>() {
            @Override
            public void handleListItemSelection(Tour tour) {
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
                var selectedTourID = tourListViewModel.getCurrentSelection().getPostgresID();
                logsFormViewModel.setTourID(selectedTourID);
                logsFormViewModel.openFormInWindow("create");
            }
        });

        this.tourListViewModel.addOpenFilledFormListener(new OpenFilledTourFormListener() {
            @Override
            public void handleEvent() {
                tourFormViewModel.openFormInWindow("update");
            }
        });
        this.logListViewModel.addOpenFilledLogFormListener(new OpenFilledLogFormListener(){
            public void handleEvent(){
                var selectedTourID = tourListViewModel.getCurrentSelection().getPostgresID();
                logsFormViewModel.setTourID(selectedTourID);
                logsFormViewModel.openFormInWindow("update");
            }
        });
        this.tourListViewModel.addSelectionListener(new ListItemSelectionListener<Tour>() {
            @Override
            public void handleListItemSelection(Tour tour) {
                staticTourInfoViewModel.fillTourInfo(tour);
            }
        });
        this.tourListViewModel.addSelectionListener(new ListItemSelectionListener<Tour>() {
            @Override
            public void handleListItemSelection(Tour tour) {
                System.out.println("published Tour:");
                System.out.println(tour);
                if(tour == null){
                    logListViewModel.setCreateLogIsDisabled(true);
                    logListViewModel.clearLogsList();
                }
                if (tour != null) {
                    var selectedTourID = tourListViewModel.getCurrentSelection().getPostgresID();
                    logListViewModel.setCreateLogIsDisabled(false);
                    logListViewModel.setLogs(selectedTourID);
                }

            }
        });
        this.logListViewModel.addListener(new ListItemSelectionListener<Log>() {
            @Override
            public void handleListItemSelection(Log log) {
                var selectedTourID = tourListViewModel.getCurrentSelection().getPostgresID();
                System.out.println("current selected postgresID : " + selectedTourID );
                logListViewModel.setEditIsDisabled(false);
                logsFormViewModel.fillFormWithSelection(log,selectedTourID);
                if(log == null){
                    logListViewModel.setEditIsDisabled(true);
                }
            }
        });

        this.tourListViewModel.addSelectionListener(new ListItemSelectionListener<Tour>() {
            @Override
            public void handleListItemSelection(Tour tour) {
                menuBarViewModel.setCurrentSelection(tour);
                menuBarViewModel.setIsItemSelected(true);
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
