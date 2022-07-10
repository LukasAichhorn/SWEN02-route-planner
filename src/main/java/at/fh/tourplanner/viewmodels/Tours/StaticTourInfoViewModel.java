package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.Main;
import at.fh.tourplanner.model.Tour;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
@Log4j2
public class StaticTourInfoViewModel {
    private final StringProperty tourName = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty destination = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty tourDistance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");
    private final StringProperty selectedTransportType = new SimpleStringProperty("");
    private final ObjectProperty<Image> imageView =
            new SimpleObjectProperty<>();

    public StaticTourInfoViewModel() {
    }

    public ObjectProperty<Image> getImageView() {
        return imageView;
    }

    public StringProperty getTourName() {
        return tourName;
    }

    public StringProperty getStart() {
        return start;
    }

    public StringProperty getDestination() {
        return destination;
    }

    public StringProperty getDescription() {
        return description;
    }

    public StringProperty getTourDistance() {
        return tourDistance;
    }

    public StringProperty getEstimatedTime() {
        return estimatedTime;
    }

    public StringProperty getSelectedTransportType() {
        return selectedTransportType;
    }

    public void fillTourInfo(Tour tour) {
        if (tour == null) {
            log.error("Error: Empty tour submitted.");
            throw new IllegalArgumentException("Empty tour submitted!");
        }
        tourName.set(tour.getName());
        start.set(tour.getStart());
        destination.set(tour.getDestination());
        description.set(tour.getDescription());
        selectedTransportType.setValue(tour.getTransportType().toString());
        estimatedTime.set(tour.getEstimatedTime());
        tourDistance.set(tour.getDistance());
        setImage(tour.getTourImagePath());


    }

    public void setImage(String imagePath) {
        Image image = new Image(imagePath);
        imageView.set(image);
    }
}

