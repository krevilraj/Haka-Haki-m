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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mayurit.hakahaki.Adapters.ProjectListAdapter;
import com.mayurit.hakahaki.Helpers.Constant;
import com.mayurit.hakahaki.Helpers.RecyclerItemClickListener;
import com.mayurit.hakahaki.Helpers.RetrofitAPI;
import com.mayurit.hakahaki.Model.ProjectModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDetail extends AppCompatActivity {

    public static final String EXTRA_OBJC = "key.EXTRA_OBJC";

    int page_no;
    int totalRowsCategeory = Constant.CATEGORY_LIMIT;
    ArrayList<ProjectModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    RelativeLayout rel_container;
    int category_id;
    SwipeRefreshLayout swipe_refresh;

    ProjectListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_category);
        Intent intent = getIntent();
//        category_id= intent.getExtras().getInt("category_id");
//        Toast.makeText(this, "categ = "+category_id, Toast.LENGTH_SHORT).show();
        rel_container = (RelativeLayout) findViewById(R.id.rel_container);
        swipeProgress(true);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                mAdapter.notifyDataSetChanged();
                requestAction();
            }
        });
        RecyclerWithListner();
    }

    private void RecyclerWithListner() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProjectListAdapter(this, list, recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        ProjectModel singleItem = list.get(position);
                        Toast.makeText(ProjectDetail.this, "categ = "+singleItem.getID(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProjectDetail.this, ActivityProjectDetail.class);
//                        intent.putExtra("post_id",singleItem.getID());
                        intent.putExtra(EXTRA_OBJC, (Serializable) singleItem);
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(final View view, final int position) {

                    }
                })
        );
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipe_refresh.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // detect when scroll reach bottom
        mAdapter.setOnLoadMoreListener(new ProjectListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int current_page) {
                totalRowsCategeory += Constant.CATEGORY_LIMIT;
                if (totalRowsCategeory > mAdapter.getItemCount() && current_page != 0) {
                    page_no = current_page + 1;

                    requestAction();
                } else {
                    mAdapter.setLoaded();
                }
            }
        });
        requestAction();

    }

    private void requestAction() {

        if (page_no == 1) {

        } else {
            mAdapter.setLoading();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                netCheck();
            }
        }, Constant.DELAY_TIME);
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

    /*public void netCheck1() {

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            fetchData1();
        } else {
            Snackbar snackbar = Snackbar.make(rel_container, "No internet connection!", Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    netCheck1();
                }
            });
            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

    }*/


    public void fetchData() {
       Log.i("Bxx","categoryid="+category_id+"page="+page_no+"Con="+Constant.CATEGORY_LIMIT);

        Call<List<ProjectModel>> noticeList = RetrofitAPI.getService().getProjectList();
        noticeList.enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {
                swipeProgress(false);
                Log.i("Bxx", "fetc = " + page_no);
                for(ProjectModel data: response.body()){
                    Log.i("postdata","v ="+data.getID());
                }
                displayApiResult(response.body());
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ProjectModel>> call, Throwable throwable) {
                Toast.makeText(ProjectDetail.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });

    }
    /*public void fetchData1() {
        Log.i("Bxx1","categoryid="+category_id+"page="+page_no+"Con="+Constant.CATEGORY_LIMIT);
        Call<List<ProjectModel>> noticeList = RetrofitAPI.getService().getCategoryLimitNews(category_id,page_no*10, Constant.CATEGORY_LIMIT);
        noticeList.enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {

                swipeProgress(false);
                List<ProjectModel> resp = response.body();
                if (resp != null) {
                    displayApiResult(response.body());
                    Log.i("checkx","ayo");
                   *//* list.addAll(response.body());
                    mAdapter.notifyDataSetChanged();*//*
                } else {
                    showNoItemView(true);
                }

            }

            @Override
            public void onFailure(Call<List<ProjectModel>> call, Throwable throwable) {
                Log.i("postdata2","v =comedy");
            }
        });

    }*/
    
    private void displayApiResult(final List<ProjectModel> posts) {

        mAdapter.insertData(posts);
    }

    private void swipeProgress(final boolean show) {
        if (!show) {
            swipe_refresh.setRefreshing(show);
            return;
        }
        swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh.setRefreshing(show);
            }
        });
    }

    private void showNoItemView(boolean show) {
        View lyt_no_item = (View) findViewById(R.id.lyt_no_item_category);
        ((TextView) findViewById(R.id.no_item_message)).setText(R.string.no_category);
        if (show) {
            recyclerView.setVisibility(View.GONE);
            lyt_no_item.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            lyt_no_item.setVisibility(View.GONE);
        }
    }

}
