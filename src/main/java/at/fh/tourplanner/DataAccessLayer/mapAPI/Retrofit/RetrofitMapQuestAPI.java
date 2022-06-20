package at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RetrofitMapQuestAPI {
    @GET("http://www.mapquestapi.com/directions/v2/route?key=AGCjhc68CSMlcIhD0GQG7ERF263ldMdT&")
    Call<Route> getDirection(@Query("from") String start, @Query("to") String end);
}
