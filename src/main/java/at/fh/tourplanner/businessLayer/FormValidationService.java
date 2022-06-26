package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.model.FormDataNewTour;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.LogFormData;
import at.fh.tourplanner.model.Tour;

public interface FormValidationService {

    boolean noEmptyValues(FormDataNewTour tour);
    boolean noEmptyValues(LogFormData logFormData);

}
