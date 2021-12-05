package com.example.dog_date.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class OwnerPreferenceItems implements Parcelable {

    public String ownerGenderP;
    public String ownerMaxAge;
    public String ownerMinAge;
    public String UserID;
    public String genderDogP;
    public String dogSizeP;
    public String breedP;
    public String dogMaxAgeP;
    public String dogMinAgep;

    public OwnerPreferenceItems() {
        //constructor
    }

    public OwnerPreferenceItems(String ownerGenderP, String ownerMaxAge, String ownerMinAge, String UserID, String genderDogP, String dogSizeP, String breedP, String dogMaxAgeP, String dogMinAgep) {
        this.ownerGenderP = ownerGenderP;
        this.ownerMaxAge = ownerMaxAge;
        this.ownerMinAge = ownerMinAge;
        this.UserID = UserID;
        this.genderDogP = genderDogP;
        this.dogSizeP = dogSizeP;
        this.breedP = breedP;
        this.dogMaxAgeP = dogMaxAgeP;
        this.dogMinAgep = dogMinAgep;

    }


    protected OwnerPreferenceItems(Parcel in) {
        ownerGenderP = in.readString();
        ownerMaxAge = in.readString();
        ownerMinAge = in.readString();
        UserID = in.readString();
        genderDogP = in.readString();
        dogSizeP = in.readString();
        breedP = in.readString();
        dogMaxAgeP = in.readString();
        dogMinAgep = in.readString();
    }

    public static final Creator<OwnerPreferenceItems> CREATOR = new Creator<OwnerPreferenceItems>() {
        @Override
        public OwnerPreferenceItems createFromParcel(Parcel in) {
            return new OwnerPreferenceItems(in);
        }

        @Override
        public OwnerPreferenceItems[] newArray(int size) {
            return new OwnerPreferenceItems[size];
        }
    };




    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ownerGenderP", ownerGenderP);
        result.put("ownerMaxAge", ownerMaxAge);
        result.put("ownerMinAge", ownerMinAge);
        result.put("dogGenderP", genderDogP);
        result.put("dogSizeP", dogSizeP);
        result.put("dogBreedP", breedP);
        result.put("dogMaxAgeP", dogMaxAgeP);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ownerGenderP);
        dest.writeString(ownerMaxAge);
        dest.writeString(ownerMinAge);
        dest.writeString(UserID);
        dest.writeString(genderDogP);
        dest.writeString(dogSizeP);
        dest.writeString(breedP);
        dest.writeString(dogMaxAgeP);
        dest.writeString(dogMinAgep);
    }
}
