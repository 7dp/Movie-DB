package id.radityo.moviedatabase.Trailer.Youtube;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import id.radityo.moviedatabase.PlayingActivity;
import id.radityo.moviedatabase.R;
import id.radityo.moviedatabase.Trailer.Youtube.Youtubes;

public class YoutubeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Youtubes> items;
    private Activity context;

    public YoutubeAdapter(List<Youtubes> items, Activity context) {
        this.items = items;
        this.context = context;
    }

    public class YoutubeViewHolder extends RecyclerView.ViewHolder {
        ImageButton ibPlay;
        TextView tvTitle;

        public YoutubeViewHolder(@NonNull View view) {
            super(view);
            ibPlay = view.findViewById(R.id.ib_play_trailer);
            tvTitle = view.findViewById(R.id.tv_trailers);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, viewGroup, false);
        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final Youtubes item = items.get(i);
        final YoutubeViewHolder yvh = (YoutubeViewHolder) holder;
        yvh.ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(item.getSource());
            }
        });
        yvh.tvTitle.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void showDialog(final String source) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Putar video di?")
                .setPositiveButton("di sini", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, PlayingActivity.class);
                        intent.putExtra("video", source);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("aplikasi lain", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + source));
                        context.startActivity(Intent.createChooser(intent, "Open with: "));
                    }
                });
        Dialog dialog = alert.create();
        dialog.show();

    }
}
