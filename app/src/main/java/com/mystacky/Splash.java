package com.mystacky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.data.PreferencesHelper;

/**
 * Created by Manish on 12/15/2015.
 */
public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        if(PreferencesHelper.getLoginCheck()){

            Intent i = new Intent(Splash.this, MainActivity.class);
            startActivity(i);
            Splash.this.finish();
            overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);

        }else{
            Intent i = new Intent(Splash.this, WebViewActivity.class);
            startActivity(i);
            Splash.this.finish();
            overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
        }
    }
}
