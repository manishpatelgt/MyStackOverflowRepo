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
import android.widget.ImageView;
import android.widget.TextView;

import com.Models.FavQuestionsItems;
import com.Models.QuestionItems;
import com.Utils.Consts;
import com.mystacky.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Manish on 03/11/2015.
 */
public class FavQuestionsAdapter extends RecyclerView.Adapter<FavQuestionsAdapter.ViewHolder> {

    private List<FavQuestionsItems> mDataset;
    private int rowLayout;
    private Context mContext;
    private LayoutInflater linf;
    private static MyClickListener myClickListener;
    private static String LOG_TAG = "FavQuestionsAdapter";

    public FavQuestionsAdapter(List<FavQuestionsItems> applications, int rowLayout, Context con) {
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

    public void refresh(List<FavQuestionsItems> applications) {
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

        String title_txt=mDataset.get(position).getTitle();
        holder.title.setText(Html.fromHtml(title_txt));

        String views=mDataset.get(position).getView_count();
        //holder.views.setText(views+" view");

        SpannableStringBuilder ViewsSpantext = new SpannableStringBuilder();
        String mystring2=new String(views+" view");
        SpannableString viewSpan = new SpannableString(mystring2);
        viewSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, mystring2.length(), 0);
        //nameSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown3)), 0, mystring2.length(), 0);
        ViewsSpantext.append(viewSpan);
        holder.views.setText(ViewsSpantext, TextView.BufferType.SPANNABLE);

        String votes=mDataset.get(position).getScore();
        SpannableStringBuilder votesSpantext = new SpannableStringBuilder();
        String mystring3=new String(votes+" votes");
        SpannableString votesSpan = new SpannableString(mystring3);
        votesSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, mystring3.length(), 0);
        //nameSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown3)), 0, mystring3.length(), 0);
        votesSpantext.append(votesSpan);
        holder.votes.setText(votesSpantext, TextView.BufferType.SPANNABLE);

        //holder.votes.setText(votes+" votes");

        String answers=mDataset.get(position).getAnswer_count();
        SpannableStringBuilder ansSpantext = new SpannableStringBuilder();
        String mystring4=new String(answers+" answers");
        SpannableString answSpan = new SpannableString(mystring4);
        answSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, mystring4.length(), 0);
        //nameSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown3)), 0, mystring4.length(), 0);
        ansSpantext.append(answSpan);
        holder.answers.setText(ansSpantext, TextView.BufferType.SPANNABLE);

        //holder.answers.setText(answers + " answers");

        if(mDataset.get(position).getIs_answered()){
            holder.answers.setTextColor(mContext.getResources().getColor(R.color.darkgreen));
        }

        String mystring = new String("Main Link");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        holder.link.setText(content);

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDataset.get(position).getLink()));
                mContext.startActivity(browserIntent);
            }
        });

        long unixSeconds2 = Long.valueOf(mDataset.get(position).getCreateDate());
        Date date2 = new Date(unixSeconds2 * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String formattedDate2 = sdf.format(date2);
        System.out.println(formattedDate2);
        holder.createddate.setText("Created at "+formattedDate2);

        SpannableStringBuilder TagSpantext = new SpannableStringBuilder();

        for(int i=0;i<mDataset.get(position).getTags().size();i++){
            Log.i(LOG_TAG, "Tag: " + mDataset.get(position).getTags().get(i));
            String tagmystring=new String(mDataset.get(position).getTags().get(i));
            SpannableString tagSpan = new SpannableString(tagmystring);
            tagSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, tagmystring.length(), 0);
            tagSpan.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.yellow4)), 0, tagmystring.length(), 0);
            TagSpantext.append(tagSpan).append("\t");
        }

        holder.tags.setText(TagSpantext, TextView.BufferType.SPANNABLE);

        String displayName=mDataset.get(position).getOwner().getDisplayName();
        holder.display_name.setText(displayName);

        String userLinkstring = new String("User Link");
        SpannableString content2 = new SpannableString(userLinkstring);
        content2.setSpan(new UnderlineSpan(), 0, userLinkstring.length(), 0);
        holder.userLink.setText(content2);

        holder.userLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDataset.get(position).getOwner().getLink()));
                mContext.startActivity(browserIntent);
            }
        });

        Picasso.with(mContext).load(mDataset.get(position).getOwner().getProfile()).into(holder.profile_img, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
            }
        });

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
        TextView title,views,answers,votes,createddate,link,tags,display_name,userLink;
        ImageView profile_img;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            views = (TextView) itemView.findViewById(R.id.views);
            answers = (TextView) itemView.findViewById(R.id.answers);
            votes = (TextView) itemView.findViewById(R.id.votes);
            createddate = (TextView) itemView.findViewById(R.id.created);
            link = (TextView) itemView.findViewById(R.id.link);
            tags = (TextView) itemView.findViewById(R.id.tags);
            display_name = (TextView) itemView.findViewById(R.id.display_name);
            userLink = (TextView) itemView.findViewById(R.id.userLink);
            profile_img = (ImageView)itemView.findViewById(R.id.profile_img);

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

