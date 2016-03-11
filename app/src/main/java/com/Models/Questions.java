package com.Models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 02/11/2015.
 */
public class Questions {

    @Expose
    private List<QuestionItems> items = new ArrayList<QuestionItems>();

    /**
     *
     * @return
     * The QuestionItems
     */
    public  List<QuestionItems> getItems() {
        return items;
    }

    /**
     *
     * @param Items
     * The QuestionItems
     */
    public void setItems(ArrayList<QuestionItems> Items) {
        this.items = Items;
    }

}
