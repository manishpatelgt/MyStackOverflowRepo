package com.mystacky;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.Models.UserShortInfo;
import com.Utils.Consts;
import com.data.PreferencesHelper;
import com.retrofit.RetrofitClient;
import com.retrofit.ServiceGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manish on 12/10/2015.
 */
public class WebViewActivity extends AppCompatActivity {

    private static Logger logger = LoggerFactory.getLogger("c*.m*.WebViewAct*");
    private final String clientId = "6136";
    private final String apiKey = "gQJsL7krOvbXkJ0NEI*mWA((";
    private final String redirectUri = "https://stackexchange.com/oauth/login_success";
    public static String EXTRA_ACTION_TOKEN_URL = "TokenUrl";
    private WebView browser;
    private ProgressBar progressBar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress_spinner);
        clearCookies();
        browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        final String url= ServiceGenerator.API_BASE_URL +"?client_id=" + clientId +"&scope=write_access"+"&redirect_uri=" + redirectUri+"";

        browser.setWebChromeClient(new WebChromeClient() {
            // Show loading progress in activity's title bar.
            @Override
            public void onProgressChanged(WebView view, int progress) {
                setProgress(progress * 100);

                if (progress >= 100) {
                    //setProgressBarIndeterminateVisibility(false);
                    progressBar.setVisibility(View.GONE);
                } else {
                    //setProgressBarIndeterminateVisibility(true);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        browser.setWebViewClient(new WebViewClient() {
            // When start to load page, show url in activity's title bar

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                logger.info("Auth URL: " + url);
                if (url.contains("#access_token")) {
                    return true;
                }
                return false;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                logger.info("Loading URL: " + url);
                progressBar.setVisibility(View.VISIBLE);
                //setProgressBarIndeterminateVisibility(true);
                //setTitle(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                logger.info("Loaded URL: " + url);
                if (url.contains("#access_token")) {

                    mProgressDialog = new ProgressDialog(WebViewActivity.this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    String token_str = url;
                    logger.debug("Token URL: " + token_str);
                    String[] str = token_str.split("access_token=");
                    logger.debug("srt[0]: " + str[0]);
                    logger.debug("srt[1]: " + str[1]);
                    String token = str[1].substring(0, str[1].length() - 14);
                    logger.debug("token: " + token);

                    //Calling method to get UserId
                    RetrofitClient.userIdServices().getUserId("stackoverflow", apiKey, token, new Callback<UserShortInfo>() {
                        @Override
                        public void success(UserShortInfo info, Response response) {
                            if (mProgressDialog.isShowing() && mProgressDialog!=null)
                                mProgressDialog.dismiss();

                            logger.debug("UserId: " + info.getItems().get(0).getUserId());
                            finishAct(info.getItems().get(0).getUserId());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (mProgressDialog.isShowing() && mProgressDialog!=null)
                                mProgressDialog.dismiss();

                            String merror = error.getMessage();
                            logger.info("merror :" + merror);
                            Snackbar.make(browser, merror, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                    });
                    //Intent intent = new Intent();
                    //intent.putExtra(EXTRA_ACTION_TOKEN_URL, url);
                    //setResult(Activity.RESULT_OK, intent);
                    //WebViewActivity.this.finish();
                    //overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
                }
            }

        });

        browser.loadUrl(url);

        /*Button loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///String scope  = " ?scope \"\" ";
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(ServiceGenerator.API_BASE_URL +"?client_id=" + clientId +"?redirect_uri=" + redirectUri+""));
                startActivity(intent);
            }
        });*/
    }

    private void finishAct(String UserId){

        Consts.UserId = UserId;
        PreferencesHelper.setLoginCheck(true);
        PreferencesHelper.setUserID(UserId);

        Intent i = new Intent(WebViewActivity.this, MainActivity.class);
        startActivity(i);
        WebViewActivity.this.finish();
        overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
    }

    public void clearCookies(){
        CookieSyncManager.createInstance(WebViewActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

}
