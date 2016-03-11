package com.mystacky;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.Models.Badges;
import com.Models.UserShortInfo;
import com.Utils.Consts;
import com.application.MainApplication;
import com.data.PreferencesHelper;
import com.google.android.gms.analytics.Tracker;
import com.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView displayName,websiteLink;
    private ImageView profile_img;
    private NavigationView  navView;
    private FloatingActionButton fab;
    private Tracker mTracker;
    private static Logger logger = LoggerFactory.getLogger("c*.m*.MainActivi*");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView=(NavigationView)findViewById(R.id.nav_view);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        displayName= (TextView)navView.findViewById(R.id.display_name);
        profile_img=(ImageView) navView.findViewById(R.id.profile_img);
        websiteLink=(TextView)navView.findViewById(R.id.weblink);
        websiteLink.setClickable(true);

        websiteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = websiteLink.getText().toString();
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mdp3030@gmail.com"});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Get In touch with me");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "");
                sendIntent.setType("plain/text");
                startActivity(sendIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

        logger.debug("get UserId: "+PreferencesHelper.getUserID());

        //Calling method to get User info
        RetrofitClient.userInfoServices().getUserInfo(PreferencesHelper.getUserID(), new Callback<UserShortInfo>() {
            @Override
            public void success(UserShortInfo info, Response response) {
                logger.info("Profile :" + info.getItems().get(0).getProfile());
                displayName.setText(info.getItems().get(0).getDisplayName());

                String mystring = new String(info.getItems().get(0).getWebSite());
                SpannableString content = new SpannableString(mystring);
                content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
                websiteLink.setText(content);

                String imageUrl = info.getItems().get(0).getProfile();
                Picasso.with(MainActivity.this).load(imageUrl).into(profile_img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                String merror = error.getMessage();
                logger.info("merror :" + merror);
                Snackbar.make(fab, merror, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        // Obtain the shared Tracker instance.
        MainApplication application = (MainApplication) getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            /*PreferencesHelper.setLoginCheck(false);
            startActivity(new Intent(MainActivity.this, Splash.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            HomeFragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            setTitle(item.getTitle());

        }else if(id == R.id.nav_questions){

            MyQuestionsFragment fragment = new MyQuestionsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            setTitle(item.getTitle());
        }
        else if(id == R.id.nav_fav){

            MyFavQuestionsFragment fragment = new MyFavQuestionsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            setTitle(item.getTitle());
       }

        else if(id == R.id.nav_privileges){

            PrivilegeFragment fragment = new PrivilegeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            setTitle(item.getTitle());
        }
        else if(id == R.id.nav_badgess){

            BadgeFragment fragment = new BadgeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            setTitle(item.getTitle());
        }
        else if(id == R.id.nav_tags){

            ActiveTagFragment fragment = new ActiveTagFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            setTitle(item.getTitle());

        }else if(id == R.id.nav_logout){

            PreferencesHelper.setLoginCheck(false);
            startActivity(new Intent(MainActivity.this, Splash.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
