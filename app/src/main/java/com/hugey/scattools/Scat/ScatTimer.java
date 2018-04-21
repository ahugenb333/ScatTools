package com.hugey.scattools.Scat;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by austin on 2/13/17.
 */

public class ScatTimer {

    private static final int TIMER_DURATION_200 = 120;
    private static final int TIMER_DURATION_230 = 150;
    private static final int TIMER_DURATION_300 = 180;

    private static final String TIMER_TEXT_200 = "2:00";
    private static final String TIMER_TEXT_230 = "2:30";
    private static final String TIMER_TEXT_300 = "3:00";

    private TimerView mTimerView;
    private Handler mTimerHandler;
    private int mTimerProgress = 0;
    private int mSecondInterval = 1000;
    private int mTimerDuration = TIMER_DURATION_230;
    private String mTimerText = TIMER_TEXT_230;

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

    public void setTimerView(TimerView timerView) {
        mTimerView = timerView;
    }

    public void postTimerDelay() {
        mTimerHandler.postDelayed(mTimerRunnable, mSecondInterval);
    }

    public void removeTimerCallbacks() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
    }

    public void resetTimerProgress() {
        mTimerProgress = 0;
        mTimerView.setTimerText(mTimerText);
    }

    public void setTimerDuration(String timerDuration) {
        mTimerText = timerDuration;
        if (TextUtils.equals(timerDuration, TIMER_TEXT_200)) {
            mTimerDuration = TIMER_DURATION_200;
            mTimerView.setTimerText(TIMER_TEXT_200);
        } else if (TextUtils.equals(timerDuration, TIMER_TEXT_230)) {
            mTimerDuration = TIMER_DURATION_230;
            mTimerView.setTimerText(TIMER_TEXT_230);
        } else if (TextUtils.equals(timerDuration, TIMER_TEXT_300)) {
            mTimerDuration = TIMER_DURATION_300;
            mTimerView.setTimerText(TIMER_TEXT_300);
        }


    }
}
