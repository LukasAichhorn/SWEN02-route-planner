package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.repositories.TourRepository;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TourListViewModel {

    private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
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

    public void addListener(ChangeListener listener) {
        this.changeListeners.add(listener);
    }

}
