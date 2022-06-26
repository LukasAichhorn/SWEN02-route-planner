package at.fh.tourplanner.businessLayer;

import javafx.scene.image.Image;

import java.util.UUID;

public interface TourImageService {
    ImageServiceResponse queryTourImage(String start, String end);
    ImageServiceResponse updateTourImage(String start, String end, UUID id);
}
