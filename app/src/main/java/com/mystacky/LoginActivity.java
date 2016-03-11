package com.mystacky;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;

import com.Models.UserShortInfo;
import com.Utils.Consts;
import com.application.MainApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.retrofit.RetrofitClient;
import com.retrofit.ServiceGenerator;
import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manish on 12/11/2015.
 */
public class LoginActivity extends AppCompatActivity {

    private static Logger logger = LoggerFactory.getLogger("c*.m*.LoginAct*");
    // you should either define client id and secret as constants or in string resources
    private final String clientId = "6136";
    private final String apiKey = "gQJsL7krOvbXkJ0NEI*mWA((";
    private final String redirectUri = "https://stackexchange.com/oauth/login_success";
    private static final int REQUEST_ACTION = 1;
    private String token_str;
    private Button loginButton;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_login3);

       final String url= ServiceGenerator.API_BASE_URL +"?client_id=" + clientId +"&scope=write_access"+"&redirect_uri=" + redirectUri+"";

        loginButton = (Button) findViewById(R.id.sign_in);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,WebViewActivity.class);
                startActivityForResult(i, REQUEST_ACTION);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
            }
        });

        // Obtain the shared Tracker instance.
        MainApplication application = (MainApplication) getApplication();
        mTracker = application.getDefaultTracker();

        // Set screen name.
        mTracker.setScreenName("LoginActivity");

        // Send a screen view.
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ACTION:
                if (resultCode == Activity.RESULT_OK) {
                    token_str = data.getExtras().getString(WebViewActivity.EXTRA_ACTION_TOKEN_URL);
                    logger.debug("Token URL: " + token_str);
                    String[] str=token_str.split("access_token=");
                    logger.debug("srt[0]: "+str[0]);
                    logger.debug("srt[1]: "+str[1]);
                    String token=str[1].substring(0,str[1].length()-14);
                    logger.debug("token: "+token);

                    //Calling method to get User info
                    RetrofitClient.userIdServices().getUserId("stackoverflow",apiKey,token,new Callback<UserShortInfo>() {
                        @Override
                        public void success(UserShortInfo info, Response response) {
                            logger.debug("UserId: " + info.getItems().get(0).getUserId());
                            Consts.UserId=info.getItems().get(0).getUserId();
                            Intent i=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            String merror = error.getMessage();
                            logger.info("merror :" + merror);
                            Snackbar.make(loginButton, merror, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                    });

                }
                break;
        }
    }
}
