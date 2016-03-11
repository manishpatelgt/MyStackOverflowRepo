package com.mystacky;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Models.Badge;
import com.Models.MyBadges;
import com.Models.UserShortInfo;
import com.Utils.Consts;
import com.data.PreferencesHelper;
import com.retrofit.RetrofitClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manish on 03/11/2015.
 */
public class BadgeFragment extends Fragment {

    private static Logger logger = LoggerFactory.getLogger("c*.m*.BadgeFrag*");
    private LinearLayout rowContainer;
    private TextView gold_txt, silver_txt, bronge_txt;
    private SpannableStringBuilder gold_details = new SpannableStringBuilder();
    private SpannableStringBuilder silver_details = new SpannableStringBuilder();
    private SpannableStringBuilder bronge_details = new SpannableStringBuilder();
    private int gold_count=0,silver_count=0,bronge_count=0;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.badge_fragment, null);

        rowContainer = (LinearLayout) v.findViewById(R.id.container);
        gold_txt = (TextView) v.findViewById(R.id.gold_txt);
        silver_txt = (TextView) v.findViewById(R.id.silver_txt);
        bronge_txt = (TextView) v.findViewById(R.id.bronge_txt);

        try {

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            //Calling method to get User Badges
            RetrofitClient.userInfoServices().getBadges(PreferencesHelper.getUserID(), new Callback<MyBadges>() {
                @Override
                public void success(MyBadges info, Response response) {

                    logger.debug("Total Badges: " + info.getItems().size());

                    if (mProgressDialog.isShowing() && mProgressDialog!=null)
                    mProgressDialog.dismiss();

                    for (Badge badge : info.getItems()) {

                       // for(int i=0;i<info.getItems().size();i++){
                        //Badge badge=info.getItems().get(i);

                        if(badge.getRank().equals("bronze")){

                            String name =badge.getName()+" ("+badge.getAwardCount()+") ";
                            SpannableString goldSpan = new SpannableString(name);
                            goldSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
                            bronge_details.append(goldSpan).append("\n");
                            bronge_count = bronge_count + Integer.parseInt(badge.getAwardCount());
                        }
                        else if(badge.getRank().equals("silver")){

                            String name =badge.getName()+" ("+badge.getAwardCount()+") ";
                            SpannableString goldSpan = new SpannableString(name);
                            goldSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
                            silver_details.append(goldSpan).append("\n");
                            silver_count = silver_count + Integer.parseInt(badge.getAwardCount());
                        }
                        else if(badge.getRank().equals("gold")){

                            String name =badge.getName()+" ("+badge.getAwardCount()+") ";
                            SpannableString goldSpan = new SpannableString(name);
                            goldSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
                            gold_details.append(goldSpan).append("\n");
                            gold_count = gold_count + Integer.parseInt(badge.getAwardCount());

                        }
                    }

                    String gold_c =String.valueOf("Total: "+gold_count);
                    SpannableString goldSpan = new SpannableString(gold_c);
                    goldSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, gold_c.length(), 0);
                    goldSpan.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.white)), 0, gold_c.length(), 0);
                    gold_details.append("\n");
                    gold_details.append(goldSpan);
                    gold_txt.setText(gold_details, TextView.BufferType.SPANNABLE);

                    String silver_c =String.valueOf("Total: "+silver_count);
                    SpannableString silverSpan = new SpannableString(silver_c);
                    silverSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, silver_c.length(), 0);
                    silverSpan.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.white)), 0, silver_c.length(), 0);
                    silver_details.append("\n");
                    silver_details.append(silverSpan);
                    silver_txt.setText(silver_details, TextView.BufferType.SPANNABLE);

                    String bronge_c =String.valueOf("Total: "+bronge_count);
                    SpannableString brongeSpan = new SpannableString(bronge_c);
                    brongeSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, bronge_c.length(), 0);
                    brongeSpan.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.white)), 0, bronge_c.length(), 0);
                    bronge_details.append("\n");
                    bronge_details.append(brongeSpan);
                    bronge_txt.setText(bronge_details, TextView.BufferType.SPANNABLE);

                    rowContainer.setVisibility(View.VISIBLE);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
}
