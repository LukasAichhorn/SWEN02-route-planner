package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.businessLayer.pdfGenerationService.PdfGenerationService;
import at.fh.tourplanner.model.Tour;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.extern.log4j.Log4j2;

import java.time.LocalTime;

@Log4j2
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
    }

    public void createTourReport() {
        tourReportUIService.restart();
    }

    public void setIsItemSelected(boolean b) {
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
                    pdfGenerationService.generateStatisticalReport();
                    log.info("Statistical report created.");
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
                    log.info("Tour report for '{}' generated.", currentSelection.getName());
                    return null;
                }
            };
        }
    }
}
