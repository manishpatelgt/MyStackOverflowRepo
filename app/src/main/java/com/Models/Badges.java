package com.Models;

import com.google.gson.annotations.Expose;

/**
 * Created by Manish on 01/11/2015.
 */
public class Badges {

    @Expose
    private String bronze;

    @Expose
    private String silver;

    @Expose
    private String gold;

    /**
     *
     * @return
     * The bronze
     */
    public String getBronze() {
        return bronze;
    }

    /**
     *
     * @param
     * bronze
     */
    public void setBronze(String bronze) {
        this.bronze = bronze;
    }

    /**
     *
     * @return
     * The silver
     */
    public String getSilver() {
        return silver;
    }

    /**
     *
     * @param
     * silver
     */
    public void setSilver(String silver) {
        this.silver = silver;
    }

    /**
     *
     * @return
     * The gold
     */
    public String getGold() {
        return gold;
    }

    /**
     *
     * @param
     * gold
     */
    public void setGold(String gold) {
        this.gold = gold;
    }





}
