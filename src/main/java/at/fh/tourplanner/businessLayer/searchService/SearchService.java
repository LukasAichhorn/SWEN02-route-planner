package at.fh.tourplanner.businessLayer.searchService;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.model.Tour;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService implements  SearchService_Interface {
    private final DAO database;

    public SearchService(DAO database){
        this.database = database;
    }
    @Override
    public List<Tour> findMatchingTours(String searchString) {
        var tours = database.getAllTours();
        return tours.stream()
                .filter(tour -> tour.getName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }
}
