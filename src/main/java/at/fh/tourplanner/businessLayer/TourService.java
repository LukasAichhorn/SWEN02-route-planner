package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.Tour;

import java.util.List;

public class TourService implements TourService_Interface {
    private final DAO database;
    public TourService(DAO database){
        this.database = database;
    }

    @Override
    public List<Tour> getToursFromDatabase() {
        return database.getAll();
    }

    @Override
    public void addNewTourToDatabase(Tour newTour) {
        database.addTour(newTour);
    }
    @Override
    public  void addCreateListener(DbCreateEvent event){
        database.addCreateListener(event);
    }
}
