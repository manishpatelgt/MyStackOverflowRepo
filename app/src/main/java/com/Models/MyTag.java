package com.Models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 03/11/2015.
 */
public class MyTag {


    @Expose
    private List<ActiveTag> items = new ArrayList<ActiveTag>();

    /**
     *
     * @return
     * The ActiveTag
     */
    public  List<ActiveTag> getItems() {
        return items;
    }

    /**
     *
     * @param Items
     * The ActiveTag
     */
    public void setItems(ArrayList<ActiveTag> Items) {
        this.items = Items;
    }
}