package com.mayurit.hakahaki.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayurit.hakahaki.Helpers.Constant;
import com.mayurit.hakahaki.Helpers.DatabaseHelper;
import com.mayurit.hakahaki.Helpers.RetrofitAPI;
import com.mayurit.hakahaki.Model.NewsListModel;
import com.mayurit.hakahaki.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHome extends Fragment {
    private static final String ARG_PARAM1 = "toolbar_title";

    ArrayList<NewsListModel> list = new ArrayList<>();
    private String toolbarTitle;
    private String mParam2;
    Context context;
    int category_id;
    CardView nefej;
    TextView mainNews1_title,mainNews2_title,mainNews3_title,mainNews4_title,
            mainNews1_content,mainNews2_content,mainNews3_content,mainNews4_content;
    ImageView mainNews1_image,mainNews2_image,mainNews3_image,mainNews4_image;
    private Context ctx;
    private LinearLayout lnrlayoutNews;
    DatabaseHelper databaseHelper;
    RelativeLayout rel_container;
    View view ;
    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance(String title) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            toolbarTitle = getArguments().getString(ARG_PARAM1);
            getActivity().setTitle(toolbarTitle);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        category_id =34;
       view = inflater.inflate(R.layout.fragment_home,null);
        // Inflate the layout for this fragment
        databaseHelper = new DatabaseHelper(context);
        mainNews1_title = view.findViewById(R.id.mainNews1_title);
        mainNews2_title = view.findViewById(R.id.mainNews2_title);
        mainNews3_title = view.findViewById(R.id.mainNews3_title);
        mainNews4_title = view.findViewById(R.id.mainNews4_title);

        mainNews1_content = view.findViewById(R.id.mainNews1_content);
        mainNews2_content = view.findViewById(R.id.mainNews2_content);
        mainNews3_content = view.findViewById(R.id.mainNews3_content);
        mainNews4_content = view.findViewById(R.id.mainNews4_content);

        mainNews1_image = view.findViewById(R.id.mainNews1_image);
        mainNews2_image = view.findViewById(R.id.mainNews2_image);
        mainNews3_image = view.findViewById(R.id.mainNews3_image);
        mainNews4_image = view.findViewById(R.id.mainNews4_image);

        lnrlayoutNews = view.findViewById(R.id.lnrlayoutNews);

        mobile_data();

        return view;



    }

    @Override
    public void onResume() {
        getActivity().setTitle(toolbarTitle);
        super.onResume();
    }
    public void  fetchNews(){
        Call<List<NewsListModel>> newsList = RetrofitAPI.getService().getCategoryLimitNews(category_id,4, Constant.CATEGORY_LIMIT);
        newsList.enqueue(new Callback<List<NewsListModel>>() {
            @Override
            public void onResponse(Call<List<NewsListModel>> call, Response<List<NewsListModel>> response) {
                list.addAll(response.body());
                Log.i("list","data = " +list.get(0).getPostTitle());

                display(list);

                lnrlayoutNews.setVisibility(View.VISIBLE);
        for(NewsListModel info:list){
            ContentValues cv = new ContentValues();
            cv.put("post_title", info.getPostTitle());
            cv.put("category_id", 34);
            cv.put("post_date", info.getPostDate());
            cv.put("post_description", info.getPostExcerpt());
            databaseHelper.insertMainNews(cv);
        }

            }

            @Override
            public void onFailure(Call<List<NewsListModel>> call, Throwable throwable) {
           //     Toast.makeText(FragmentHome.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void mobile_data(){
       Log.i("msz", "v="+databaseHelper.countRowTable("tbl_news"));
            if((databaseHelper.countRowTable("tbl_news") == 0)){
                netCheck();
            }
            else {
                List<NewsListModel> listModel = databaseHelper.getQAList("34");
               // list.addAll(databaseHelper.getQAList("34"));
                Log.i("msz","size = "+list.size());
                display(listModel);


        /*        List<NewsListModel> listModel1 = databaseHelper.getQAList("28");
               // list.addAll(databaseHelper.getQAList("34"));
                Log.i("msz","size = "+list.size());
                display(listModel1);
                */
                lnrlayoutNews.setVisibility(View.VISIBLE);
            }


    }

    public void netCheck() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            fetchNews();
        } else {
            rel_container = (RelativeLayout) view.findViewById(R.id.rel_container);
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

    private void display(List<NewsListModel> list) {

        mainNews1_title.setText(list.get(0).getPostTitle());
        mainNews2_title.setText(list.get(1).getPostTitle());
        mainNews3_title.setText(list.get(2).getPostTitle());
        mainNews4_title.setText(list.get(3).getPostTitle());

        mainNews1_content.setText(list.get(0).getPostExcerpt());
        mainNews2_content.setText(list.get(1).getPostExcerpt());
        mainNews3_content.setText(list.get(2).getPostExcerpt());
        mainNews4_content.setText(list.get(3).getPostExcerpt());

        Glide.with(context).load(list.get(0).getImageId()).into(mainNews1_image);
        Glide.with(context).load(list.get(1).getImageId()).into(mainNews2_image);
        Glide.with(context).load(list.get(2).getImageId()).into(mainNews3_image);
        Glide.with(context).load(list.get(3).getImageId()).into(mainNews4_image);
    }
}
