package com.mystacky;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Models.FavQuestions;
import com.Models.FavQuestionsItems;
import com.Models.QuestionItems;
import com.Models.Questions;
import com.Utils.Consts;
import com.adapters.FavQuestionsAdapter;
import com.adapters.QuestionsAdapter;
import com.data.PreferencesHelper;
import com.itemAnimator.CustomItemAnimator;
import com.retrofit.RetrofitClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manish on 03/11/2015.
 */
public class MyFavQuestionsFragment extends Fragment {

    private static Logger logger = LoggerFactory.getLogger("c*.m*.MyFavQuestionsFr*");
    private RecyclerView mRecyclerView;
    private FavQuestionsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<FavQuestionsItems> _list;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fav_questions_fragment, null);

        try{
            mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            _list=new ArrayList<FavQuestionsItems>();
            mAdapter = new FavQuestionsAdapter(_list, R.layout.fav_questions_view_row, getActivity());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new CustomItemAnimator());

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();


            //Calling method to get User Questions
            RetrofitClient.userInfoServices().getFavQuestions(PreferencesHelper.getUserID(), new Callback<FavQuestions>() {
                @Override
                public void success(FavQuestions info, Response response) {

                    if (mProgressDialog.isShowing() && mProgressDialog!=null)
                        mProgressDialog.dismiss();

                    logger.info("Total Questions :" + info.getItems().size());
                    _list = info.getItems();
                    mAdapter.refresh(_list);

                    Snackbar.make(mRecyclerView, "Total Favorite Questions: " + info.getItems().size(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                @Override
                public void failure(RetrofitError error) {

                    if (mProgressDialog.isShowing() && mProgressDialog!=null)
                        mProgressDialog.dismiss();

                    String merror = error.getMessage();
                    logger.info("merror :" + merror);
                    Snackbar.make(mRecyclerView, merror, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });

            ((FavQuestionsAdapter) mAdapter).setOnItemClickListener(new FavQuestionsAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    logger.info(" Clicked on Item " + position);
                }
            });


        }catch(Exception e){
            e.printStackTrace();
        }

        return v;
    }
}
