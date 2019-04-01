package id.radityo.moviedatabase;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

    @GET("popular")
    Call<ResponseBody> popularItem(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<ResponseBody> ratedItem(@Query("api_key") String apiKey);

    @GET("reviews")
    Call<ResponseBody> reviewItem(@Query("api_key") String apiKey);

    @GET("trailers")
    Call<ResponseBody> trailersItem(@Query("api_key") String apiKey);

    @GET("{movieId}")
    Call<ResponseBody> detailsItem(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey);
}
