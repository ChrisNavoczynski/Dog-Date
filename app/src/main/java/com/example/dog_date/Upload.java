package com.example.dog_date;

public class Upload {
    private String ownerName;
    private String ownerGender;
    private String ownerAge;
    private String mImageUrl;
    private String ownerStates;

    public Upload(){
        // empty constructor needed
    }

    public Upload(String ownerName, String ownerGender, String ownerAge, String mImageUrl, String ownerStates){
        this.ownerName = ownerName;
        this.mImageUrl = mImageUrl;
        this.ownerAge = ownerAge;
        this.ownerGender = ownerGender;
        this.ownerStates = ownerStates;
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
}
