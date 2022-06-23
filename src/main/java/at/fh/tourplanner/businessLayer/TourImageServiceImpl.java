package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.DataAccessLayer.mapAPI.MapAPI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TourImageServiceImpl implements TourImageService {
    private final MapAPI mapAPI;

    public TourImageServiceImpl(MapAPI mapAPI) {
        this.mapAPI = mapAPI;
    }

    @Override
    public ImageServiceResponse queryTourImage(String start, String end) {
        //buffered image to file and return location
        UUID newID = UUID.randomUUID();
        return new ImageServiceResponse(
                imageToFile(mapAPI.queryRouteImage(start, end),newID),
                newID);
    }

    private static Image convertToFxImage(BufferedImage image) {
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

    private String imageToFile(BufferedImage img,UUID newID) {
        File outputfile = new File(newID + ".jpg");
        try {
            ImageIO.write(img, "jpg", outputfile);
            System.out.println("imagefile: " + outputfile.getPath() + " created");
            return outputfile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }
}
