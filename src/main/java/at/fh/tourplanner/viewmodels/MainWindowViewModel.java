package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.listenerInterfaces.*;
import at.fh.tourplanner.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class MainWindowViewModel {

    private final TourFormViewModel tourFormViewModel;

    private final TourListViewModel tourListViewModel;

    private final SearchBarViewModel searchBarViewModel;
    private final LogsViewModel logsViewModel;

    private final List<NewTourModeActionListener> newTourModeActionListeners = new ArrayList<>();

    public MainWindowViewModel(TourFormViewModel tourFormViewModel, TourListViewModel tourListViewModel, SearchBarViewModel searchBarViewModel, LogsViewModel logsViewModel) {
        this.tourFormViewModel = tourFormViewModel;
        this.tourListViewModel = tourListViewModel;
        this.searchBarViewModel = searchBarViewModel;
        this.logsViewModel = logsViewModel;


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
        this.tourListViewModel.addListener(new ListItemSelectiontListener() {
            @Override
            public void fillForm(Tour tour) {
                tourFormViewModel.fillFormWithSelection(tour);
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
