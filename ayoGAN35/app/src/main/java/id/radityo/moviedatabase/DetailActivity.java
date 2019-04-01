package id.radityo.moviedatabase;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.radityo.moviedatabase.Database.MovieModel;
import id.radityo.moviedatabase.Database.RealmHelper;
import id.radityo.moviedatabase.Review.ReviewAdapter;
import id.radityo.moviedatabase.Review.Reviewer;
import id.radityo.moviedatabase.Trailer.Quicktime.QuicktimeAdapter;
import id.radityo.moviedatabase.Trailer.Quicktime.Quicktimes;
import id.radityo.moviedatabase.Trailer.Youtube.YoutubeAdapter;
import id.radityo.moviedatabase.Trailer.Youtube.Youtubes;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    public final String API_KEY = "ce64212de916313a39e9490b889fb667";
    private static final String TAG = "movie";
    TextView tvYear, tvDuration, tvRating, tvSinopsis, tvNoTrailer, tvTitle, tvNoReview;
    RoundedImageView rivImage;
    Button addFavorite;
    ProgressBar progressBarReview, progressBarTrailer;

    RecyclerView rvReview, rvYoutube, rvQuicktime;
    List<Reviewer> reviewsList = new ArrayList<>(); // TODO: perubahan dari list ke realmlist
    ReviewAdapter reviewAdapter;
    List<Youtubes> youtubesList = new ArrayList<>(); //youtube's List
    YoutubeAdapter youtubeAdapter;
    List<Quicktimes> quicktimesList = new ArrayList<>();    //quicktime's List
    QuicktimeAdapter quicktimeAdapter;

    RealmList<Reviewer> reviewers;
    List<Reviewer> list;
    RealmResults<Reviewer> results;
    RealmResults<Youtubes> resultsYoutube;
    RealmResults<Quicktimes> resultsQuicktime;

    Realm realm;
    MovieModel movieModel = new MovieModel();
    RealmHelper realmHelper;
    public int movieId;
    public String name;
    private boolean ada = movieModel.isExisting();

    //AdapterFavorit adapterFavorit = new AdapterFavorit(new Or, true, quicktimesList, this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Realm
        Realm.init(DetailActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        //Getting intent
        final String title = getIntent().getStringExtra("title");
        final String posterPath = getIntent().getStringExtra("poster_path");
        final String year = getIntent().getStringExtra("year");
        final String sinopsis = getIntent().getStringExtra("sinopsis");
        final float rate = getIntent().getFloatExtra("rate", 0);
        movieId = getIntent().getIntExtra("movie_id", 0);

        //Logging
        Log.e(TAG, "###movieId: " + movieId);
        Log.e(TAG, "###title: " + title);
        Log.e(TAG, "###year: " + year);
        Log.e(TAG, "###rate: " + rate);
        Log.e(TAG, "###posterPath: " + posterPath);

        //Customize action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setElevation(0f);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        LinearLayout llNggantung = findViewById(R.id.ll_nggantung);
        llNggantung.setElevation(5f);

        //Finding view
        tvTitle = findViewById(R.id.tv_title_detail);
        tvDuration = findViewById(R.id.tv_duration_detail);
        tvRating = findViewById(R.id.tv_rate_detail);
        tvSinopsis = findViewById(R.id.tv_sinopsis_detail);
        tvYear = findViewById(R.id.tv_year_detail);
        tvNoReview = findViewById(R.id.tv_no_review);
        tvNoTrailer = findViewById(R.id.tv_no_trailer);
        rivImage = findViewById(R.id.iv_image_detail);
        addFavorite = findViewById(R.id.bt_add_favorite);
        rvReview = findViewById(R.id.rv_review);
        rvYoutube = findViewById(R.id.rv_youtube);
        rvQuicktime = findViewById(R.id.rv_quicktime);
        progressBarReview = findViewById(R.id.progress_bar_review);
        progressBarTrailer = findViewById(R.id.progress_bar_trailer);
        tvNoTrailer.setVisibility(View.GONE);
        tvNoReview.setVisibility(View.GONE);

        rvReview.setLayoutManager(new LinearLayoutManager(this, 1, false));
        rvReview.setHasFixedSize(true);
        rvYoutube.setLayoutManager(new LinearLayoutManager(this, 1, false));
        rvYoutube.setHasFixedSize(true);
        rvQuicktime.setLayoutManager(new LinearLayoutManager(this, 1, false));
        rvQuicktime.setHasFixedSize(true);

//        reviewsList = realmHelper.getAllReviews();
//        youtubesList = realmHelper.ge

        if (!reviewsList.isEmpty()) {
            progressBarReview.setVisibility(View.GONE);
        }
        if (!youtubesList.isEmpty() || !quicktimesList.isEmpty()) {
            progressBarTrailer.setVisibility(View.GONE);
        }
        //Realm Result
        results = realm.where(Reviewer.class).equalTo("movieId", movieId).findAll();
        results.load();
        resultsYoutube = realm.where(Youtubes.class).equalTo("movieId", movieId).findAll();
        resultsYoutube.load();
        resultsQuicktime = realm.where(Quicktimes.class).equalTo("movieId", movieId).findAll();
        resultsQuicktime.load();

        hitReviewsItem(API_KEY);    //hit review
        hitTrailersItem(API_KEY);   //hit trailer
        hitDetailsItem(API_KEY);    //hit details

        reviewsList.addAll(realmHelper.findAll(Reviewer.class, "movieId", movieId));
        youtubesList.addAll(realmHelper.findAllTrailers(Youtubes.class, "name", name));
        quicktimesList.addAll(realmHelper.findAllTrailers(Quicktimes.class, "name", name));

        //Checking Condition and OnClick
        /**Jika movieId ternyata ada
         di database button dan aksinya diubah**/

        if (realm.where(MovieModel.class).equalTo("movieId", movieId).count() > 0) {
            movieModel.setExisting(true);
            addFavorite.setText(getString(R.string.del_from_favorite));
            addFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (movieModel.isExisting()) {
                        realmHelper.delete(movieId);
                        movieModel.setExisting(false);
                        addFavorite.setText(getString(R.string.add_to_favorite));
                        Log.e(TAG, "### delete");
                        Snackbar.make(v, title + " dihapus dari favorit", Snackbar.LENGTH_LONG).show();
                    } else {
                        if (!title.isEmpty() && movieId != 0) {
                            movieModel.setMovieId(movieId);
                            movieModel.setTitle(title);
                            movieModel.setYear(year);
                            movieModel.setPosterPath(posterPath);
                            movieModel.setSinopsis(sinopsis);
                            movieModel.setRating(rate);
                            movieModel.setExisting(true);

                            realmHelper.save(movieModel);
                            realmHelper.saveObject(reviewsList);
                            realmHelper.saveObject(youtubesList);
                            realmHelper.saveObject(quicktimesList);
                            addFavorite.setText(getString(R.string.del_from_favorite));
                            Snackbar.make(v, title + " ditambahkan ke favorit", Snackbar.LENGTH_LONG).show();
                            Log.e("movie", "### saveObject reviews : " + reviewsList);
                            Log.e("movie", "### saveObject youtube : " + youtubesList);
                            Log.e("movie", "### saveObject quicktime : " + quicktimesList);
                        } else {
                            Toast.makeText(DetailActivity.this, "Tidak dapat menambahkan", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            movieModel.setExisting(false);
            addFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!movieModel.isExisting()) {
                        if (!title.isEmpty() && movieId != 0) {
                            movieModel.setMovieId(movieId);
                            movieModel.setTitle(title);
                            movieModel.setYear(year);
                            movieModel.setPosterPath(posterPath);
                            movieModel.setSinopsis(sinopsis);
                            movieModel.setRating(rate);
                            movieModel.setExisting(true);

                            realmHelper.save(movieModel);
                            realmHelper.saveObject(reviewsList);
                            realmHelper.saveObject(youtubesList);
                            realmHelper.saveObject(quicktimesList);
                            addFavorite.setText(getString(R.string.del_from_favorite));
                            Snackbar.make(v, title + " ditambahkan ke favorit", Snackbar.LENGTH_LONG).show();
                            Log.e(TAG, "### add");
                            Log.e("movie", "### saveObject reviews : " + reviewsList);
                            Log.e("movie", "### saveObject youtube : " + youtubesList);
                            Log.e("movie", "### saveObject quicktime : " + quicktimesList);
                        } else {
                            Toast.makeText(DetailActivity.this, "Tidak dapat menambahkan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        realmHelper.delete(movieId);
                        movieModel.setExisting(false);
                        addFavorite.setText(getString(R.string.add_to_favorite));
                        Snackbar.make(v, title + " dihapus dari favorit", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }

//            addFavorite.setText(getString(R.string.del_from_favorite));
//            addFavorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (movieModel.isExisting()) {             // jika sudah ada
//                        realmHelper.delete(movieId);
//                        movieModel.setExisting(false);
//                        addFavorite.setText(getString(R.string.add_to_favorite));
//                        Snackbar.make(v, title + " dihapus dari favorit", Snackbar.LENGTH_LONG).show();
//                        Log.e(TAG, "###" + title + " dihapus dari favorit");
//                    } else {
//                        if (!title.isEmpty() && movieId != 0) {
//                            movieModel.setMovieId(movieId);
//                            movieModel.setTitle(title);
//                            movieModel.setYear(year);
//                            movieModel.setPosterPath(posterPath);
//                            movieModel.setSinopsis(sinopsis);
//                            movieModel.setRating(rate);
//                            movieModel.setExisting(true);
//
//                            realmHelper.save(movieModel);
//                            realmHelper.saveObject(reviewsList);
//                            realmHelper.saveObject(youtubesList);
//                            realmHelper.saveObject(quicktimesList);
//                            addFavorite.setText(getString(R.string.add_to_favorite));
//                            Snackbar.make(v, title + " ditambahkan ke favorit", Snackbar.LENGTH_LONG).show();
//                            Log.e(TAG, "###" + title + " ditambahkan ke favorit");
//                            Log.e("movie", "### saveObject reviews : " + reviewsList);
//                            Log.e("movie", "### saveObject youtube : " + youtubesList);
//                            Log.e("movie", "### saveObject quicktime : " + quicktimesList);
//                        } else {
//                            Toast.makeText(DetailActivity.this, "Tidak dapat menambahkan", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            });

        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.ic_photo_black_24dp)
                .into(rivImage);
        tvTitle.setText(title);
        tvYear.setText(year.substring(0, 4));
        String rating = String.valueOf(rate) + "/10";
        tvRating.setText(rating);
        tvSinopsis.setText(sinopsis);

//        if (movieModel.getReviewsList() != null) {
////            if (_movieModel.getReviewsList().size() != 0) {
//            Log.e(TAG, "### List from MovieModel : NOT NULL");
////                list = _movieModel.getReviewsList();
////                reviewAdapter = new ReviewAdapter(list, DetailActivity.this);
////                rvReview.setAdapter(reviewAdapter);
////                reviewAdapter.notifyDataSetChanged();
////            } else {
////                Log.e(TAG, "### List from MovieModel : SIZE IS 0");
////                Log.e(TAG, "### List from MovieModel : " + _movieModel.getReviewsList());
////                reviewAdapter = new ReviewAdapter(reviewsList, DetailActivity.this);
////                rvReview.setAdapter(reviewAdapter);
////                reviewAdapter.notifyDataSetChanged();
////            }
//        } else {
//            Log.e(TAG, "### List from MovieModel : IS NULL");
//        }
    }

    private void hitReviewsItem(String apiKey) {
        APIInterface service = ApiClient.getReviews(movieId);
        Call<ResponseBody> call = service.reviewItem(apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    reviewAdapter = new ReviewAdapter(reviewsList, DetailActivity.this);
                    rvReview.setAdapter(reviewAdapter); // TODO: perpindahan dari atas ke bawah
                    progressBarReview.setVisibility(View.GONE);
                    try {
                        reviewsList.clear();
                        String respon = response.body().string();
                        JSONObject object1 = new JSONObject(respon);

                        //saving
                        int movieId = object1.getInt("id");
                        int page = object1.getInt("page");
                        JSONArray array = object1.getJSONArray("results");

                        if (array.length() == 0) {
                            tvNoReview.setVisibility(View.VISIBLE);
                        }

                        for (int p = 0; p < array.length(); p++) {
                            JSONObject object2 = array.getJSONObject(p);
                            //saving
                            String author = object2.getString("author");
                            String content = object2.getString("content");
                            String id = object2.getString("id");
                            String url = object2.getString("url");

                            //setting
                            // TODO: set reviews to model
                            Reviewer review = new Reviewer();
                            review.setAuthor(author);
                            review.setContent(content);
                            review.setId(id);
                            review.setUrl(url);
                            review.setMovieId(movieId);
                            reviewsList.add(review);
                        }
//                        reviewAdapter = new ReviewAdapter(reviewsList, DetailActivity.this);
                        reviewAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reviewAdapter = new ReviewAdapter(results, DetailActivity.this);
                            rvReview.setAdapter(reviewAdapter);
                            reviewAdapter.notifyDataSetChanged();

                            progressBarReview.setVisibility(View.GONE);
                            Log.e(TAG, "###respon message from review : " + response.message());
                            Log.e(TAG, "###respon errorBody from review : " + response.errorBody());
                            //reviewAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                reviewAdapter = new ReviewAdapter(results, DetailActivity.this);
                rvReview.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();

                progressBarReview.setVisibility(View.GONE);
                Toast.makeText(DetailActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "NO INTERNET CONNECTION");
                t.getLocalizedMessage();
                t.getMessage();
                t.printStackTrace();
            }
        });
    }

    private void hitTrailersItem(String apiKey) {
        APIInterface service = ApiClient.getTrailers(movieId);
        Call<ResponseBody> call = service.trailersItem(apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    youtubeAdapter = new YoutubeAdapter(youtubesList, DetailActivity.this);
                    rvYoutube.setAdapter(youtubeAdapter);
                    quicktimeAdapter = new QuicktimeAdapter(quicktimesList, DetailActivity.this);
                    rvQuicktime.setAdapter(quicktimeAdapter);
                    Log.e(TAG, "### RESPONSE IS SUCCESSFUL GAN");

                    progressBarTrailer.setVisibility(View.GONE);
                    try {
                        youtubesList.clear();
                        quicktimesList.clear();

                        String respon = response.body().string();
                        JSONObject object1 = new JSONObject(respon);
                        JSONArray qucktimeArray = object1.getJSONArray("quicktime");
                        JSONArray youtubeArray = object1.getJSONArray("youtube");
                        Log.e(TAG, "youtubeArray GAN!!! : " + youtubeArray);

                        if (qucktimeArray.length() == 0 && youtubeArray.length() == 0) {
                            tvNoTrailer.setVisibility(View.VISIBLE);
                        }
                        if (qucktimeArray.length() == 0) {
                            Log.e(TAG, "Quicktime array is NULL");
                            /*do nothing*/
                        } else {
                            for (int p = 0; p < qucktimeArray.length(); p++) {
                                JSONObject objectQ = qucktimeArray.getJSONObject(p);
                                name = objectQ.getString("name");
                                String size = objectQ.getString("size");
                                String source = objectQ.getString("source");
                                String type = objectQ.getString("type");

                                // TODO: set trailers to model
                                Quicktimes trailerQ = new Quicktimes();
                                trailerQ.setName(name);
                                trailerQ.setType(type);
                                trailerQ.setSource(source);
                                trailerQ.setSize(size);
                                trailerQ.setMovieId(movieId);

                                quicktimesList.add(trailerQ);
                            }
                            quicktimeAdapter.notifyDataSetChanged();
                        }

                        if (youtubeArray.length() == 0) {
                            Log.e(TAG, "Youtube array is NULL");
                            /*do nothing*/
                        } else {
                            for (int p = 0; p < youtubeArray.length(); p++) {
                                JSONObject objectY = youtubeArray.getJSONObject(p);
                                name = objectY.getString("name");
                                String size = objectY.getString("size");
                                String source = objectY.getString("source");
                                String type = objectY.getString("type");

                                Youtubes trailerY = new Youtubes();
                                trailerY.setName(name);
                                trailerY.setSize(size);
                                trailerY.setSource(source);
                                trailerY.setType(type);
                                trailerY.setMovieId(movieId);

                                youtubesList.add(trailerY);
                            }
                            youtubeAdapter.notifyDataSetChanged();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            youtubeAdapter = new YoutubeAdapter(resultsYoutube, DetailActivity.this);
                            rvYoutube.setAdapter(youtubeAdapter);
                            youtubeAdapter.notifyDataSetChanged();
                            quicktimeAdapter = new QuicktimeAdapter(resultsQuicktime, DetailActivity.this);
                            rvQuicktime.setAdapter(quicktimeAdapter);
                            quicktimeAdapter.notifyDataSetChanged();
                            Log.e(TAG, "###respon message from trailer : " + response.message());
                            Log.e(TAG, "###respon errorBody from trailer : " + response.errorBody());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                youtubeAdapter = new YoutubeAdapter(resultsYoutube, DetailActivity.this);
                rvYoutube.setAdapter(youtubeAdapter);
                youtubeAdapter.notifyDataSetChanged();
                quicktimeAdapter = new QuicktimeAdapter(resultsQuicktime, DetailActivity.this);
                rvQuicktime.setAdapter(quicktimeAdapter);
                quicktimeAdapter.notifyDataSetChanged();

                progressBarTrailer.setVisibility(View.GONE);
                Log.e(TAG, "NO INTERNET CONNECTION");
                t.getLocalizedMessage();
                t.getMessage();
                t.printStackTrace();
                onFailToConnect();
            }
        });
    }

    private void hitDetailsItem(String apiKey) {
        APIInterface service = ApiClient.getDetails(movieId);
        Call<ResponseBody> call = service.detailsItem(movieId, apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String respon = response.body().string();
                        JSONObject object1 = new JSONObject(respon);
                        int duration = object1.getInt("runtime");
                        String dur = String.valueOf(duration) + " Min";
                        tvDuration.setText(dur);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.e(TAG, "###response from details not success \n"
                                        + response.errorBody().string()
                                        + "\n" + response.message());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.getLocalizedMessage();
                t.getMessage();
                t.printStackTrace();
//                onFailToConnect();
            }
        });
    }

    private void onFailToConnect() {
        progressBarTrailer.setVisibility(View.GONE);
        progressBarReview.setVisibility(View.GONE);
        /*relativeLayout.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitPopularItem(API_KEY);
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
