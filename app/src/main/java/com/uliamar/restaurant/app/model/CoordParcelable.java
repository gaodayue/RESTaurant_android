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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeFloat(mLon);
        parcel.writeFloat(mLat);
    }

    private CoordParcelable(Parcel in) {
        mID = in.readInt();
        mLon = in.readFloat();
        mLat = in.readFloat();
    }

    public CoordParcelable(int id, float lon, float lat) {
        mID = id;
        mLat = lat;
        mLon = lon;
    }

    public static final Parcelable.Creator<CoordParcelable> CREATOR = new Parcelable.Creator<CoordParcelable>() {
        public CoordParcelable createFromParcel(Parcel in) {
            return new CoordParcelable(in);
        }

        public CoordParcelable[] newArray(int size) {
            return new CoordParcelable[size];
        }
    };
}
