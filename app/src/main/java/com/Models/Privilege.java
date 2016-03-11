package com.Models;

import com.google.gson.annotations.Expose;

/**
 * Created by Manish on 03/11/2015.
 */
public class Privilege {

    @Expose
    private String reputation;

    @Expose
    private String description;

    @Expose
    private String short_description;


    /**
     *
     * @return
     * The short_description
     */
    public String getShortDescription() {
        return short_description;
    }

    /**
     *
     * @param
     * short_description
     */
    public void setShortDescription(String short_description) {
        this.short_description = short_description;
    }


    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param
     * description
     */
    public void setDescription(String description) {
        this.description = description;
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


}
