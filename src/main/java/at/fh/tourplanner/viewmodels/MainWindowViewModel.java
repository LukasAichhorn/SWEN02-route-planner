package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.listenerInterfaces.*;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class MainWindowViewModel {

    private final TourFormViewModel tourFormViewModel;

    private final TourListViewModel tourListViewModel;

    private final SearchBarViewModel searchBarViewModel;
    private final LogsFormViewModel logsFormViewModel;
    private  final LogListViewModel logListViewModel;

    private final List<NewTourModeActionListener> newTourModeActionListeners = new ArrayList<>();

    public MainWindowViewModel(TourFormViewModel tourFormViewModel, TourListViewModel tourListViewModel, SearchBarViewModel searchBarViewModel, LogsFormViewModel logsFormViewModel,LogListViewModel logListViewModel) {
        this.tourFormViewModel = tourFormViewModel;
        this.tourListViewModel = tourListViewModel;
        this.searchBarViewModel = searchBarViewModel;
        this.logsFormViewModel = logsFormViewModel;
        this.logListViewModel = logListViewModel;


        //Section - listener
        this.tourFormViewModel.addCreateActionListener(new FormActionCreateListener() {
            @Override
            public void handleCreateAction(Tour formData) {
                tourListViewModel.saveTourToList(formData);
            }
        });
        this.tourFormViewModel.addEditActionListener(new FormActionEditListener() {
            @Override
            public void handleEditAction(Tour formData) {
                tourListViewModel.editTour(formData);
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectiontListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                tourFormViewModel.fillFormWithSelection(tour);
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectiontListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                System.out.println("published Tour:");
                System.out.println(tour);
                logListViewModel.setLogs(tour.getLogs());
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectiontListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                System.out.println("Clearing Form:");
                logsFormViewModel.clearForm();
            }
        });
        this.logListViewModel.addListener(new ListItemSelectiontListener<Log>() {
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

        newTourModeActionListeners.add(new NewTourModeActionListener() {
            @Override
            public void handleNewTourModeAction() {
                tourFormViewModel.handleNewTourMode();
            }
        });

    }


    public void publishNewTourModeEvent() {
        for(var listener : newTourModeActionListeners) {
            listener.handleNewTourModeAction();
        }
    }
}
