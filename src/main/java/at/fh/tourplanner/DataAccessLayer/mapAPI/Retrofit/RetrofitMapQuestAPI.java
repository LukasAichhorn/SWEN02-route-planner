package at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RetrofitMapQuestAPI {
    @GET("http://www.mapquestapi.com/directions/v2/route?key=AGCjhc68CSMlcIhD0GQG7ERF263ldMdT&")
    Call<DirectionServiceResponse> getDirection(@Query("from") String start, @Query("to") String end);

    @GET("https://www.mapquestapi.com/staticmap/v5/map?key=AGCjhc68CSMlcIhD0GQG7ERF263ldMdT&size=@2x&")
    Call<ResponseBody> getTourImage(@Query("start") String start,
                                    @Query("end") String end);
}
