package at.fh.tourplanner.businessLayer;

import javafx.scene.image.Image;

public interface TourImageService {
    ImageServiceResponse queryTourImage(String start, String end);
}
