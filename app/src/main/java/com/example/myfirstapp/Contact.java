package com.example.myfirstapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String name, info;
    private int imageId;

    public Contact(){}

    private Contact(Parcel in) {
        name = in.readString();
        info = in.readString();
        imageId = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String cName) {
        this.name = cName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String cInfo) {
        this.info = cInfo;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int cImageId) {
        this.imageId = cImageId;
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

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}

