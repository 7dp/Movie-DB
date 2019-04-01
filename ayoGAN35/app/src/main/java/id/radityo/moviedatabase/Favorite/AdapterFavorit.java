package id.radityo.moviedatabase.Favorite;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.radityo.moviedatabase.Database.MovieModel;
import id.radityo.moviedatabase.DetailActivity;
import id.radityo.moviedatabase.R;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class AdapterFavorit extends RealmRecyclerViewAdapter<MovieModel, AdapterFavorit.MovieModelViewHolder> {
    private List<MovieModel> items;
    private Activity context;

    public AdapterFavorit(List<MovieModel> items, Activity context, RealmResults<MovieModel> results) {
        super(results, true);
        this.items = items;
        this.context = context;
    }

    public AdapterFavorit(@Nullable OrderedRealmCollection<MovieModel> data, boolean autoUpdate, List<MovieModel> items, Activity context) {
        super(data, autoUpdate);
        this.items = items;
        this.context = context;
    }

    public class MovieModelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MovieModelViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_popular_item);
        }
    }

    @NonNull
    @Override
    public MovieModelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.popular_item, viewGroup, false);
        return new MovieModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieModelViewHolder movieModelViewHolder, int i) {
        final MovieModelViewHolder mmvh = movieModelViewHolder;
        final MovieModel item = items.get(i);

        mmvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie_id", item.getMovieId());
                intent.putExtra("title", item.getTitle());
                //intent.putExtra("duration", item.getDuration());
                intent.putExtra("year", item.getYear());
                intent.putExtra("poster_path", item.getPosterPath());
                intent.putExtra("sinopsis", item.getSinopsis());
                intent.putExtra("rate", item.getRating());
                context.startActivity(intent);
            }
        });

        Picasso.get()
                .load(item.getPosterPath())
                .into(mmvh.imageView);
    }
}
