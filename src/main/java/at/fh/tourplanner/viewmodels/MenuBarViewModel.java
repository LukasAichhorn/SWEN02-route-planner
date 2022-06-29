package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.businessLayer.PdfGenerationService;
import at.fh.tourplanner.businessLayer.PdfGenerationServiceImpl;
import at.fh.tourplanner.model.Tour;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalTime;
import java.util.List;

public class MenuBarViewModel {

    private final BooleanProperty isTourSelected = new SimpleBooleanProperty(false);
    private final PdfGenerationService pdfGenerationService;
    private Tour currentSelection;
    private StatisticalReportUIService statisticalReportUIService;
    private TourReportUIService tourReportUIService;

    public MenuBarViewModel(PdfGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
        this.statisticalReportUIService = new StatisticalReportUIService();
        this.tourReportUIService = new TourReportUIService();

    }

    public Property<Boolean> getIsTourSelected() {
        return this.isTourSelected;
    }
    public void createStatisticalReport() {
        statisticalReportUIService.restart();
        System.out.println("Statistical Report");
    }

    public void createTourReport() {
        tourReportUIService.restart();
    }

    public void setIsItemSelected(boolean b) {
        System.out.println(b + ": tour is selected");
        isTourSelected.set(b);
    }

    public void setCurrentSelection(Tour tour) {
        this.currentSelection = tour;
    }

    public class StatisticalReportUIService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {

            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("started creating stat report at " + LocalTime.now());
                    pdfGenerationService.generateStatisticalReport();
                    System.out.println("finished creating stat report at " + LocalTime.now());
                    return null;
                }
            };
        }
    }
    public class TourReportUIService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {

            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    pdfGenerationService.generateTourReport(currentSelection);
                    return null;
                }
            };
        }
    }
}
