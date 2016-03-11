package com.Models;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 03/11/2015.
 */
public class FavQuestions {

    @Expose
    private List<FavQuestionsItems> items = new ArrayList<FavQuestionsItems>();

    /**
     *
     * @return
     * The FavQuestionsItems
     */
    public  List<FavQuestionsItems> getItems() {
        return items;
    }

    /**
     *
     * @param Items
     * The FavQuestionsItems
     */
    public void setItems(ArrayList<FavQuestionsItems> Items) {
        this.items = Items;
    }
}
