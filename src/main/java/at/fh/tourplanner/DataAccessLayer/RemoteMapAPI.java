package at.fh.tourplanner.DataAccessLayer;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoteMapAPI implements MapAPI {

    private final MapApiRestInterface mapApi;

    public RemoteMapAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mapquestapi.com/")
               .addConverterFactory(JacksonConverterFactory.create())
                .build();

        mapApi = retrofit.create(MapApiRestInterface.class);
    }


    @Override
    public Route queryDirection(String start, String end) {
        Route result;
        try {
            result = mapApi.getDirection(start, end).execute().body();
            System.out.println(result.getRoute().toString());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return new Route();
        }

    }
}

