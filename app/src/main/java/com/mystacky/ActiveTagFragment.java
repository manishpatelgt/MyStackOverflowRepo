package com.mystacky;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Models.ActiveTag;
import com.Models.Badge;
import com.Models.MyBadges;
import com.Models.MyTag;
import com.Utils.Consts;
import com.retrofit.RetrofitClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manish on 03/11/2015.
 */
public class ActiveTagFragment extends Fragment {

    private static Logger logger = LoggerFactory.getLogger("c*.m*.ActiveTagFrag*");
    private LinearLayout rowContainer;
    private TextView tag_txt;
    private SpannableStringBuilder tag_details = new SpannableStringBuilder();
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.tag_fragment, null);

        rowContainer = (LinearLayout) v.findViewById(R.id.container);
        tag_txt = (TextView) v.findViewById(R.id.tag_txt);
        try{

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            //Calling method to get User Tags
            RetrofitClient.userInfoServices().getActiveTags(Consts.UserId, new Callback<MyTag>() {
                @Override
                public void success(MyTag info, Response response) {

                    logger.debug("Total Tags: " + info.getItems().size());

                    if (mProgressDialog.isShowing() && mProgressDialog != null)
                        mProgressDialog.dismiss();

                    for (ActiveTag tag : info.getItems()) {

                        String name = tag.getName() + " (" + tag.getCount() + ") ";
                        SpannableString goldSpan = new SpannableString(name);
                        goldSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
                        tag_details.append(goldSpan).append("\n").append("\n");
                    }

                    tag_txt.setText(tag_details, TextView.BufferType.SPANNABLE);
                    rowContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void failure(RetrofitError error) {
                    if (mProgressDialog.isShowing() && mProgressDialog != null)
                        mProgressDialog.dismiss();
                    String merror = error.getMessage();
                    logger.info("merror :" + merror);
                    Snackbar.make(rowContainer, merror, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
        return v;
    }
}
