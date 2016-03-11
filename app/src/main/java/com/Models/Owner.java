package com.Models;

import com.google.gson.annotations.Expose;

/**
 * Created by Manish on 03/11/2015.
 */
public class Owner {

    @Expose
    private String link;

    @Expose
    private String profile_image;

    @Expose
    private String display_name;

    @Expose
    private String reputation;


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
}
