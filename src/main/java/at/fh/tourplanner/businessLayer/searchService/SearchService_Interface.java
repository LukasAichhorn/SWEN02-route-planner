package at.fh.tourplanner.businessLayer.searchService;

import at.fh.tourplanner.model.Tour;

import java.util.List;

public interface SearchService_Interface {
    List<Tour> findMatchingTours(String searchString);
}
