package com.examble.myfirstapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable{

    private String name;
    private String info;
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

    public static final Creator<com.examble.myfirstapp.ListItem> CREATOR = new Creator<com.examble.myfirstapp.ListItem>() {
        @Override
        public com.examble.myfirstapp.ListItem createFromParcel(Parcel in) {
            return new com.examble.myfirstapp.ListItem(in);
        }

        @Override
        public com.examble.myfirstapp.ListItem[] newArray(int size) {
            return new com.examble.myfirstapp.ListItem[size];
        }
    };
}
