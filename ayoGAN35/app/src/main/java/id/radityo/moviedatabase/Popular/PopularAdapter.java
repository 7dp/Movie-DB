package id.radityo.moviedatabase.Popular;

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

import java.util.List;

import id.radityo.moviedatabase.DetailActivity;
import id.radityo.moviedatabase.R;

public class PopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Popular> items;
    private Activity context;
    private static final String TAG = "movie";

    public PopularAdapter(List<Popular> items, Activity context) {
        this.items = items;
        this.context = context;
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public PopularViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_popular_item);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.popular_item, viewGroup, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final PopularViewHolder pvh = (PopularViewHolder) holder;
        final Popular item = items.get(i);

        Picasso.get()
                .load(item.getPosterPath())
                .placeholder(R.drawable.ic_photo_black_24dp)
                .into(pvh.imageView);

        pvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                String title = item.getTitle();
                String posterPath = item.getPosterPath();
                String sinopsis = item.getOverview();
                String year = item.getReleaseDate();
                float rate = item.getVoteAverage();
                int movieId = item.getId();

                TabPopular tabPopular = new TabPopular();
                int duration = tabPopular.duration;

                intent.putExtra("title", title);
                intent.putExtra("poster_path", posterPath);
                intent.putExtra("sinopsis", sinopsis);
                intent.putExtra("year", year);
                intent.putExtra("rate", rate);
                intent.putExtra("movie_id", movieId);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
