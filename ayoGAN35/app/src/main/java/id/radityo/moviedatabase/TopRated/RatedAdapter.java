package id.radityo.moviedatabase.TopRated;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.radityo.moviedatabase.DetailActivity;
import id.radityo.moviedatabase.R;

public class RatedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Rated> items;
    private Activity context;

    public RatedAdapter(List<Rated> items, Activity context) {
        this.items = items;
        this.context = context;
    }

    public class RatedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public RatedViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_rated_item);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rated_item, viewGroup, false);
        return new RatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final Rated item = items.get(i);
        final RatedViewHolder rvh = (RatedViewHolder) holder;

        Picasso.get()
                .load(item.getPosterPath())
                .placeholder(R.drawable.ic_photo_black_24dp)
                .into(rvh.imageView);

        rvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("year", item.getReleaseDate());
                intent.putExtra("poster_path", item.getPosterPath());
                intent.putExtra("rate", item.getVoteAverage());
                intent.putExtra("sinopsis", item.getOverview());
                intent.putExtra("movie_id", item.getId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
