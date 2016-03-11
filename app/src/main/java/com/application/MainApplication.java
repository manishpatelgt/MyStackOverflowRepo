package com.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mystacky.R;

/**
 * Created by Manish on 30/10/2015.
 */
public class MainApplication extends Application {

    private Tracker mTracker;
    private static final String LOG_TAG = "MyStacky";
    public static MainApplication REF_GAPPLICATION;
    // Needs to be volatile as another thread can see a half initialised instance.
    private volatile static MainApplication applicationInstance;


    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Application singleton
        applicationInstance = this;
        applicationContext = getApplicationContext();

        preferences = applicationContext.getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
    }

    private static Context applicationContext = null;

    public static Context getContext() {
        return applicationContext;
    }

    public static void setContext(Context newContext) {
        applicationContext = newContext;
    }

    private static SharedPreferences preferences;

    public static SharedPreferences getPreferences() {
        return preferences;
    }


}
