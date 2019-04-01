package id.radityo.moviedatabase;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class ApiClient {
    public static String baseUrl = "https://api.themoviedb.org/3/movie/";
    public static String baseUrlCrop = "https://api.themoviedb.org/3/";
    public static String baseImage = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static APIInterface getProduct() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(APIInterface.class);
    }

    public static APIInterface getReviews(int movieId) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl + movieId + "/")
                .build();
        return retrofit.create(APIInterface.class);
    }

    public static APIInterface getTrailers(int movieId) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl + movieId + "/")
                .build();
        return retrofit.create(APIInterface.class);
    }

    public static APIInterface getDetails(int movieId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        service.detailsItem(movieId, "ce64212de916313a39e9490b889fb667");
        return service;
    }

    /*public static APIInterface getDetails(int movieId) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl + movieId + "/")
                .build();
        return retrofit.create(APIInterface.class);
    }*/
}
