package com.example.prashant_admin.fetchnews.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Source implements Parcelable{
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    public Source(){

    }

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Source(Parcel in) {
        readFromParcel(in);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public void readFromParcel(Parcel in){
        id = in.readString();
        name = in.readString();
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Source createFromParcel(Parcel in) {
                    return new Source(in);
                }

                public Source[] newArray(int size) {
                    return new Source[size];
                }
            };
}
