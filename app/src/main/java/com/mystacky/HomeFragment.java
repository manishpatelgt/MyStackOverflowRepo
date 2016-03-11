package com.mystacky;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Models.UserShortInfo;
import com.Utils.Consts;
import com.application.MainApplication;
import com.data.PreferencesHelper;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manish on 30/10/2015.
 */
public class HomeFragment extends Fragment {

    private static Logger logger = LoggerFactory.getLogger("c*.m*.HomeFrag*");
   private LinearLayout rowContainer;
    private ProgressDialog mProgressDialog;
    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.home_fragment,null);


        // Obtain the shared Tracker instance.
        MainApplication application = (MainApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        // Set screen name.
        mTracker.setScreenName("HomeFragment");

        // Send a screen view.
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        // Row Container
        rowContainer = (LinearLayout) v.findViewById(R.id.row_container);

         mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        //Calling method to get User info
        RetrofitClient.userInfoServices().getUserInfo(PreferencesHelper.getUserID(), new Callback<UserShortInfo>() {
            @Override
            public void success(UserShortInfo info, Response response) {

                try {

                    if (mProgressDialog.isShowing() && mProgressDialog!=null)
                        mProgressDialog.dismiss();

                    View profileView = inflater.inflate(R.layout.layout_profile, null);
                    ImageView profile_img = (ImageView) profileView.findViewById(R.id.profile_img);

                    String imageUrl = info.getItems().get(0).getProfile();
                    Picasso.with(getActivity()).load(imageUrl).into(profile_img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                        }
                    });

                    ((TextView) profileView.findViewById(R.id.display_name)).setText(info.getItems().get(0).getDisplayName());
                    ((TextView) profileView.findViewById(R.id.location)).setText(info.getItems().get(0).getLocation());
                    ((TextView) profileView.findViewById(R.id.age)).setText(info.getItems().get(0).getAge());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(Consts.Card_MarginTop, 0, 0, Consts.Card_MarginBottom);
                        rowContainer.addView(profileView, layoutParams);

                    } else{
                        rowContainer.addView(profileView);
                    }

                    View reputationView = inflater.inflate(R.layout.layout_row, null);
                    String repo = info.getItems().get(0).getReputation();
                    fillRow(reputationView, "Reputation", repo);

                    View useridView = inflater.inflate(R.layout.layout_row, null);
                    String userid = info.getItems().get(0).getUserId();
                    fillRow(useridView, "UserId", userid);

                    View badgeView = inflater.inflate(R.layout.layout_row, null);

                    SpannableStringBuilder details = new SpannableStringBuilder();

                    String gold = info.getItems().get(0).getBadges().getGold() + "(Gold)   ";
                    SpannableString goldSpan = new SpannableString(gold);
                    goldSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, gold.length(), 0);
                    goldSpan.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange)), 0, gold.length(), 0);
                    details.append(goldSpan);

                    String silver = info.getItems().get(0).getBadges().getSilver() + "(Silver)   ";
                    SpannableString silverSpan = new SpannableString(silver);
                    silverSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, silver.length(), 0);
                    silverSpan.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.grey)), 0, silver.length(), 0);
                    details.append(silverSpan);

                    String bronge = info.getItems().get(0).getBadges().getBronze() + "(Bronze)   ";
                    SpannableString brongeSpan = new SpannableString(bronge);
                    brongeSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, bronge.length(), 0);
                    brongeSpan.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange4)), 0, bronge.length(), 0);
                    details.append(brongeSpan);

                    fillRow3(badgeView, "Badges", details);

                    View websiteView = inflater.inflate(R.layout.layout_website, null);
                    String Name = info.getItems().get(0).getWebSite();
                    fillRow2(websiteView, "Website", Name);

                    View StackyView = inflater.inflate(R.layout.layout_website, null);
                    String link = info.getItems().get(0).getLink();
                    fillRow2(StackyView, "StackOverflow Link", link);


                    View flairView = inflater.inflate(R.layout.layout_flair, null);
                    ImageView flair_img = (ImageView) flairView.findViewById(R.id.flair_img);
                    Picasso.with(getActivity()).load(Consts.flairURL).into(flair_img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(Consts.Card_MarginTop, 0, 0, Consts.Card_MarginBottom);
                        rowContainer.addView(flairView, layoutParams);

                    } else{
                        rowContainer.addView(flairView);
                    }

                    long unixSeconds2 = Long.valueOf(info.getItems().get(0).getLastAccessDate());
                    Date date2 = new Date(unixSeconds2 * 1000L); // *1000 is to convert seconds to milliseconds
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); // the format of your date
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                    String formattedDate2 = sdf.format(date2);
                    System.out.println(formattedDate2);

                    View lastView = inflater.inflate(R.layout.layout_row, null);
                    fillRow(lastView, "Last Access", formattedDate2);

                    long unixSeconds = Long.valueOf(info.getItems().get(0).getCreateDate());
                    Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                    String formattedDate = sdf.format(date);
                    System.out.println(formattedDate);

                    View createedView = inflater.inflate(R.layout.layout_row, null);
                    fillRow(createedView, "Created ", formattedDate);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing() && mProgressDialog!=null)
                    mProgressDialog.dismiss();
                String merror = error.getMessage();
                logger.info("merror :" + merror);
                Snackbar.make(rowContainer, merror, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return v;
    }

    public void fillRow(View view, final String title, final String description) {
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);

        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        descriptionView.setText(description);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(Consts.Card_MarginTop, 0, 0, Consts.Card_MarginBottom);
            rowContainer.addView(view, layoutParams);

        } else{
            rowContainer.addView(view);
        }

    }

    public void fillRow3(View view, final String title, final SpannableStringBuilder description) {
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);

        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        descriptionView.setText(description, TextView.BufferType.SPANNABLE);
        //rowContainer.addView(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(Consts.Card_MarginTop, 0, 0, Consts.Card_MarginBottom);
            rowContainer.addView(view,layoutParams);

        } else{
            rowContainer.addView(view);
        }

    }

    public void fillRow2(View view, final String title, final String description) {
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);

        TextView descriptionView = (TextView) view.findViewById(R.id.website);

        descriptionView.setClickable(true);

        String mystring=new String(description);
        SpannableString content = new SpannableString(mystring);
        //content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown3)), 0, mystring.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        descriptionView.setText(content);

        descriptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = description;
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


        //rowContainer.addView(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(Consts.Card_MarginTop, 0, 0, Consts.Card_MarginBottom);
            rowContainer.addView(view,layoutParams);

        } else{
            rowContainer.addView(view);
        }

    }
}
