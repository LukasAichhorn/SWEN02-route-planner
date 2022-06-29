package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.model.Tour;

import java.util.List;

public interface PdfGenerationService {


    void generateTourReport(Tour tour);
    void generateStatisticalReport();


}
