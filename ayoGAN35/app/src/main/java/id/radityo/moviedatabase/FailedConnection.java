package id.radityo.moviedatabase;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class FailedConnection {

    public static void onFailToConnect(ProgressBar progressBar, RelativeLayout relativeLayout, Button button) {
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hitPopularItem(API_KEY);
                Snackbar.make(v, "Snackbar", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
