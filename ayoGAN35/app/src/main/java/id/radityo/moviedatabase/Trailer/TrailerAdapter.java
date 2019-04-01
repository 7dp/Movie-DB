package id.radityo.moviedatabase.Trailer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import id.radityo.moviedatabase.DetailActivity;
import id.radityo.moviedatabase.R;

public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Trailers> items;
    DetailActivity context;

    public TrailerAdapter(List<Trailers> items, DetailActivity context) {
        this.items = items;
        this.context = context;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        ImageButton ibPlay;
        TextView tvName;

        public TrailerViewHolder(@NonNull View view) {
            super(view);
            ibPlay = view.findViewById(R.id.ib_play_trailer);
            tvName = view.findViewById(R.id.tv_trailers);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, viewGroup, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final Trailers item = items.get(i);
        final TrailerViewHolder tvh = (TrailerViewHolder) holder;

//        tvh.tvName.setText(item.getName());
//        tvh.ibPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + item.getSource()));
//                context.startActivity(Intent.createChooser(intent, "Open with:"));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
