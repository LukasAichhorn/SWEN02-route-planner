package at.fh.tourplanner.businessLayer.mapApiService;

import at.fh.tourplanner.businessLayer.mapApiService.ImageServiceResponse;

import java.util.UUID;

public interface TourImageService {
    ImageServiceResponse queryTourImage(String start, String end);
    ImageServiceResponse updateTourImage(String start, String end, UUID id);
}
