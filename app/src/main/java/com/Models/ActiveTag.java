package com.Models;

import com.google.gson.annotations.Expose;

/**
 * Created by Manish on 03/11/2015.
 */
public class ActiveTag {

    @Expose
    private String count;

    @Expose
    private String name;


    /**
     *
     * @return
     * The count
     */
    public String getCount() {
        return count;
    }

    /**
     *
     * @param
     * count
     */
    public void setCount(String count) {
        this.count = count;
    }

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

}
