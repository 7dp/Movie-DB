package id.radityo.moviedatabase.Review;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.radityo.moviedatabase.R;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Reviewer> items;
    private Activity context;

    public ReviewAdapter(List<Reviewer> items, Activity context) {
        this.items = items;
        this.context = context;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvReviewer, tvReview;

        public ReviewViewHolder(@NonNull View view) {
            super(view);
            tvReview = view.findViewById(R.id.tv_review);
            tvReviewer = view.findViewById(R.id.tv_reviewer);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        Reviewer item = items.get(i);
        ReviewViewHolder rvh = (ReviewViewHolder) holder;
        rvh.tvReviewer.setText(item.getAuthor());
        rvh.tvReview.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
