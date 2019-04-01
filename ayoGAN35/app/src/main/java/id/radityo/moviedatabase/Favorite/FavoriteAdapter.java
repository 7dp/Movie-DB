package id.radityo.moviedatabase.Favorite;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import id.radityo.moviedatabase.Database.MovieModel;
import id.radityo.moviedatabase.DetailActivity;
import id.radityo.moviedatabase.R;
import id.radityo.moviedatabase.Review.Reviewer;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieModel> items;
    private Activity context;

    public FavoriteAdapter(List<MovieModel> items, Activity context) {
        this.items = items;
        this.context = context;
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public FavoriteViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_popular_item);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.popular_item, viewGroup, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final FavoriteViewHolder fvh = (FavoriteViewHolder) holder;
        final MovieModel item = items.get(i);
//        final Reviewer reviews = new Reviewer();

        fvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie_id", item.getMovieId());
                intent.putExtra("title", item.getTitle());
//                intent.putExtra("duration", item.getDuration());
                intent.putExtra("year", item.getYear());
                intent.putExtra("poster_path", item.getPosterPath());
                intent.putExtra("sinopsis", item.getSinopsis());
                intent.putExtra("rate", item.getRating());
//                intent.putExtra("reviews", item.getReviewsList());
                context.startActivity(intent);
                //parcelable
//                intent.putExtra("reviews", item);
            }
        });
        Picasso.get()
                .load(item.getPosterPath())
                .into(fvh.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
