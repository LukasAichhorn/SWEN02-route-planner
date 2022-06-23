package at.fh.tourplanner.businessLayer;

import java.util.UUID;
import lombok.*;
@AllArgsConstructor
@Setter
@Getter
public class ImageServiceResponse {
    private String imagePath;
    private UUID    generatedId;
}
