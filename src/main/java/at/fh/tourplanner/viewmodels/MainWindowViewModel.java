package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.listenerInterfaces.FormActionListener;
import at.fh.tourplanner.model.Tour;

public class MainWindowViewModel {

    private TourFormViewModel tourFormViewModel;

    private TourListViewModel tourListViewModel;

    public MainWindowViewModel(TourFormViewModel tourFormViewModel, TourListViewModel tourListViewModel){
        this.tourFormViewModel = tourFormViewModel;
        this.tourListViewModel = tourListViewModel;

        this.tourFormViewModel.addListener(new FormActionListener() {
            @Override
            public void handleFormAction(Tour formData) {
                tourListViewModel.saveTourToList(formData);
            }
        });
    }


}
