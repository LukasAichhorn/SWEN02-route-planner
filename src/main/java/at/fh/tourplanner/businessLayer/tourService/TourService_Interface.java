package at.fh.tourplanner.businessLayer.tourService;

import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.Tour;

import java.util.List;
import java.util.UUID;

public interface TourService_Interface {
    public List<Tour> getToursFromDatabase();

    void addNewTourToDatabase(Tour newTour);
    void updateTourInDatabase(Tour tour);
    void deleteTourInDatabase(UUID id);
}
