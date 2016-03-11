package com.Models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 03/11/2015.
 */
public class MyBadges {

    @Expose
    private List<Badge> items = new ArrayList<Badge>();

    /**
     *
     * @return
     * The Badge
     */
    public  List<Badge> getItems() {
        return items;
    }

    /**
     *
     * @param Items
     * The Badge
     */
    public void setItems(ArrayList<Badge> Items) {
        this.items = Items;
    }
}
