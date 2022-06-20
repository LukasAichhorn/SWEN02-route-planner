package at.fh.tourplanner.DataAccessLayer.mapAPI;

import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.RetrofitMapQuestAPI;
import at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit.Route;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

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
}

