package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;

import java.time.Duration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DAO implements DAO_Interface<Tour> {
    private static DAO instance = new DAO();

    public static DAO getInstance() {
        return instance;
    }

    private List<Tour> inMemoryDatabase = new ArrayList();

    private DAO() {
        ArrayList<Log> testLogs = new ArrayList<>();
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER, Duration.ofHours(1), "Roadtrip 1"));
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER, Duration.ofHours(2), "Roadtrip2"));
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER, Duration.ofHours(1).plusMinutes(30), "roadtrip3"));
        inMemoryDatabase.add(new Tour("Roadtrip", "Wien", "Graz", "Fun  fun", TransportType.BICYCLE, "180 km", "11h", testLogs));
        testLogs.clear();
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER, Duration.ofHours(1), "KurzAusFlug 1"));
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER, Duration.ofHours(2), "KurzAusFlug 2"));
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER, Duration.ofHours(1).plusMinutes(30), "KurzAusFlug 3"));
        inMemoryDatabase.add(new Tour("Kurzausflug", "Graz", "Paris", "Fun fun fun", TransportType.CAR, "1233 km", "12h 30min", testLogs));
        testLogs.clear();
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER,
                Duration.ofHours(1), "Trip 1"));
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER,
                Duration.ofHours(2), "Trip 2"));
        testLogs.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER,
                Duration.ofHours(1).plusMinutes(30), "Trip 3"));
        inMemoryDatabase.add(new Tour("Trip", "Wien", "Krems", " fun", TransportType.PEDESTRIAN, "70 km", "14h", testLogs));
    }

    public List<Tour> getAll() {
        return inMemoryDatabase;
    }

    public void create(Tour t) {
        inMemoryDatabase.add(t);
    }


    public List<Tour> findMatchingTours(String searchString) {
        //get all tours from db
        var tours = this.inMemoryDatabase;
        if (searchString.isEmpty() || searchString == null) {
            return tours;
        }
        return tours.stream()
                .filter(tour -> tour.getName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }
}
