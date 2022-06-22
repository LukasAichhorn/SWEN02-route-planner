package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.Tour;

import java.util.List;

public interface TourService_Interface {
    public List<Tour> getToursFromDatabase();

    void addNewTourToDatabase(Tour newTour);
    void addCreateListener(DbCreateEvent event);
}
