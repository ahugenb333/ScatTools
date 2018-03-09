package com.hugey.scattools.Settings;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 1/3/18.
 */

public class Settings implements Parcelable {

    public static final String TIMER_DURATION_200 = "2:00";
    public static final String TIMER_DURATION_230 = "2:30";
    public static final String TIMER_DURATION_300 = "3:00";

    private boolean mIsScatAlphabet = true;
    private boolean mSkipPrevious = true;
    private String mTimerDuration = TIMER_DURATION_230;

    public boolean isScatAlphabet() {
        return mIsScatAlphabet;
    }

    public boolean getSkipPrevious() {
        return mSkipPrevious;
    }

    public String getTimerDuration() {
        return mTimerDuration;
    }

    public void setAlphabetDefault(boolean isDefault) {
        mIsScatAlphabet = isDefault;
    }

    public void setSkipPrevious(boolean skipPrevious) {
        mSkipPrevious = skipPrevious;
    }

    public void setTimerDuration(String duration) {
        mTimerDuration = duration;
    }

    public Settings(Parcel in) {
        mIsScatAlphabet = in.readByte() != 0;
        mSkipPrevious = in.readByte() != 0;
        mTimerDuration = in.readString();
    }

    //default settings constructor
    public Settings() {

    }

    //copy constructor
    public Settings(Settings set) {
        setAlphabetDefault(set.isScatAlphabet());
        setSkipPrevious(set.getSkipPrevious());
        setTimerDuration(set.getTimerDuration());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (mIsScatAlphabet ? 1 : 0));
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