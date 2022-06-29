package at.fh.tourplanner.businessLayer.validationService;

import at.fh.tourplanner.businessLayer.validationService.FormValidationService;
import at.fh.tourplanner.model.FormDataNewTour;
import at.fh.tourplanner.model.LogFormData;

public class FormValidationServiceImp implements FormValidationService {
    @Override
    public boolean noEmptyValues(FormDataNewTour tour) {
        if(tour.getTourName().isBlank()) return false;
        if(tour.getStart().isBlank()) return false;
        if(tour.getEnd().isBlank()) return false;
       if(tour.getDescription().isBlank()) return false;
       if(tour.getTransportType() == null) return false;
        return true;
        }

    @Override
    public boolean noEmptyValues(LogFormData logFormData) {
        return true;
    }
}

