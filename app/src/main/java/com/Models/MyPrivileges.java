package com.Models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 03/11/2015.
 */
public class MyPrivileges {

    @Expose
    private List<Privilege> items = new ArrayList<Privilege>();

    /**
     *
     * @return
     * The Privileges
     */
    public  List<Privilege> getItems() {
        return items;
    }

    /**
     *
     * @param Items
     * The Privileges
     */
    public void setItems(ArrayList<Privilege> Items) {
        this.items = Items;
    }
}
