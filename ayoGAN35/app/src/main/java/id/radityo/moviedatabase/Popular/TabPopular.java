package id.radityo.moviedatabase.Popular;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.radityo.moviedatabase.APIInterface;
import id.radityo.moviedatabase.ApiClient;
import id.radityo.moviedatabase.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabPopular extends Fragment {
    private static final String TAG = "movie";
    public final String API_KEY = "ce64212de916313a39e9490b889fb667";
    RecyclerView recyclerView;
    List<Popular> popularList = new ArrayList<>();
    PopularAdapter adapter;
    ProgressBar progressBar;
    Button button;
    RelativeLayout relativeLayout;
    FrameLayout frameLayout;

    String title = "";
    String posterPath = "";
    String year = "";
    double rate;
    int movieId;
    int duration;
    String sinopsis = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_popular, container, false);
        recyclerView = view.findViewById(R.id.recycler_popular);
        progressBar = view.findViewById(R.id.progress_bar_popular);
        relativeLayout = view.findViewById(R.id.rl_broke_popular);
        button = view.findViewById(R.id.bt_broke_popular);
        frameLayout = view.findViewById(R.id.fl_popular);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        relativeLayout.setVisibility(View.GONE);

        adapter = new PopularAdapter(popularList, getActivity());
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        /*if (!popularList.isEmpty()) {
            progressBar.setVisibility(View.GONE);
        }*/
        hitPopularItem(API_KEY);

        return view;
    }

    private void hitPopularItem(String apiKey) {
        if (!popularList.isEmpty()) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        APIInterface service = ApiClient.getProduct();
        Call<ResponseBody> call = service.popularItem(apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    try {
                        popularList.clear();
                        String respon = response.body().string();
                        JSONObject object1 = new JSONObject(respon);
                        JSONArray arrayResult = object1.getJSONArray("results");
//                        Log.e(TAG, "array result: " + arrayResult);

                        for (int position = 0; position < arrayResult.length(); position++) {
                            JSONObject object2 = arrayResult.getJSONObject(position);

                            /*from API**/

                            title = object2.getString("title");
                            posterPath = object2.getString("poster_path");
                            year = object2.getString("release_date");
                            rate = object2.getDouble("vote_average");
                            sinopsis = object2.getString("overview");
                            movieId = object2.getInt("id");

                            /*Setting value*/
                            Popular popular = new Popular();
                            popular.setPosterPath(ApiClient.baseImage + posterPath);
                            popular.setTitle(title);
                            popular.setReleaseDate(year);
                            popular.setVoteAverage((float) rate);
                            popular.setOverview(sinopsis);
                            popular.setId(movieId);

                            //adding
                            popularList.add(popular);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException jse) {
                        jse.printStackTrace();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "###response not success \n" + response.errorBody() + "\n" + response.message());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Failure on popular happen");
                t.getLocalizedMessage();
                t.getMessage();
                t.printStackTrace();
                onFailToConnect();
            }
        });
    }

    private void onFailToConnect() {
        progressBar.setVisibility(View.GONE);
        if (!popularList.isEmpty()) {
            relativeLayout.setVisibility(View.GONE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
                hitPopularItem(API_KEY);
            }
        });
    }
}
