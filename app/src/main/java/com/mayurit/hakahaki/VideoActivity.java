package com.mayurit.hakahaki;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mayurit.hakahaki.Helpers.RetrofitAPI;
import com.mayurit.hakahaki.Model.VideoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity {
    public static final String EXTRA_OBJC = "key.EXTRA_OBJC";

    int page_no;
    RelativeLayout rel_container;
    String post_id;
    VideoModel post;

    TextView vid_title1, expandableTextView_description, vid_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
       // expandText("देशको पूर्वी भूभाग भएर बहने नदीहरूमा बाढी आउन सक्ने संभावना बढेको छ । जल तथा मौसम विज्ञान विभागको जलविज्ञान महाशाखा तथा बाढी पूर्वानुमान शाखाले जारी गरेको बाढी बुलेटिन ");
        vid_title1 = (TextView) findViewById(R.id.vid_title1);
        expandableTextView_description = (TextView) findViewById(R.id.expandableTextView_description);
        vid_date = (TextView) findViewById(R.id.vid_date);
        Intent intent = getIntent();
        post = (VideoModel) getIntent().getSerializableExtra(EXTRA_OBJC);

        netCheck();
    }

    public void netCheck() {

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            fetchData();
        } else {
            Snackbar snackbar = Snackbar.make(rel_container, "No internet connection!", Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    netCheck();
                }
            });
            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

    }


    public void fetchData() {
                Call<VideoModel> noticeList = RetrofitAPI.getService().getVideoDetail("video", post.getID());
        noticeList.enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {

                VideoModel resp = response.body();
                if (resp != null) {
                    displayResult(resp);

                } else {
                    showNoItemView(true);
                }
            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable throwable) {
                Toast.makeText(VideoActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }

  /*  private void expandText(String information) {
        final ExpandableTextView expandableTextView = (ExpandableTextView) this.findViewById(R.id.expandableTextView_description);
        expandableTextView.setText(information);
        final ImageButton buttonToggle = (ImageButton) this.findViewById(R.id.button_toggle);
        expandableTextView.setAnimationDuration(750L);
        expandableTextView.setInterpolator(new OvershootInterpolator());
        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());


        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expandableTextView.toggle();
                float deg = buttonToggle.getRotation() + 180F;
                buttonToggle.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

            }
        });
    }*/
    private void displayResult(VideoModel posts) {
        vid_title1.setText(posts.getPostTitle());
        vid_date.setText(posts.getPostDate());
        expandableTextView_description.setText(posts.getPostContent());
    }
    private void showNoItemView(boolean show) {
        View lyt_no_item = (View) findViewById(R.id.lyt_no_item_category);
        ((TextView) findViewById(R.id.no_item_message)).setText(R.string.no_category);
        if (show) {
            lyt_no_item.setVisibility(View.VISIBLE);
        } else {
            lyt_no_item.setVisibility(View.GONE);
        }
    }
    public static void directLinkToBrowser(Activity activity, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "Ops, Cannot open url", Toast.LENGTH_LONG).show();
        }
    }

}
