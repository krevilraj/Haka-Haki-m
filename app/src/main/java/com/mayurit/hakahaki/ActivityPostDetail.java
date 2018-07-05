package com.mayurit.hakahaki;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mayurit.hakahaki.Adapters.CategoryNewsListAdapter;
import com.mayurit.hakahaki.Helpers.Constant;
import com.mayurit.hakahaki.Helpers.RecyclerItemClickListener;
import com.mayurit.hakahaki.Helpers.RetrofitAPI;
import com.mayurit.hakahaki.Model.NewsListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPostDetail extends AppCompatActivity {

    public static final String EXTRA_OBJC = "key.EXTRA_OBJC";

    int page_no;
    RelativeLayout rel_container;
    String post_id;
    NewsListModel post;

    TextView txt_title,txt_date,txt_like_count;
    ImageView img_full;
    WebView web_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Intent intent = getIntent();
        post_id= "7300";
        post = (NewsListModel) getIntent().getSerializableExtra(EXTRA_OBJC);

        Log.i("postdatax1",post.getPostTitle());
//        post_id= intent.getExtras().getString("post_id");
        Toast.makeText(this, "categ = "+post_id, Toast.LENGTH_SHORT).show();
        rel_container = (RelativeLayout) findViewById(R.id.rel_container);

        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_like_count = (TextView) findViewById(R.id.txt_like_count);
        img_full = (ImageView) findViewById(R.id.img_full);
        web_description = (WebView) findViewById(R.id.web_description);
        displayApiResult(post);

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

        Call<List<NewsListModel>> noticeList = RetrofitAPI.getService().getPostDetail(post.getID());
        noticeList.enqueue(new Callback<List<NewsListModel>>() {
            @Override
            public void onResponse(Call<List<NewsListModel>> call, Response<List<NewsListModel>> response) {

                List<NewsListModel> resp = response.body();
                if (resp != null) {
                    displayResult(resp.get(0));

                } else {
                    showNoItemView(true);
                }


            }

            @Override
            public void onFailure(Call<List<NewsListModel>> call, Throwable throwable) {
                Toast.makeText(ActivityPostDetail.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void displayApiResult(NewsListModel posts) {
        txt_title.setText(posts.getPostTitle());
        txt_date.setText(posts.getPostDate());


        txt_like_count.setText(""+posts.getLikeCount());
        if(posts.getLikeCount()==null){
            txt_like_count.setText("");
        }
        web_description.loadData(posts.getPostExcerpt(),"text/html; charset=utf-8","utf-8");
        Glide.with(getApplicationContext()).load(posts.getImageId()).into(img_full);
    }


    private void displayResult(NewsListModel posts) {
        txt_title.setText(posts.getPostTitle());
        txt_date.setText(posts.getPostDate());


        txt_like_count.setText(""+posts.getLikeCount());
        if(posts.getLikeCount().equals("null")){
            txt_like_count.setText("");
        }
        web_description.loadData(posts.getPostContent(),"text/html; charset=utf-8","utf-8");
        Glide.with(getApplicationContext()).load(posts.getImageId()).into(img_full);
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

}
