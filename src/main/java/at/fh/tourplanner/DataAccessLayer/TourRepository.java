package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TourRepository {
    private static TourRepository instance = new TourRepository();
    public static TourRepository getInstance(){return instance;}
    private List<Tour> tours = new ArrayList();

    TourRepository() {
        tours.add(new Tour("Roadtrip", "Wien", "Graz", "Fun  fun", TransportType.BICYCLE, "180 km", "11h"));
        tours.add(new Tour("Kurzausflug", "Graz", "Paris", "Fun fun fun", TransportType.CAR, "1233 km", "12h 30min"));
        tours.add(new Tour("Trip", "Wien", "Krems", " fun", TransportType.PEDESTRIAN, "70 km", "14h"));
    }

    public List<Tour> getAll(){
        return tours;
    }

    public void create(Tour t){
        tours.add(t);
    }


    public List<Tour> findMatchingTours(String searchString) {
      //get all tours from db
      var tours  = this.tours;
      if(searchString.isEmpty() || searchString == null){
          return tours;
      }
      return tours.stream()
              .filter(tour-> tour.getName().toLowerCase().contains(searchString.toLowerCase()))
              .collect(Collectors.toList());
    }
}
