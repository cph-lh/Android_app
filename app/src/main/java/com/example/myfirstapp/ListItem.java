package com.example.myfirstapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable{

    private String name, info;
    private int imageId;

    public ListItem() {
    }

    private ListItem(Parcel in) {
        name = in.readString();
        info = in.readString();
        imageId = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String iName) {
        this.name = iName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String iInfo) {
        this.info = iInfo;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int iImageId) {
        this.imageId = iImageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(name);
        out.writeString(info);
        out.writeInt(imageId);
    }

    public static final Creator<com.example.myfirstapp.ListItem> CREATOR = new Creator<com.example.myfirstapp.ListItem>() {
        @Override
        public com.example.myfirstapp.ListItem createFromParcel(Parcel in) {
            return new com.example.myfirstapp.ListItem(in);
        }

        @Override
        public com.example.myfirstapp.ListItem[] newArray(int size) {
            return new com.example.myfirstapp.ListItem[size];
        }
    };
}
