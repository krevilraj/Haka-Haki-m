package com.mayurit.hakahaki;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mayurit.hakahaki.Fragments.FragmentCategory;
import com.mayurit.hakahaki.Fragments.FragmentHome;
import com.mayurit.hakahaki.Fragments.FragmentMembers;
import com.mayurit.hakahaki.Fragments.FragmentMusic;
import com.mayurit.hakahaki.Fragments.FragmentNEFEJ;
import com.mayurit.hakahaki.Fragments.FragmentNotice;
import com.mayurit.hakahaki.Fragments.FragmentProject;
import com.mayurit.hakahaki.Fragments.FragmentVideo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    String toolbartitle = "Home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragment = FragmentHome.newInstance(toolbartitle);
        navigateFromDrawer(fragment, toolbartitle);
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

                if (doubleBackToExitPressedOnce) {
                    ActivityCompat.finishAffinity(MainActivity.this);
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();
        Intent intent;
        Context context = getApplicationContext();
        if (id == R.id.nav_home) {
            toolbartitle = "Home";
            fragment = FragmentHome.newInstance(toolbartitle);
        } else if (id == R.id.nav_nefej) {
           /* toolbartitle = (String) getText(R.string.nefej);
            fragment = FragmentNEFEJ.newInstance(toolbartitle);*/
            intent = new Intent(context, NEEFEJDetail.class);
            startActivity(intent);
        } else if (id == R.id.nav_project) {
            intent = new Intent(context, ProjectDetail.class);
            startActivity(intent);
            /*toolbartitle = (String) getText(R.string.project);
            fragment = FragmentProject.newInstance(toolbartitle);*/
        } else if (id == R.id.nav_video) {
            /*toolbartitle = (String) getText(R.string.video);
            fragment = FragmentVideo.newInstance(toolbartitle);*/
            intent = new Intent(context, VideoDetail.class);
            startActivity(intent);
        } else if (id == R.id.nav_music) {
           /* toolbartitle = (String) getResources().getText(R.string.music);
            fragment = FragmentMusic.newInstance(toolbartitle);*/
            intent = new Intent(context, AudioDetail.class);
            startActivity(intent);
        } else if (id == R.id.nav_notice) {
            toolbartitle = (String) getText(R.string.notice);
            fragment = FragmentNotice.newInstance(toolbartitle);
        } else if (id == R.id.nav_members) {
            toolbartitle = (String) getText(R.string.members);
            fragment = FragmentMembers.newInstance(toolbartitle);
        } else if (id == R.id.nav_category) {
            toolbartitle = (String) getText(R.string.category);
            fragment = FragmentCategory.newInstance(toolbartitle);
        } else if (id == R.id.nav_rate_us) {
            RateUs();
        }

        navigateFromDrawer(fragment, toolbartitle);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void navigateFromDrawer(Fragment fragment, String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        // depending on whether the device is a phone or tablet
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, title)
                .commit();


    }


    public void RateUs() {
        Uri uri = Uri.parse("market://details?id=com.mayurit.nepaliloksewapreparation");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.mayurit.nepaliloksewapreparation" +
                            MainActivity.this.getPackageName())));
        }
    }

    public void changeFragment(Fragment fragment, Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("mID", id);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

}
