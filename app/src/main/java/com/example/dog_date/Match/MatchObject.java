package com.example.dog_date.Match;

public class MatchObject {

    private String userId;
    private String name;
    private String ImageUrl;

    public MatchObject (String userId, String name, String ImageUrl){
        this.userId = userId;
        this.name = name;
        this.ImageUrl = ImageUrl;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserID(String userID){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getImageUrl(){
        return ImageUrl;
    }
    public void setImageUrl(String ImageUrl){
        this.ImageUrl = ImageUrl;
    }
}
