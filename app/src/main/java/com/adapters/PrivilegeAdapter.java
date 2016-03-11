package com.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Models.Privilege;
import com.Models.QuestionItems;
import com.mystacky.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Manish on 03/11/2015.
 */
public class PrivilegeAdapter extends RecyclerView.Adapter<PrivilegeAdapter.ViewHolder> {

    private List<Privilege> mDataset;
    private int rowLayout;
    private Context mContext;
    private LayoutInflater linf;
    private static MyClickListener myClickListener;
    private static String LOG_TAG = "PrivilegeAdapter";

    public PrivilegeAdapter(List<Privilege> applications, int rowLayout, Context con) {
        this.mDataset = applications;
        this.rowLayout = rowLayout;
        this.mContext = con;
        this.linf = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.linf = LayoutInflater.from(con);
    }

    public void clearData() {
        int size = this.mDataset.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mDataset.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
            /// this.mDataset.clear();
            notifyDataSetChanged();
        }
    }

    public void refresh(List<Privilege> applications) {
        this.mDataset.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String short_desc=mDataset.get(position).getShortDescription();
        holder.short_desc.setText(Html.fromHtml(short_desc));

        String long_desc=mDataset.get(position).getDescription();
        holder.desc.setText(long_desc);

        SpannableStringBuilder ViewsSpantext = new SpannableStringBuilder();
        String repo=mDataset.get(position).getReputation();
        String mystring=new String("Reputation: "+repo);
        SpannableString repoSpan = new SpannableString(mystring);
        repoSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, mystring.length(), 0);
        repoSpan.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.darkgreen)), 0, mystring.length(), 0);
        ViewsSpantext.append(repoSpan);
        holder.repuation.setText(ViewsSpantext, TextView.BufferType.SPANNABLE);

        //holder.repuation.setText(repo);
    }

    public void removeAt(int position) {
        this.mDataset.remove(position);
        this.notifyItemRemoved(position);
        Log.i(LOG_TAG, " mDataset size: " + mDataset.size());
        notifyItemRangeChanged(0, mDataset.size());
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView repuation,desc,short_desc;

        public ViewHolder(View itemView) {
            super(itemView);
            repuation = (TextView) itemView.findViewById(R.id.repo);
            desc = (TextView) itemView.findViewById(R.id.long_Short);
            short_desc = (TextView) itemView.findViewById(R.id.short_desc);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            try{
                myClickListener.onItemClick(getAdapterPosition(), v);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
