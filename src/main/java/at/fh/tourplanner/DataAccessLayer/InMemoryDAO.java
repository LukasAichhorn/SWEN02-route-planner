package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class InMemoryDAO implements DAO {
    private static final InMemoryDAO instance = new InMemoryDAO();
    private final List<DbCreateEvent> createListeners = new ArrayList<>();

    public static InMemoryDAO getInstance() {
        return instance;
    }


    private final List<Tour> toursDatabase = new ArrayList<>();

 private InMemoryDAO() {
//        ArrayList<Log> testLogs1 = new ArrayList<>();
//        testLogs1.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER,
//                Duration.ofHours(1), "Roadtrip 1"));
//        testLogs1.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER,
//                Duration.ofHours(2), "Roadtrip2"));
//        testLogs1.add(new Log(LocalDate.now(), 4, DifficultyTier.BEGINNER,
//                Duration.ofHours(1).plusMinutes(30), "roadtrip3"));
//        inMemoryDatabase.add(new Tour("Roadtrip", "Wien", "Graz", "Fun  fun",
//                TransportType.BICYCLE, "180 km", "11h", testLogs1,null));
//        ArrayList<Log> testLogs2 = new ArrayList<>();
//        testLogs2.add(new Log(LocalDate.now(), 4, DifficultyTier.ADVANCED,
//                Duration.ofHours(1),
//                "KurzAusFlug 1"));
//        testLogs2.add(new Log(LocalDate.now(), 4, DifficultyTier.ADVANCED,
//                Duration.ofHours(2),
//                "KurzAusFlug 2"));
//        testLogs2.add(new Log(LocalDate.now(), 4, DifficultyTier.ADVANCED,
//                Duration.ofHours(1).plusMinutes(30), "KurzAusFlug 3"));
//        inMemoryDatabase.add(new Tour("Kurzausflug", "Graz", "Paris", "Fun fun fun",
//                TransportType.CAR, "1233 km", "12h 30min", testLogs2,null));
//        ArrayList<Log> testLogs3 = new ArrayList<>();
//        testLogs3.add(new Log(LocalDate.now(), 4, DifficultyTier.MASTER,
//                Duration.ofHours(1), "Trip 1"));
//        testLogs3.add(new Log(LocalDate.now(), 4, DifficultyTier.MASTER,
//                Duration.ofHours(2), "Trip 2"));
//        testLogs3.add(new Log(LocalDate.now(), 4, DifficultyTier.MASTER,
//                Duration.ofHours(1).plusMinutes(30), "Trip 3"));
//        inMemoryDatabase.add(new Tour("Trip", "Wien", "Krems", " fun",
//                TransportType.PEDESTRIAN, "70 km", "14h", testLogs3,null));
    }

    public List<Tour> getAllTours() {
        return toursDatabase;
    }

    @Override
    public List<Log> getAllLogsForTour(int TourID) {
     List<Log> logs = new ArrayList<>();
        for (int i = 0; i < toursDatabase.size(); i++) {
            if(toursDatabase.get(i).getPostgresID() == TourID){
                logs.addAll(toursDatabase.get(i).getLogs());
            }
        }
     return logs;
    }

    @Override
    public void addTour(Tour tour) {
        System.out.println("inside database");
        try{
            System.out.println("simulate latency start");
            sleep(300);
            System.out.println("simulate latency done");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        this.toursDatabase.add(tour);
    }

    @Override
    public void updateTour(Tour tour) {
        for (int i = 0; i < toursDatabase.size(); i++) {
            if(toursDatabase.get(i).getUUID() == tour.getUUID()){
                toursDatabase.set(i, tour);
            }
        }

    }

    @Override
    public void deleteTour(UUID id) {
        for (int i = 0; i < toursDatabase.size(); i++) {
            if(toursDatabase.get(i).getUUID() == id){
                toursDatabase.remove(i);
            }
        }

    }

    @Override
    public void addLog(Log log) {
        for (Tour tour : toursDatabase) {
            if (tour.getPostgresID() == log.getTourID()) {
                tour.getLogs().add(log);
            }
        }



    }

    @Override
    public void updateLog(Log log) {
        for (int i = 0; i < toursDatabase.size(); i++) {
            if(toursDatabase.get(i).getPostgresID() == log.getTourID()){
                for (int j = 0; j < toursDatabase.get(i).getLogs().size(); j++) {
                    if(toursDatabase.get(i).getLogs().get(j).getId() == log.getId()){
                        toursDatabase.get(i).getLogs().set(j, log);
                    }
                }
            }
        }
    }

    @Override
    public void deleteLog(int id) {

    }

    public void create(Tour t) {
        toursDatabase.add(t);
    }


    public List<Tour> findMatchingTours(String searchString) {
        //get all tours from db
        var tours = this.toursDatabase;
        if (searchString.isEmpty() || searchString == null) {
            return tours;
        }
        return tours.stream()
                .filter(tour -> tour.getName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }
}
