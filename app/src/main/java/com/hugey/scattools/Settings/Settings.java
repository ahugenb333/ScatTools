package com.hugey.scattools.Settings;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 1/3/18.
 */

public class Settings implements Parcelable {

    private boolean mAlphabetDefault = true;
    private boolean mSkipPrevious = true;
    private String mTimerDuration = "2:30";

    public boolean getAlphabetDefault() {
        return mAlphabetDefault;
    }

    public boolean getSkipPrevious() {
        return mSkipPrevious;
    }

    public String getTimerDuration() {
        return mTimerDuration;
    }

    public void setAlphabetDefault(boolean isDefault) {
        mAlphabetDefault = isDefault;
    }

    public void setSkipPrevious(boolean skipPrevious) {
        mSkipPrevious = skipPrevious;
    }

    public void setTimerDuration(String duration) {
        mTimerDuration = duration;
    }

    public Settings(Parcel in) {
        mAlphabetDefault = in.readByte() != 0;
        mSkipPrevious = in.readByte() != 0;
        mTimerDuration = in.readString();
    }

    //default settings constructor
    public Settings() {

    }

    //construct from another settings object
    public Settings(Settings set) {
        setAlphabetDefault(set.getAlphabetDefault());
        setSkipPrevious(set.getSkipPrevious());
        setTimerDuration(set.getTimerDuration());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (mAlphabetDefault ? 1 : 0));
        parcel.writeByte((byte) (mSkipPrevious ? 1 : 0));
        parcel.writeString(mTimerDuration);
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
}
