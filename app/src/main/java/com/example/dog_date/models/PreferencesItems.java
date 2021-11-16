package com.example.dog_date.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class PreferencesItems implements Parcelable {
    public String UserID;
    public String genderDogP;
    public String dogSizeP;
    public String breedP;
    public String dogMaxAgeP;
    public String dogMinAgep;

    public PreferencesItems() {
        // Default constructor
    }

    public PreferencesItems(String UserID, String genderDogP, String dogSizeP, String breedP, String dogMaxAgeP, String dogMinAgep) {
        this.UserID = UserID;
        this.genderDogP = genderDogP;
        this.dogSizeP = dogSizeP;
        this.breedP = breedP;
        this.dogMaxAgeP = dogMaxAgeP;
        this.dogMinAgep = dogMinAgep;
    }


    protected PreferencesItems(Parcel in) {
        UserID = in.readString();
        genderDogP = in.readString();
        dogSizeP = in.readString();
        breedP = in.readString();
        dogMaxAgeP = in.readString();
        dogMinAgep = in.readString();
    }

    public static final Creator<PreferencesItems> CREATOR = new Creator<PreferencesItems>() {
        @Override
        public PreferencesItems createFromParcel(Parcel in) {
            return new PreferencesItems(in);
        }

        @Override
        public PreferencesItems[] newArray(int size) {
            return new PreferencesItems[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dogGenderP", genderDogP);
        result.put("dogSizeP", dogSizeP);
        result.put("dogBreedP", breedP);
        result.put("dogMaxAgeP", dogMaxAgeP);
        result.put("dogMinAgeP", dogMinAgep);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserID);
        dest.writeString(genderDogP);
        dest.writeString(dogSizeP);
        dest.writeString(breedP);
        dest.writeString(dogMaxAgeP);
        dest.writeString(dogMinAgep);
    }

}
