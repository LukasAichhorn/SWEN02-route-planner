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
        BufferedImage bufferedImage = mapAPI.queryRouteImage(start, end);
        return new ImageServiceResponse(
                imageToFile(bufferedImage,newID),
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
        String location = "C:/Users/Lukas/Documents/01_UNI/04_SEM/SWEN02/";
        File outputfile = new File(location + newID + ".jpg");
        try {
            ImageIO.write(img, "jpg", outputfile);
            System.out.println("imagefile: " + outputfile.getAbsolutePath() + " created");
            return outputfile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }
}
