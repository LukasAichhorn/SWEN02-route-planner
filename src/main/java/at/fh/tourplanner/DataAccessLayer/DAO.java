package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.Tour;

import java.util.List;
import java.util.UUID;

public interface DAO {
    List<Tour> getAll();

    //T get(UUID uuid);
    void addTour(Tour tour);

    void addCreateListener(DbCreateEvent createEventListener);
}
