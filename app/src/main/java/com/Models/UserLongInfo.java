package com.Models;

/**
 * Created by Manish on 30/10/2015.
 */
public class UserLongInfo {

    private int Age;
    private String Reputation,UserId,Location,Website,Link,Profile_image,display_name;

    public UserLongInfo(){
    }

    public void setAge(int Age){
        this.Age=Age;
    }

    public int getAge(){
        return Age;
    }

    public void setReputation(String Reputation){
        this.Reputation=Reputation;
    }

    public String getReputation(){
        return Reputation;
    }

    public void setLocation(String Location){
        this.Location=Location;
    }

    public String getLocation(){
        return Location;
    }

    public void setLink(String Link){
        this.Link=Link;
    }

    public String getLink(){
        return Link;
    }

    public void setWebsite(String Website){
        this.Website=Website;
    }

    public String getWebsite(){
        return Website;
    }

    public void setProfile_image(String Profile_image){
        this.Profile_image=Profile_image;
    }

    public String getProfile_image(){
        return Profile_image;
    }

    public void setdisplay_name(String display_name){
        this.display_name=display_name;
    }

    public String getdisplay_name(){
     return display_name;
    }

}
