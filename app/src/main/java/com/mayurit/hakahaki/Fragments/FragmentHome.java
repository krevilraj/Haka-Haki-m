package com.mayurit.hakahaki.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;

import com.mayurit.hakahaki.ActivityPostDetail;
import com.mayurit.hakahaki.Adapters.CategoryAdapter;

import com.mayurit.hakahaki.AudioDetail;
import com.mayurit.hakahaki.CategoryDetail;
import com.mayurit.hakahaki.Helpers.Constant;
import com.mayurit.hakahaki.Helpers.DatabaseHelper;
import com.mayurit.hakahaki.Helpers.RetrofitAPI;
import com.mayurit.hakahaki.Model.NewsListModel;

import com.mayurit.hakahaki.NEEFEJDetail;
import com.mayurit.hakahaki.ProjectDetail;

import com.mayurit.hakahaki.R;
import com.mayurit.hakahaki.VideoDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHome extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "toolbar_title";
    public static final String EXTRA_OBJC = "key.EXTRA_OBJC";

    ArrayList<NewsListModel> list = new ArrayList<>();
    ArrayList<NewsListModel> list1 = new ArrayList<>();
    ArrayList<NewsListModel> list2 = new ArrayList<>();
    private String toolbarTitle;
    Context context;

    int category_id = 34, category_id2 = 2, category_id3 = 28;

    CardView nefej, project, music, video, members, notice;

    CardView main_news1, main_news2, main_news3, main_news4;
    LinearLayout main_news_a1, main_news_a2, main_news_a3;
    LinearLayout main_news_b1, main_news_b2, main_news_b3;


    TextView mainNews1_title, mainNews2_title, mainNews3_title, mainNews4_title,
            mainNews1_content, mainNews2_content, mainNews3_content, mainNews4_content,
            mainNews1_date, mainNews2_date, mainNews3_date, mainNews4_date;
    ImageView mainNews1_image, mainNews2_image, mainNews3_image, mainNews4_image;

    //changes for bisheshsamachar
    TextView bisheshsamachar_header, ent_title1, ent_title2, ent_title3, readmore;
    ImageView ent_img1, ent_img2, ent_img3;
    TextView ent_date1, ent_date2, ent_date3;

    //changes for bisheshrepor
    TextView bisheshreport_header, report_title1, report_title2, report_title3, report_readmore;
    ImageView report_img1, report_img2, report_img3;
    TextView report_date1, report_date2, report_date3;

    private Context ctx;
    private LinearLayout lnrlayoutNews;
    DatabaseHelper databaseHelper;
    RelativeLayout rel_container;
    View view;

    //changes now
    private RecyclerView recyclerView;
    CategoryAdapter mAdapter;

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

        view = inflater.inflate(R.layout.fragment_home, null);
        category_id = 34;
        databaseHelper = new DatabaseHelper(context);
        lnrlayoutNews = view.findViewById(R.id.lnrlayoutNews);
        initFirstCategorySection();
        initSecondCategorySection();
        initThirdCategorySection();

        nefej = view.findViewById(R.id.nefej);
        project = view.findViewById(R.id.project);
        music = view.findViewById(R.id.music);
        video = view.findViewById(R.id.video);
        notice = view.findViewById(R.id.notice);
        members = view.findViewById(R.id.members);

        nefej.setOnClickListener(this);
        project.setOnClickListener(this);
        music.setOnClickListener(this);
        video.setOnClickListener(this);
        notice.setOnClickListener(this);
        members.setOnClickListener(this);

        mobile_data();
        fetchNews();
        fetchNew2();
        fetchNew3();
        return view;

    }

    private void initFirstCategorySection() {
        // Inflate the layout for this fragment
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

        mainNews1_date = view.findViewById(R.id.mainNews1_date);
        mainNews2_date = view.findViewById(R.id.mainNews2_date);
        mainNews3_date = view.findViewById(R.id.mainNews3_date);
        mainNews4_date = view.findViewById(R.id.mainNews4_date);

        main_news1 = view.findViewById(R.id.card_view_mainNews1);
        main_news2 = view.findViewById(R.id.card_view_mainNews2);
        main_news3 = view.findViewById(R.id.card_view_mainNews3);
        main_news4 = view.findViewById(R.id.card_view_mainNews4);
    }

    private void initSecondCategorySection() {
        bisheshsamachar_header = view.findViewById(R.id.bisheshsamachar_header);
        ent_title1 = view.findViewById(R.id.ent_title1);
        ent_title2 = view.findViewById(R.id.ent_title2);
        ent_title3 = view.findViewById(R.id.ent_title3);

        ent_date1 = view.findViewById(R.id.ent_date1);
        ent_date2 = view.findViewById(R.id.ent_date2);
        ent_date3 = view.findViewById(R.id.ent_date3);

        ent_img1 = view.findViewById(R.id.ent_img1);
        ent_img2 = view.findViewById(R.id.ent_img2);
        ent_img3 = view.findViewById(R.id.ent_img3);

        readmore = view.findViewById(R.id.readmore);

        readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                intent.putExtra("category_id", category_id2);
                startActivity(intent);
            }
        });

        main_news_a1 = view.findViewById(R.id.mtrl_main2_news1);
        main_news_a2 = view.findViewById(R.id.mtrl_main2_news2);
        main_news_a3 = view.findViewById(R.id.mtrl_main2_news3);
        main_news_a1.setOnClickListener(this);
        main_news_a2.setOnClickListener(this);
        main_news_a3.setOnClickListener(this);

    }

    private void initThirdCategorySection() {
        //bishesh report workflow
        bisheshreport_header = view.findViewById(R.id.bisheshreport_header);
        report_title1 = view.findViewById(R.id.report_title1);
        report_title2 = view.findViewById(R.id.report_title2);
        report_title3 = view.findViewById(R.id.report_title3);

        report_date1 = view.findViewById(R.id.report_date1);
        report_date2 = view.findViewById(R.id.report_date2);
        report_date3 = view.findViewById(R.id.report_date3);

        report_img1 = view.findViewById(R.id.report_img1);
        report_img2 = view.findViewById(R.id.report_img2);
        report_img3 = view.findViewById(R.id.report_img3);

        report_readmore = view.findViewById(R.id.report_readmore);

        main_news_b1 = view.findViewById(R.id.mtrl_main3_news1);
        main_news_b2 = view.findViewById(R.id.mtrl_main3_news2);
        main_news_b3 = view.findViewById(R.id.mtrl_main3_news3);

        main_news_b1.setOnClickListener(this);
        main_news_b2.setOnClickListener(this);
        main_news_b3.setOnClickListener(this);


        //end of bishesh report workflow callling done
    }

    @Override
    public void onResume() {
        getActivity().setTitle(toolbarTitle);
        super.onResume();
    }

    public void fetchNews() {
        Call<List<NewsListModel>> newsList = RetrofitAPI.getService().getCategoryLimitNews(category_id, 0, 4);
        newsList.enqueue(new Callback<List<NewsListModel>>() {
            @Override
            public void onResponse(Call<List<NewsListModel>> call, Response<List<NewsListModel>> response) {
                list.clear();
                list.addAll(response.body());
                Log.i("list", "data = " + list.get(0).getPostTitle());

                display(list);

                lnrlayoutNews.setVisibility(View.VISIBLE);
                for (NewsListModel info : list) {
                    ContentValues cv = new ContentValues();
                    cv.put("post_title", info.getPostTitle());
                    cv.put("category_id", 34);
                    cv.put("post_date", info.getPostDate());
                    cv.put("post_description", info.getPostExcerpt());
                    databaseHelper.insertMainNews(cv);
                }

                Log.i("list", "data = " + list.get(0).getPostTitle());


                mainNews1_title.setText(list.get(0).getPostTitle());
                mainNews2_title.setText(list.get(1).getPostTitle());
                mainNews3_title.setText(list.get(2).getPostTitle());
                mainNews4_title.setText(list.get(3).getPostTitle());

                mainNews1_content.setText(list.get(0).getPostExcerpt());
                mainNews2_content.setText(list.get(1).getPostExcerpt());
                mainNews3_content.setText(list.get(2).getPostExcerpt());
                mainNews4_content.setText(list.get(3).getPostExcerpt());

                mainNews1_date.setText(list.get(0).getPostDate());
                mainNews2_date.setText(list.get(1).getPostDate());
                mainNews3_date.setText(list.get(2).getPostDate());
                mainNews4_date.setText(list.get(3).getPostDate());

                Glide.with(context).load(list.get(0).getImageId()).into(mainNews1_image);
                Glide.with(context).load(list.get(1).getImageId()).into(mainNews2_image);
                Glide.with(context).load(list.get(2).getImageId()).into(mainNews3_image);
                Glide.with(context).load(list.get(3).getImageId()).into(mainNews4_image);


            }

            @Override
            public void onFailure(Call<List<NewsListModel>> call, Throwable throwable) {
                //     Toast.makeText(FragmentHome.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchNew2() {

        final Call<List<NewsListModel>> newsList = RetrofitAPI.getService().getCategoryLimitNews(category_id2, 0, 3);
        newsList.enqueue(new Callback<List<NewsListModel>>() {
            @Override
            public void onResponse(Call<List<NewsListModel>> call, Response<List<NewsListModel>> response) {
                list1.clear();
                list1.addAll(response.body());

                Log.i("list", "data = " + list1.get(0).getPostTitle());

                for (NewsListModel info : list1) {
                    ContentValues cv = new ContentValues();
                    cv.put("post_title", info.getPostTitle());
                    cv.put("category_id", category_id2);
                    cv.put("post_date", info.getPostDate());
                    cv.put("post_description", info.getPostExcerpt());
                    databaseHelper.insertMainNews(cv);
                }


                //entertainment get feed from server
                ent_title1.setText(list1.get(0).getPostTitle());
                ent_title2.setText(list1.get(1).getPostTitle());
                ent_title2.setText(list1.get(2).getPostTitle());

                ent_date1.setText(list1.get(0).getPostDate());
                ent_date2.setText(list1.get(1).getPostDate());
                ent_date3.setText(list1.get(2).getPostDate());

                Glide.with(context).load(list1.get(0).getImageId()).into(ent_img1);
                Glide.with(context).load(list1.get(1).getImageId()).into(ent_img2);
                Glide.with(context).load(list1.get(2).getImageId()).into(ent_img3);

            }

            @Override
            public void onFailure(Call<List<NewsListModel>> call, Throwable throwable) {
                //     Toast.makeText(FragmentHome.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchNew3() {

        Call<List<NewsListModel>> newsList = RetrofitAPI.getService().getCategoryLimitNews(category_id3, 0, 3);
        newsList.enqueue(new Callback<List<NewsListModel>>() {
            @Override
            public void onResponse(Call<List<NewsListModel>> call, Response<List<NewsListModel>> response) {
                list2.clear();
                list2.addAll(response.body());


                for (NewsListModel info : list2) {
                    ContentValues cv = new ContentValues();
                    cv.put("post_title", info.getPostTitle());
                    cv.put("category_id", category_id3);
                    cv.put("post_date", info.getPostDate());
                    cv.put("post_description", info.getPostExcerpt());
                    databaseHelper.insertMainNews(cv);
                }
                Log.i("list", "data = " + list2.get(0).getPostTitle());


                //set the values from the server to filed in bishesh report
                report_title1.setText(list2.get(0).getPostTitle());
                report_title2.setText(list2.get(1).getPostTitle());
                report_title3.setText(list2.get(2).getPostTitle());

                report_date1.setText(list2.get(0).getPostDate());
                report_date2.setText(list2.get(1).getPostDate());
                report_date3.setText(list2.get(2).getPostDate());

                Glide.with(context).load(list2.get(0).getImageId()).into(report_img1);
                Glide.with(context).load(list2.get(1).getImageId()).into(report_img2);
                Glide.with(context).load(list2.get(2).getImageId()).into(report_img3);

                report_readmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CategoryDetail.class);
                        intent.putExtra("category_id", category_id3);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<NewsListModel>> call, Throwable throwable) {
                //     Toast.makeText(FragmentHome.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void mobile_data() {
        if ((databaseHelper.countRowTable("tbl_news") == 0)) {
            netCheck();
        } else {
            list = databaseHelper.getQAList("" + category_id);
            display(list);

            list1 = databaseHelper.getQAList("" + category_id2);
            display2(list1);

            list2 = databaseHelper.getQAList("" + category_id3);
            display3(list2);
            lnrlayoutNews.setVisibility(View.VISIBLE);
        }


    }

    public void netCheck() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            fetchNews();
        } else {
            rel_container = view.findViewById(R.id.rel_container);
            Snackbar snackbar = Snackbar.make(rel_container, "No internet connection!", Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    netCheck();
                }
            });
            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
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

        mainNews1_date.setText(list.get(0).getPostDate());
        mainNews2_date.setText(list.get(1).getPostDate());
        mainNews3_date.setText(list.get(2).getPostDate());
        mainNews4_date.setText(list.get(3).getPostDate());

        Glide.with(context).load(list.get(0).getImageId()).into(mainNews1_image);
        Glide.with(context).load(list.get(1).getImageId()).into(mainNews2_image);
        Glide.with(context).load(list.get(2).getImageId()).into(mainNews3_image);
        Glide.with(context).load(list.get(3).getImageId()).into(mainNews4_image);

        main_news1.setOnClickListener(this);
        main_news2.setOnClickListener(this);
        main_news3.setOnClickListener(this);
        main_news4.setOnClickListener(this);

    }

    //ofline catch for samachar
    private void display2(List<NewsListModel> list1) {

        ent_title1.setText(list1.get(0).getPostTitle());
        ent_title2.setText(list1.get(1).getPostTitle());
        ent_title2.setText(list1.get(2).getPostTitle());

        ent_date1.setText(list1.get(0).getPostDate());
        ent_date2.setText(list1.get(1).getPostDate());
        ent_date3.setText(list1.get(2).getPostDate());

        Glide.with(context).load(list1.get(0).getImageId()).into(ent_img1);
        Glide.with(context).load(list1.get(1).getImageId()).into(ent_img2);
        Glide.with(context).load(list1.get(2).getImageId()).into(ent_img3);

        main_news_a1.setOnClickListener(this);
        main_news_a2.setOnClickListener(this);
        main_news_a3.setOnClickListener(this);

    }

    //ofline ko lagi list ma database ma pathako
    private void display3(List<NewsListModel> list2) {

        report_title1.setText(list2.get(0).getPostTitle());
        report_title2.setText(list2.get(1).getPostTitle());
        report_title3.setText(list2.get(2).getPostTitle());

        report_date1.setText(list2.get(0).getPostDate());
        report_date2.setText(list2.get(1).getPostDate());
        report_date3.setText(list2.get(2).getPostDate());

        Glide.with(context).load(list2.get(0).getImageId()).into(report_img1);
        Glide.with(context).load(list2.get(1).getImageId()).into(report_img2);
        Glide.with(context).load(list2.get(2).getImageId()).into(report_img3);

        main_news_b1.setOnClickListener(this);
        main_news_b2.setOnClickListener(this);
        main_news_b3.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.nefej:
                intent = new Intent(context, NEEFEJDetail.class);
                startActivity(intent);
                break;
            case R.id.project:
                intent = new Intent(context, ProjectDetail.class);
                startActivity(intent);
                break;
            case R.id.music:
                intent = new Intent(context, AudioDetail.class);
                startActivity(intent);
                break;
            case R.id.video:
                intent = new Intent(context, VideoDetail.class);
                startActivity(intent);
                break;
            case R.id.card_view_mainNews1:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list.get(0));
                startActivity(intent);
                break;
            case R.id.card_view_mainNews2:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list.get(1));
                startActivity(intent);
                break;
            case R.id.card_view_mainNews3:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list.get(2));
                startActivity(intent);
                break;
            case R.id.card_view_mainNews4:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list.get(3));
                startActivity(intent);
                break;
            case R.id.mtrl_main2_news1:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list1.get(0));
                startActivity(intent);
                break;
            case R.id.mtrl_main2_news2:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list1.get(1));
                startActivity(intent);
                break;
            case R.id.mtrl_main2_news3:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list1.get(2));
                startActivity(intent);
                break;
            case R.id.mtrl_main3_news1:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list2.get(0));
                startActivity(intent);
                break;
            case R.id.mtrl_main3_news2:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list2.get(1));
                startActivity(intent);
                break;
            case R.id.mtrl_main3_news3:
                intent = new Intent(context, ActivityPostDetail.class);
                intent.putExtra(EXTRA_OBJC, (Serializable) list2.get(2));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
