package com.Models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 03/11/2015.
 */
public class FavQuestionsItems {

    @Expose
    private Owner owner;

    @Expose
    private List<String> tags = new ArrayList<String>();

    @Expose
    private boolean is_answered;

    @Expose
    private String view_count;

    @Expose
    private String answer_count;

    @Expose
    private  String score;

    @Expose
    private  String creation_date;

    @Expose
    private String link;

    @Expose
    private String title;

    public Owner getOwner() {
        return owner;
    }


    public void setOwner(Owner owner) {
        this.owner = owner;
    }


    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param
     * title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param
     * link
     */
    public void setLink(String link) {
        this.link = link;
    }


    /**
     *
     * @return
     * The creation_date
     */
    public String getCreateDate() {
        return creation_date;
    }

    /**
     *
     * @param
     * creation_date
     */
    public void setCreateDate(String creation_date) {
        this.creation_date = creation_date;
    }


    /**
     *
     * @return
     * The score
     */
    public String getScore() {
        return score;
    }

    /**
     *
     * @param
     * score
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     *
     * @return
     * The answer_count
     */
    public String getAnswer_count() {
        return answer_count;
    }

    /**
     *
     * @param
     * answer_count
     */
    public void setAnswer_count(String answer_count) {
        this.answer_count = answer_count;
    }

    /**
     *
     * @return
     * The view_count
     */
    public String getView_count() {
        return view_count;
    }

    /**
     *
     * @param
     * view_count
     */
    public void setView_count(String view_count) {
        this.view_count = view_count;
    }


    /**
     *
     * @return
     * The is_answered
     */
    public boolean getIs_answered() {
        return is_answered;
    }

    /**
     *
     * @param
     * is_answered
     */
    public void setIs_answered(boolean is_answered) {
        this.is_answered = is_answered;
    }

    /**
     *
     * @return
     * The tags
     */
    public  List<String> getTags() {
        return tags;
    }

    /**
     *
     * @param Items
     * The tags
     */
    public void setTags(ArrayList<String> Items) {
        this.tags = Items;
    }
}
