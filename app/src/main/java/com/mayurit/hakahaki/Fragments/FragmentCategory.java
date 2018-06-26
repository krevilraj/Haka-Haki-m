package com.mayurit.hakahaki.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mayurit.hakahaki.Adapters.CategoryAdapter;
import com.mayurit.hakahaki.Helpers.RecyclerItemClickListener;
import com.mayurit.hakahaki.Helpers.RetrofitAPI;
import com.mayurit.hakahaki.Model.CategoryModel;
import com.mayurit.hakahaki.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCategory extends Fragment {
    private static final String ARG_PARAM1 = "toolbar_title";

    ArrayList<CategoryModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    CategoryAdapter mAdapter;
    RelativeLayout rel_container;

    private String toolbarTitle;
    private String mParam2;
    Context context;
    View view;

    public FragmentCategory() {
        // Required empty public constructor
    }

    public static FragmentCategory newInstance(String title) {
        FragmentCategory fragment = new FragmentCategory();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category_list, container, false);
        RecyclerWithListner();

        return view;
    }

    private void RecyclerWithListner() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CategoryAdapter(list);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CategoryModel singleItem = list.get(position);
                        Toast.makeText(context, "data = "+singleItem.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongItemClick(final View view, final int position) {

                    }
                })
        );

        netCheck();

    }

    public void fetchData(){
        Call<List<CategoryModel>> noticeList = RetrofitAPI.getService().getCategoryList();
        noticeList.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                list.addAll(response.body());
                mAdapter.notifyDataSetChanged();
                view.findViewById(R.id.rel_container).setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable throwable) {
            }
        });

    }

    public void netCheck() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            fetchData();
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


}
