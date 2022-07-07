package at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;
import at.fh.tourplanner.configuration.AppConfigurationLoader;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface RetrofitMapQuestAPI {

    @GET("http://www.mapquestapi.com/directions/v2/route?")
    Call<DirectionServiceResponse> getDirection(@Query("key") String apiKey, @Query("from") String start, @Query("to") String end);

    @GET("https://www.mapquestapi.com/staticmap/v5/map?")
    Call<ResponseBody> getTourImage(
            @Query("key") String apiKey,
            @Query("size") String size,
            @Query("start") String start,
            @Query("end") String end);
}
