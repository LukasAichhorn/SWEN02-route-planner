package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TourRepository {
    private DAO_Interface<Tour> database = DAO.getInstance();
    private ObservableList<Tour> tours = FXCollections.observableArrayList();

    private static TourRepository instance = new TourRepository();

    public static TourRepository getInstance() {
        return instance;
    }
    private TourRepository(){
        tours.setAll(database.getAll());
    }
    public List<Tour> getCachedToursList(){
        return database.getAll();
    }
    public void addTour(Tour tour){
        database.addTour(tour);
    }
}


