package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.model.FormDataNewTour;
import at.fh.tourplanner.model.Tour;

public class FormValidationServiceImp implements  FormValidationService{
    @Override
    public boolean noEmptyValues(FormDataNewTour tour) {
        if(tour.getTourName().isBlank()) return false;
        if(tour.getStart().isBlank()) return false;
        if(tour.getEnd().isBlank()) return false;
       if(tour.getDescription().isBlank()) return false;
       if(tour.getTransportType() == null) return false;
        return true;
        }
}

