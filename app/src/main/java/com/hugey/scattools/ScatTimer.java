package com.hugey.scattools;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import io.reactivex.internal.operators.observable.ObservableInterval;

/**
 * Created by austin on 2/13/17.
 */

public class ScatTimer {

    private TimerView mTimerView;
    private Handler mTimerHandler;
    private int mTimerProgress = 0;
    private int mSecondInterval = 1000;
    private int mTimerDuration = 150;

    Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            mTimerProgress += mSecondInterval;

            int duration = mTimerProgress / 1000;
            int time = mTimerDuration - duration;

            String minutes = Integer.toString(time / 60);
            String seconds = Integer.toString(time % 60);
            if (time % 60 < 10) {
                seconds = "0" + seconds;
            }
            String displayTime = minutes + ":" + seconds;
            mTimerView.setTimerText(displayTime);

            if (time > 0) {
                mTimerHandler.postDelayed(mTimerRunnable, mSecondInterval);
            }
        }
    };

    public ScatTimer(TimerView timerView) {
        mTimerView = timerView;
        mTimerHandler = new Handler();
    }

    public interface TimerView {
        void setTimerText(String text);
    }

    public void postTimerDelay() {
        mTimerHandler.postDelayed(mTimerRunnable, mSecondInterval);
    }

    public void postTimerDelay(int delay) {
        mTimerHandler.postDelayed(mTimerRunnable, mSecondInterval);
    }

    public void removeTimerCallbacks() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
    }

    public void resetTimerProgress() {
        mTimerProgress = 0;
    }

    public void resetTimerProgress(int progress) {
        mTimerProgress = progress;
    }

    public void setTimerView(TimerView timerView) {
        mTimerView = timerView;
    }

    public int getTimerProgress() {
        return mTimerProgress;
    }
}
