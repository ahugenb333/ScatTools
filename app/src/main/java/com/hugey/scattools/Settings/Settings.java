package com.hugey.scattools.Settings;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Objects;

/**
 * Created by user on 1/3/18.
 */

public class Settings implements Parcelable {

    public static final String TIMER_DURATION_200 = "2:00";
    public static final String TIMER_DURATION_230 = "2:30";
    public static final String TIMER_DURATION_300 = "3:00";
    public static final String TIMER_DURATION_000 = "0:00";

    public static final String EXPIRE_SOUND_RING = "Phone Ringing";
    public static final String EXPIRE_SOUND_ROOSTER = "Rooster";
    public static final String EXPIRE_SOUND_TRAIN = "Train Whistle";

    private String mExpireSound = EXPIRE_SOUND_RING;
    private boolean mIsTickSounds = false;

    private boolean mIsScatAlphabet = true;
    private boolean mSkipPrevious = false;

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

    public String getExpireSound() {
        return mExpireSound;
    }

    public boolean isTickSounds() {
        return mIsTickSounds;
    }

    public boolean isTimerDuration200() {
        return TextUtils.equals(mTimerDuration, TIMER_DURATION_200);
    }

    public boolean isTimerDuration230() {
        return TextUtils.equals(mTimerDuration, TIMER_DURATION_230);
    }

    public boolean isTimerDuration300() {
        return TextUtils.equals(mTimerDuration, TIMER_DURATION_300);
    }

    public boolean shouldPlayExpireSound(String text) {
        return (TextUtils.equals(text, TIMER_DURATION_000));
    }

    public boolean shouldPlayTickSound(String text) {
        return (mIsTickSounds && !TextUtils.equals(text, mTimerDuration));
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

    public void setExpireSound(String expireSound) {
        mExpireSound = expireSound;
    }

    public void setTickSounds(boolean tickSounds) {
        mIsTickSounds = tickSounds;
    }

    public Settings(Parcel in) {
        mIsTickSounds = in.readByte() != 0;
        mIsScatAlphabet = in.readByte() != 0;
        mSkipPrevious = in.readByte() != 0;
        mTimerDuration = in.readString();
        mExpireSound = in.readString();
    }

    //default settings constructor
    public Settings() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settings settings = (Settings) o;
        return mIsTickSounds == settings.mIsTickSounds &&
                mIsScatAlphabet == settings.mIsScatAlphabet &&
                mSkipPrevious == settings.mSkipPrevious &&
                TextUtils.equals(mExpireSound, settings.mExpireSound) &&
                TextUtils.equals(mTimerDuration, settings.mTimerDuration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (mIsTickSounds ? 1 : 0));
        parcel.writeByte((byte) (mIsScatAlphabet ? 1 : 0));
        parcel.writeByte((byte) (mSkipPrevious ? 1 : 0));
        parcel.writeString(mTimerDuration);
        parcel.writeString(mExpireSound);
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
