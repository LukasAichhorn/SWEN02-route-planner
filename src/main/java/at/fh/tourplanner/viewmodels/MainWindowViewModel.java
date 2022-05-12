package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.listenerInterfaces.*;
import at.fh.tourplanner.model.Tour;

public class MainWindowViewModel {

    private TourFormViewModel tourFormViewModel;

    private TourListViewModel tourListViewModel;

    public MainWindowViewModel(TourFormViewModel tourFormViewModel, TourListViewModel tourListViewModel){
        this.tourFormViewModel = tourFormViewModel;
        this.tourListViewModel = tourListViewModel;
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

    }


}
