package at.fh.tourplanner.viewmodels.UiServices;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.DirectionServiceResponse;
import at.fh.tourplanner.businessLayer.mapApiService.DirectionService;
import at.fh.tourplanner.businessLayer.mapApiService.ImageServiceResponse;
import at.fh.tourplanner.businessLayer.mapApiService.TourImageService;
import at.fh.tourplanner.businessLayer.tourService.TourService;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Log4j2
public class UiServiceUpdateTour extends Service<String> {
    private StringProperty id;
    private StringProperty name;
    private StringProperty start;
    private StringProperty end;
    private StringProperty description;
    private ObjectProperty<TransportType> selectedTransportType;
    private final DirectionService directionService;
    private final TourImageService tourImageService;
    private final TourService tourService;

    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            protected String call() {
                log.info("Updating tour with ID: {}", id);
                DirectionServiceResponse dirR = directionService.queryDirection(start.get(), end.get());
                log.info("Updated direction data for tour '{}' fetched", name.get());
                ImageServiceResponse imgR = tourImageService.updateTourImage(start.get(),end.get(),UUID.fromString(id.get()));
                log.info("Updated image for tour '{}' fetched", name.get());

                    Tour newTour = new Tour(
                            UUID.fromString(id.get()),
                            name.get(),
                            start.get(),
                            end.get(),
                            description.get(),
                            selectedTransportType.getValue(),
                            String.valueOf(dirR.getRoute().getDistance()),
                            dirR.getRoute().getFormattedTime(),
                            new ArrayList<>(),
                            imgR.getImagePath()
                    );
                    tourService.updateTourInDatabase(newTour);
                    return "service Task Done";
            }
        };
    }
}
