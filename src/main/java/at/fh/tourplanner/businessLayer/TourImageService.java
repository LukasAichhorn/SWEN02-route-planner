package at.fh.tourplanner.businessLayer;

import javafx.scene.image.Image;

public interface TourImageService {
    Image queryTourImage(String start, String end);
}
