package id.radityo.moviedatabase.TopRated;

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

public class TabRated extends Fragment {
    private static final String TAG = "movie";
    public final String API_KEY = "ce64212de916313a39e9490b889fb667";
    RecyclerView recyclerView;
    RatedAdapter adapter;
    List<Rated> ratedList = new ArrayList<>();
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_rated, container, false);
        relativeLayout = view.findViewById(R.id.rl_broke_rated);
        button = view.findViewById(R.id.bt_broke_rated);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_rated);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        relativeLayout.setVisibility(View.GONE);

        adapter = new RatedAdapter(ratedList, getActivity());
        recyclerView.setAdapter(adapter);
        hitRatedItem(API_KEY);
        return view;
    }

    private void hitRatedItem(String apiKey) {
        if (!ratedList.isEmpty()) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        APIInterface service = ApiClient.getProduct();
        Call<ResponseBody> call = service.ratedItem(apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    try {
                        ratedList.clear();
                        String respon = response.body().string();
                        JSONObject object1 = new JSONObject(respon);
                        JSONArray array = object1.getJSONArray("results");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object2 = array.getJSONObject(i);

                            //getting value
                            String posterPath = object2.getString("poster_path");
                            String title = object2.getString("title");
                            String year = object2.getString("release_date");
                            String sinopsis = object2.getString("overview");
                            double rate = object2.getDouble("vote_average");
                            int movieId = object2.getInt("id");

                            //setting the value
                            Rated rated = new Rated();
                            rated.setPosterPath(ApiClient.baseImage + posterPath);
                            rated.setTitle(title);
                            rated.setReleaseDate(year);
                            rated.setOverview(sinopsis);
                            rated.setVoteAverage((float) rate);
                            rated.setId(movieId);

                            ratedList.add(rated);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } catch (JSONException jse) {
                        jse.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "#####response not success \n" + response.errorBody() + "\n" + response.message());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Failure on rated happen");
                t.getLocalizedMessage();
                t.getMessage();
                t.printStackTrace();
                onFailToConnect();
            }
        });
    }

    private void onFailToConnect() {
        progressBar.setVisibility(View.GONE);
        if (!ratedList.isEmpty()) {
            relativeLayout.setVisibility(View.GONE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
                hitRatedItem(API_KEY);
            }
        });
    }
}
