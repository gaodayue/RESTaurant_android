package com.uliamar.restaurant.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pol on 18/05/14.
 */
public class CoordParcelable implements Parcelable {
    private int mID;
    private float mLon;
    private float mLat;
    private String mName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeFloat(mLon);
        parcel.writeFloat(mLat);
        parcel.writeString(mName);
    }

    private CoordParcelable(Parcel in) {
        mID = in.readInt();
        mLon = in.readFloat();
        mLat = in.readFloat();
        mName = in.readString();
    }

    public CoordParcelable(int id, float lon, float lat, String name) {
        mID = id;
        mLat = lat;
        mLon = lon;
        mName = name;
    }

    public static final Parcelable.Creator<CoordParcelable> CREATOR = new Parcelable.Creator<CoordParcelable>() {
        public CoordParcelable createFromParcel(Parcel in) {
            return new CoordParcelable(in);
        }

        public CoordParcelable[] newArray(int size) {
            return new CoordParcelable[size];
        }
    };

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public float getmLon() {
        return mLon;
    }

    public void setmLon(float mLon) {
        this.mLon = mLon;
    }

    public float getmLat() {
        return mLat;
    }

    public void setmLat(float mLat) {
        this.mLat = mLat;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
