package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;

import java.util.List;
import java.util.UUID;

public interface DAO {
    List<Tour> getAllTours();
    List<Log> getAllLogsForTour(int TourID);

    //T get(UUID uuid);
    void addTour(Tour tour);
    void updateTour(Tour tour);
    void deleteTour(UUID id);
    void addLog(Log log);
    void updateLog(Log log);
    void deleteLog(int id);



}
