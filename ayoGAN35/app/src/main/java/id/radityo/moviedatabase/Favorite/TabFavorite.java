package id.radityo.moviedatabase.Favorite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import id.radityo.moviedatabase.Database.MovieModel;
import id.radityo.moviedatabase.Database.RealmHelper;
import id.radityo.moviedatabase.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TabFavorite extends Fragment {
    RecyclerView recyclerView;
    LinearLayout llNoFile;
    List<MovieModel> modelList = new ArrayList<>();
    FavoriteAdapter favoriteAdapter;
    Realm realm;
    RealmHelper realmHelper;
    AdapterFavorit adapterFavorit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_favorite, container, false);

        llNoFile = view.findViewById(R.id.ll_no_favorit);
        llNoFile.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recycler_favorite);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);
        modelList = realmHelper.getStoredMovie();

        adapterFavorit = new AdapterFavorit(modelList, getActivity(), realmHelper.getStoredMovie());
        //between
        favoriteAdapter = new FavoriteAdapter(modelList, getActivity());
        recyclerView.setAdapter(adapterFavorit);
//        favoriteAdapter.notifyDataSetChanged();

        if (modelList.isEmpty()) {
            llNoFile.setVisibility(View.VISIBLE);
        } else {
            llNoFile.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
        realm = null;
    }

    @Override
    public void onResume() {
        if (modelList.isEmpty()) {
            llNoFile.setVisibility(View.VISIBLE);
        } else {
            llNoFile.setVisibility(View.GONE);
        }
        super.onResume();
    }
}
