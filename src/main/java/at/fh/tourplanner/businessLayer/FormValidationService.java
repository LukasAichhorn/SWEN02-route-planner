package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.model.Tour;

public interface FormValidationService {

    boolean noEmptyValues(Tour tour);

}
