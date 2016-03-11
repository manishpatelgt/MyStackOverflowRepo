package com.Models;

import com.google.gson.annotations.Expose;

/**
 * Created by Manish on 03/11/2015.
 */
public class Badge {

    @Expose
    private String badge_type;

    @Expose
    private String award_count;

    @Expose
    private String rank;

    @Expose
    private String link;

    @Expose
    private String name;



    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param
     * name
     */
    public void setName(String name) {
        this.name = name;
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
     * The rank
     */
    public String getRank() {
        return rank;
    }

    /**
     *
     * @param
     * rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }


    /**
     *
     * @return
     * The badge_type
     */
    public String getBadgeType() {
        return badge_type;
    }

    /**
     *
     * @param
     * badge_type
     */
    public void setBadgeType(String badge_type) {
        this.badge_type = badge_type;
    }

    /**
     *
     * @return
     * The award_count
     */
    public String getAwardCount() {
        return award_count;
    }

    /**
     *
     * @param
     * award_count
     */
    public void setAwardCount(String award_count) {
        this.award_count = award_count;
    }




}
