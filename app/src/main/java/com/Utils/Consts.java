package com.Utils;

/**
 * Created by Manish on 30/10/2015.
 */
public class Consts {

    /**
     * Application-wide consts
     */

    public static String UserId = "1114338";
    public static final String mainURL = "http://api.stackexchange.com/2.2";
    public static final String mainURL2 = "https://api.stackexchange.com/2.2";
    public static final String UserInfoURL = mainURL + "/users/" + UserId + "?order=desc&sort=reputation&site=stackoverflow";
    public static final String flairURL = "http://stackoverflow.com/users/flair/" + UserId + ".png";
    public static final String QuestionsURL = mainURL +"/users/"+UserId+"/questions?order=desc&sort=activity&site=stackoverflow";
    public static final String FavQuestionsURL = mainURL +"/users/"+UserId+"/favorites?order=desc&sort=added&site=stackoverflow";
    public static final String MyPrivilegesURL = mainURL +"/users/"+UserId+"/privileges?site=stackoverflow";
    public static final String MyBadgesURL = mainURL +"/users/"+UserId+"/badges?order=desc&sort=rank&site=stackoverflow";
    public static final String ActiveTagsURL = mainURL +"/users/"+UserId+"/tags?page=1&pagesize=20&order=desc&sort=popular&site=stackoverflow";
    public static final int Card_MarginTop=25;
    public static final int Card_MarginBottom=25;

}