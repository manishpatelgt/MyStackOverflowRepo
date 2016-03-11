package com.Models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 30/10/2015.
 */
public class Items {

    @Expose
    private String website_url;

    @Expose
    private String profile_image;

    @Expose
    private String display_name;

    @Expose
    private String link;

    @Expose
    private String location;

    @Expose
    private String reputation;

    @Expose
    private String age;

    @Expose
    private String user_id;

    @Expose
    private  Badges badge_counts;

    @Expose
    private  String creation_date;

    @Expose
    private  String last_access_date;

    public Badges getBadges() {
        return badge_counts;
    }


    public void setBadges(Badges badges) {
        this.badge_counts = badges;
    }


    /**
     *
     * @return
     * The display_name
     */
    public String getDisplayName() {
        return display_name;
    }

    /**
     *
     * @param
     * display_name
     */
    public void setDisplayName(String display_name) {
        this.display_name = display_name;
    }

    /**
     *
     */
    /**
     *
     * @return
     * The profile_image
     */
    public String getProfile() {
        return profile_image;
    }

    /**
     *
     * @param
     * profile_image
     */
    public void setProfile(String profile_image) {
        this.profile_image = profile_image;
    }

    /**
     *
     * @return
     * The website_url
     */
    public String getWebSite() {
        return website_url;
    }

    /**
     *
     * @param
     * website_url
     */
    public void setWebSite(String website_url) {
        this.website_url = website_url;
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
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param
     * location
     */
    public void setLocation(String location) {
        this.location = location;
    }


    /**
     *
     * @return
     * The reputation
     */
    public String getReputation() {
        return reputation;
    }

    /**
     *
     * @param
     * reputation
     */
    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    /**
     *
     * @return
     * The age
     */
    public String getAge() {
        return age;
    }

    /**
     *
     * @param
     * age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     *
     * @return
     * The user_id
     */
    public String getUserId() {
        return user_id;
    }

    /**
     *
     * @param
     * user_id
     */
    public void setUserId(String user_id) {
        this.user_id = user_id;
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
     * The last_access_date
     */
    public String getLastAccessDate() {
        return last_access_date;
    }

    /**
     *
     * @param
     * last_access_date
     */
    public void setLastAccessDate(String last_access_date) {
        this.last_access_date = last_access_date;
    }






}
