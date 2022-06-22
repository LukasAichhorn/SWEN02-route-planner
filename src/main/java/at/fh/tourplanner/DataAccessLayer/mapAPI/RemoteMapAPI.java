package at.fh.tourplanner.DataAccessLayer.mapAPI;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.RetrofitMapQuestAPI;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Route;
import javafx.scene.image.Image;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class RemoteMapAPI implements MapAPI {

    private final RetrofitMapQuestAPI mapApi;

    public RemoteMapAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mapquestapi.com/")
               .addConverterFactory(JacksonConverterFactory.create())
                .build();

        mapApi = retrofit.create(RetrofitMapQuestAPI.class);
    }


    @Override
    public Route queryDirection(String start, String end) {
        Route result;
        try {
            result = mapApi.getDirection(start, end).execute().body();
            System.out.println( "QueryDirection result: " + result.getRoute().toString());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return new Route();
        }

    }

    @Override
    public BufferedImage queryRouteImage(String start, String end) {
        InputStream result;
        try {
            result = mapApi.getTourImage(start, end).execute().body().byteStream();
            BufferedImage img = ImageIO.read(result);
            System.out.println( "QueryRouteImage result: ");
            System.out.println(img);
            return img;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

