package com.example.dog_date.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class OwnerPreferenceItems implements Parcelable {

    public String UserID;
    public String ownerGenderP;
    public String ownerMaxAge;
    public String ownerMinAge;

    public OwnerPreferenceItems(String userId, String ownerGenderP, EditText ownerMaxAge, EditText ownerMinAge) {
        //constructor
    }

    public OwnerPreferenceItems(String UserID, String ownerGenderP, String ownerMaxAge, String ownerMinAge) {
        this.UserID = UserID;
        this.ownerGenderP = ownerGenderP;
        this.ownerMaxAge = ownerMaxAge;
        this.ownerMinAge = ownerMinAge;

    }


    protected OwnerPreferenceItems(Parcel in) {
        UserID = in.readString();
        ownerGenderP = in.readString();
        ownerMaxAge = in.readString();
        ownerMinAge = in.readString();
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

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserID);
        dest.writeString(ownerGenderP);
        dest.writeString(ownerMaxAge);
        dest.writeString(ownerMinAge);
    }
}
