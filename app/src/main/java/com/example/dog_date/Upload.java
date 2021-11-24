package com.example.dog_date;

public class Upload {
    private String UserID;
    private String ownerName;
    private String ownerGender;
    private String ownerAge;
    private String ownerBio;
    private String mImageUrl;
    private String ownerStates;

    private String dogName;
    private String dogBreed;
    private String dogAge;
    private String dogBio;

    public Upload(){
        // empty constructor needed
    }

    public Upload(String UserID, String ownerName, String ownerGender, String ownerAge, String mImageUrl, String ownerStates, String ownerBio, String dogName, String dogBreed, String dogAge, String dogBio){
        this.UserID = UserID;
        this.ownerName = ownerName;
        this.mImageUrl = mImageUrl;
        this.ownerAge = ownerAge;
        this.ownerGender = ownerGender;
        this.ownerStates = ownerStates;
        this.ownerBio = ownerBio;

        this.dogName = dogName;
        this.dogBreed = dogBreed;
        this.dogAge = dogAge;
        this.dogBio = dogBio;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerGender() {
        return ownerGender;
    }

    public void setOwnerGender(String ownerGender) {
        this.ownerGender = ownerGender;
    }

    public String getOwnerAge() {
        return ownerAge;
    }

    public void setOwnerAge(String ownerAge) {
        this.ownerAge = ownerAge;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getOwnerStates() {
        return ownerStates;
    }

    public void setOwnerStates(String ownerStates) {
        this.ownerStates = ownerStates;
    }

    public String getOwnerBio() { return ownerBio; }

    public void setOwnerBio(String ownerBio) { this.ownerBio = ownerBio; }

    public String getDogName() { return dogName; }

    public void setDogName(String dogName) { this.dogName = dogName; }

    public String getDogBreed() { return dogBreed; }

    public void setDogBreed(String dogBreed) { this.dogBreed = dogBreed; }

    public String getDogAge() { return dogAge; }

    public void setDogAge(String dogAge) { this.dogAge = dogAge; }

    public String getDogBio() { return dogBio; }

    public void setDogBio(String dogBio) { this.dogBio = dogBio; }
}
