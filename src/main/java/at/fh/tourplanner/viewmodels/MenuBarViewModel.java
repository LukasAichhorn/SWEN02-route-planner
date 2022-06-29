package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.businessLayer.PdfGenerationService;
import at.fh.tourplanner.businessLayer.PdfGenerationServiceImpl;
import at.fh.tourplanner.model.Tour;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.List;

public class MenuBarViewModel {

    private final BooleanProperty isTourSelected = new SimpleBooleanProperty(false);
    private final PdfGenerationService pdfGenerationService;
    private Tour currentSelection;
    private List<Tour> tours;

    public MenuBarViewModel(PdfGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;

    }


    public Property<Boolean> getIsTourSelected() {
        return this.isTourSelected;
    }
    public void createStatisticalReport() {
        pdfGenerationService.generateStatisticalReport(tours);
        System.out.println("Statistical Report");
    }

    public void createTourReport() {
        pdfGenerationService.generateTourReport(currentSelection);
        System.out.println("tour report");
    }

    public void setIsItemSelected(boolean b) {
        System.out.println(b + ": tour is selected");
        isTourSelected.set(b);
    }

    public void setCurrentSelection(Tour tour) {
        this.currentSelection = tour;
    }

    public void updateTours(List<Tour> tours) {
        this.tours = tours;
    }
}
