package com.hugey.scattools.Scat;

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

    private static final String TIMER_TEXT_DEFAULT = "2:30";

    public static final int TICKING_PLAY = 1;
    public static final int TICKING_PAUSE = 2;
    public static final int TICKING_RESUME = 3;

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
            mTimerView.setIsTicking(TICKING_PAUSE);

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

        void setIsTicking(int ticking);
    }

    public void postTimerDelay() {
        mTimerHandler.postDelayed(mTimerRunnable, mSecondInterval);
    }

    public void removeTimerCallbacks() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
        mTimerView.setIsTicking(TICKING_RESUME);
    }

    public void resetTimerProgress() {
        mTimerProgress = 0;
        mTimerView.setIsTicking(TICKING_PLAY);
        mTimerView.setTimerText(TIMER_TEXT_DEFAULT);
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
