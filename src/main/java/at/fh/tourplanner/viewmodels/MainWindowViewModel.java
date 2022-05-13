package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.listenerInterfaces.*;
import at.fh.tourplanner.model.Tour;

public class MainWindowViewModel {

    private TourFormViewModel tourFormViewModel;

    private TourListViewModel tourListViewModel;

    private SearchBarViewModel searchBarViewModel;

    public MainWindowViewModel(TourFormViewModel tourFormViewModel, TourListViewModel tourListViewModel, SearchBarViewModel searchBarViewModel){
        this.tourFormViewModel = tourFormViewModel;
        this.tourListViewModel = tourListViewModel;
        this.searchBarViewModel = searchBarViewModel;
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

    }


}
