package com.mayurit.hakahaki.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.mayurit.hakahaki.Helpers.Constant;
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
    ArrayList<NewsListModel> list1 = new ArrayList<>();
    ArrayList<NewsListModel> list2 = new ArrayList<>();
    private String toolbarTitle;
    private String mParam2;
    Context context;
    int category_id;
    TextView mainNews1_title,mainNews2_title,mainNews3_title,mainNews4_title,
            mainNews1_content,mainNews2_content,mainNews3_content,mainNews4_content;
    ImageView mainNews1_image,mainNews2_image,mainNews3_image,mainNews4_image;


    CardView crd_video;

    //changes for bisheshsamachar
    TextView bisheshsamachar_header,ent_title1,ent_title2,ent_title3,readmore;
    ImageView ent_img1,ent_img2,ent_img3;
    TextView ent_date1,ent_date2,ent_date3;

    //changes for bisheshrepor
    TextView bisheshreport_header,report_title1,report_title2,report_title3,report_readmore;
    ImageView report_img1,report_img2,report_img3;
    TextView report_date1,report_date2,report_date3;

    private Context ctx;
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

        View view = inflater.inflate(R.layout.fragment_home,null);
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

        //bisheshsamachar initialization workflow
        bisheshsamachar_header=view.findViewById(R.id.bisheshsamachar_header);
        ent_title1=view.findViewById(R.id.ent_title1);
        ent_title2=view.findViewById(R.id.ent_title2);
        ent_title3=view.findViewById(R.id.ent_title3);

        readmore=view.findViewById(R.id.readmore);

        ent_date1=view.findViewById(R.id.ent_date1);
        ent_date2=view.findViewById(R.id.ent_date2);
        ent_date3=view.findViewById(R.id.ent_date3);


        ent_img1=view.findViewById(R.id.ent_img1);
        ent_img2=view.findViewById(R.id.ent_img2);
        ent_img3=view.findViewById(R.id.ent_img3);
        //end of bisheshsamachar portion

        //bishesh report workflow
        bisheshreport_header = view.findViewById(R.id.bisheshreport_header);
        report_title1=view.findViewById(R.id.report_title1);
        report_title2=view.findViewById(R.id.report_title2);
        report_title3=view.findViewById(R.id.report_title3);

        report_readmore=view.findViewById(R.id.report_readmore);

        report_date1=view.findViewById(R.id.report_date1);
        report_date2=view.findViewById(R.id.report_date2);
        report_date3=view.findViewById(R.id.report_date3);



        report_img1=view.findViewById(R.id.report_img1);
        report_img2=view.findViewById(R.id.report_img2);
        report_img3=view.findViewById(R.id.report_img3);
        //end of bishesh report workflow callling done



        crd_video = view.findViewById(R.id.crd_video);
        crd_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fetchNews();
        fetchbisheshsamachar_News();
        fetchbisheshreport_News();

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

            @Override
            public void onFailure(Call<List<NewsListModel>> call, Throwable throwable) {
           //     Toast.makeText(FragmentHome.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void  fetchbisheshsamachar_News(){
        category_id=2;
        Call<List<NewsListModel>> newsList = RetrofitAPI.getService().getCategoryLimitNews(2,0, 3);
        newsList.enqueue(new Callback<List<NewsListModel>>() {
            @Override
            public void onResponse(Call<List<NewsListModel>> call, Response<List<NewsListModel>> response) {
                list1.addAll(response.body());

            Log.i("list","data = " +list1.get(0).getPostTitle());


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

    //call the values from server and set
    public void  fetchbisheshreport_News(){
        category_id=28;
        Call<List<NewsListModel>> newsList = RetrofitAPI.getService().getCategoryLimitNews(28,0, 3);
        newsList.enqueue(new Callback<List<NewsListModel>>() {
            @Override
            public void onResponse(Call<List<NewsListModel>> call, Response<List<NewsListModel>> response) {
                list2.addAll(response.body());

                Log.i("list","data = " +list2.get(0).getPostTitle());


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

            }

            @Override
            public void onFailure(Call<List<NewsListModel>> call, Throwable throwable) {
                //     Toast.makeText(FragmentHome.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
