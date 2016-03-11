package com.APICall;

import com.Models.FavQuestions;
import com.Models.MyBadges;
import com.Models.MyPrivileges;
import com.Models.MyTag;
import com.Models.QuestionItems;
import com.Models.Questions;
import com.Models.UserShortInfo;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Manish on 31/10/2015.
 */
public interface RestInterface {

    //get UserId
    @GET("/me")
    void getUserId(@Query("site") String site,@Query("key") String apikey,@Query("access_token") String access_token,Callback<UserShortInfo> cb);

    //get UserInformation
    @GET("/users/{userId}?order=desc&sort=reputation&site=stackoverflow")
    void getUserInfo(@Path("userId") String userId, Callback<UserShortInfo> cb);

    //get User All Questions
    @GET("/users/{userId}/questions?order=desc&sort=activity&site=stackoverflow")
    void getQuestions(@Path("userId") String userId, Callback<Questions> cb);


    //get User All Favorites Questions
    @GET("/users/{userId}/favorites?page=1&pagesize=50&order=desc&sort=activity&site=stackoverflow")
    void getFavQuestions(@Path("userId") String userId, Callback<FavQuestions> cb);


    //get User Privileges
    @GET("/users/{userId}/privileges?site=stackoverflow")
    void getPrivileges(@Path("userId") String userId, Callback<MyPrivileges> cb);

    //get User Badges
    @GET("/users/{userId}/badges?page=1&pagesize=80&order=desc&sort=type&site=stackoverflow")
    void getBadges(@Path("userId") String userId, Callback<MyBadges> cb);


    //get User Active Tags
    @GET("/users/{userId}/tags?page=1&pagesize=20&order=desc&sort=popular&site=stackoverflow")
    void getActiveTags(@Path("userId") String userId, Callback<MyTag> cb);

}
