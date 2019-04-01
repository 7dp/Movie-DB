package id.radityo.moviedatabase.Trailer.Quicktime;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import id.radityo.moviedatabase.R;

public class QuicktimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Quicktimes> items;
    private Activity context;

    public QuicktimeAdapter(List<Quicktimes> items, Activity context) {
        this.items = items;
        this.context = context;
    }

    public class QuicktimeViewHolder extends RecyclerView.ViewHolder {
        ImageButton ibPlay;
        TextView tvTitle;

        public QuicktimeViewHolder(@NonNull View view) {
            super(view);
            ibPlay = view.findViewById(R.id.ib_play_trailer);
            tvTitle = view.findViewById(R.id.tv_trailers);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, viewGroup, false);
        return new QuicktimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final Quicktimes item = items.get(i);
        final QuicktimeViewHolder qvh = (QuicktimeViewHolder) holder;

        qvh.ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + item.getSource()));
                context.startActivity(Intent.createChooser(intent,"Open with: "));
            }
        });
        qvh.tvTitle.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
