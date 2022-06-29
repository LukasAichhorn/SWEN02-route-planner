package at.fh.tourplanner.businessLayer.tourService;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.model.Tour;

import java.util.List;
import java.util.UUID;

public class TourService implements TourService_Interface {
    private final DAO database;

    public TourService(DAO database){
        this.database = database;
    }

    @Override
    public List<Tour> getToursFromDatabase() {
        return database.getAllTours();
    }
    @Override
    public void addNewTourToDatabase(Tour newTour) {
        database.addTour(newTour);
    }
    @Override
    public void updateTourInDatabase(Tour tour) {
        database.updateTour(tour);
    }

    @Override
    public void deleteTourInDatabase(UUID id) {
        database.deleteTour(id);
    }

}
