package at.fh.tourplanner.repositories;

import at.fh.tourplanner.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourRepository {
    private static TourRepository instance = new TourRepository();
    public static TourRepository getInstance(){return instance;}
    private List<Tour> tours = new ArrayList();

    TourRepository() {
        tours.add(new Tour("Roadtrip", "Wien", "Graz", "Fun  fun"));
        tours.add(new Tour("Kurzausflug", "Graz", "Paris", "Fun fun fun"));
        tours.add(new Tour("Trip", "Wien", "Krems", " fun"));

        System.out.println(tours);
    }

    public List<Tour> getAll(){
        return tours;
    }

    public void create(Tour t){
        tours.add(t);
    }



}
