package com.mayurit.hakahaki.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mayurit.hakahaki.ActivityPostDetail;
import com.mayurit.hakahaki.ActivityPostTypeDetail;
import com.mayurit.hakahaki.ActivityPostTypeList;
import com.mayurit.hakahaki.Adapters.CategoryNewsListAdapter;
import com.mayurit.hakahaki.Helpers.Constant;
import com.mayurit.hakahaki.Helpers.RecyclerItemClickListener;
import com.mayurit.hakahaki.Helpers.RetrofitAPI;
import com.mayurit.hakahaki.MainActivity;
import com.mayurit.hakahaki.Model.NewsListModel;
import com.mayurit.hakahaki.NEEFEJDetail;
import com.mayurit.hakahaki.ProjectDetail;
import com.mayurit.hakahaki.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNEFEJ extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "toolbar_title";

    private String toolbarTitle;
    Context context;

    public static final String EXTRA_OBJC = "key.EXTRA_OBJC";

    View view;
    LinearLayout lnr_project,lnr_board_member,lnr_employee,lnr_about;

    public FragmentNEFEJ() {
        // Required empty public constructor
    }

    public static FragmentNEFEJ newInstance() {
        FragmentNEFEJ fragment = new FragmentNEFEJ();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nefej, container, false);
        context = getActivity();

        lnr_project = view.findViewById(R.id.lnr_project);
        lnr_board_member = view.findViewById(R.id.lnr_board_member);
        lnr_employee = view.findViewById(R.id.lnr_employee);
        lnr_about = view.findViewById(R.id.lnr_about);

        lnr_project.setOnClickListener(this);
        lnr_board_member.setOnClickListener(this);
        lnr_employee.setOnClickListener(this);
        lnr_about.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        toolbarTitle = (String) getResources().getText(R.string.nefej);
        getActivity().setTitle(toolbarTitle);
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.lnr_project:
                intent = new Intent(context, ProjectDetail.class);
                startActivity(intent);
                break;
            case R.id.lnr_board_member:
                intent = new Intent(context, ActivityPostTypeList.class);
                intent.putExtra(EXTRA_OBJC, "member");
                startActivity(intent);
                break;
            case R.id.lnr_employee:
                intent = new Intent(context, ActivityPostTypeList.class);
                intent.putExtra(EXTRA_OBJC, "employee");
                startActivity(intent);
                break;
            case R.id.lnr_about:
                intent = new Intent(context, ActivityPostTypeDetail.class);
                intent.putExtra("post_id",1430);
                intent.putExtra("post_type","page");
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
