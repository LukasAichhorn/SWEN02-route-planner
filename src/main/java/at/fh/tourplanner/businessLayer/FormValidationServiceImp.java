package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.model.Tour;

public class FormValidationServiceImp implements  FormValidationService{
    @Override
    public boolean noEmptyValues(Tour tour) {
        if(tour.getName().isBlank()) return false;
       // if(tour.getStart().isBlank()) return false;
       // if(tour.getDestination().isBlank()) return false;
       // if(tour.getDescription().isBlank()) return false;
       // if(tour.getTransportType()) return false;
       // if(tour.getName().isBlank()) return false;
        return true;
        }
}

