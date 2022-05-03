package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.repositories.TourRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TourListViewModel {
    ObservableList<Tour> tours = FXCollections.observableArrayList();
    public ObservableList<Tour> getTours() {
        return tours;
    }

    public TourListViewModel() {
        setTours(TourRepository.getInstance().getAll());
        System.out.println(tours);
    }

    public void setTours(List<Tour> tourList) {
        tours.clear();
        tours.addAll(tourList);

    }

}
