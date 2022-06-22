package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.mapAPI.MapAPI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class TourImageServiceImpl implements TourImageService{
    private final MapAPI mapAPI;

    public TourImageServiceImpl(MapAPI mapAPI) {
        this.mapAPI = mapAPI;
    }
    @Override
    public Image queryTourImage(String start, String end) {
       return convertToFxImage(mapAPI.queryRouteImage(start,end));

    }private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}
