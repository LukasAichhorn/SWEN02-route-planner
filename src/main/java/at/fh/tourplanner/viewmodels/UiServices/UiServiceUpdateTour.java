package at.fh.tourplanner.viewmodels.UiServices;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.DirectionServiceResponse;
import at.fh.tourplanner.businessLayer.DirectionService;
import at.fh.tourplanner.businessLayer.ImageServiceResponse;
import at.fh.tourplanner.businessLayer.TourImageService;
import at.fh.tourplanner.businessLayer.TourService;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@AllArgsConstructor
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
                System.out.println("edit tour with id: " + id);
                System.out.println("Updating ID: " + id + "in thread");
                DirectionServiceResponse dirR = directionService.queryDirection(start.get(), end.get());
                System.out.println("Fetching direction data is done");
                ImageServiceResponse imgR = tourImageService.updateTourImage(start.get(),end.get(),UUID.fromString(id.get()));
                System.out.println("fetching image data is done");

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
