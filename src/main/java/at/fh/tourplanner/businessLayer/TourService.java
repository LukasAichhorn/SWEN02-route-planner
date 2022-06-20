package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.TourRepository;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;

import java.util.List;

public class TourService implements TourService_Interface {

    @Override
    public List<Tour> getCachedTours() {
        return TourRepository.getInstance().getCachedToursList();
    }
}
