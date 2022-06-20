package at.fh.tourplanner.DataAccessLayer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;


public interface MapApiRestInterface {
    @GET("http://www.mapquestapi.com/directions/v2/route?key=AGCjhc68CSMlcIhD0GQG7ERF263ldMdT&")
    Call<Route> getDirection(@Query("from") String start, @Query("to") String end);
}
