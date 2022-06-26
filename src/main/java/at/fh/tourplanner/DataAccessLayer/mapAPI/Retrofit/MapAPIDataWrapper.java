package at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;

import at.fh.tourplanner.businessLayer.ImageServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MapAPIDataWrapper {
    private DirectionServiceResponse directionServiceResponse;
    private ImageServiceResponse imageServiceResponse;
}
