package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.model.Tour;

import java.util.List;
import java.util.UUID;

public interface DAO_Interface<T> {
    List<Tour> getAll();
    //T get(UUID uuid);
    void addTour(Tour tour);


}
