package com.hugey.scattools.Scat;

import android.os.Handler;
import android.text.TextUtils;

import java.util.Random;

/**
 * Created by austin on 2/13/17.
 */

public class ScatDie {

    public interface DieView {

        void setDieText(String text);

        void setIsRolling(boolean rolling);
    }

    private DieView mDieView;

    private Handler mDieHandler = new Handler();
    private int mDieProgress = 0;
    private int mDieInterval = 80;
    private int mCurrentLetter;
    private String[] mLetters;

    private boolean mSkipPrevious = false;
    private String mPrevious = "";


    Runnable mDieRunnable = new Runnable() {
        @Override
        public void run() {
            mDieProgress += mDieInterval;
            if (mDieProgress < 1200) {
                mDieHandler.postDelayed(mDieRunnable, mDieInterval);
                mCurrentLetter++;
                if (mLetters.length == mCurrentLetter) {
                    mCurrentLetter = 0;
                }
                mPrevious = getRandomLetter();
                mDieView.setDieText(mPrevious);
            } else {
                mDieProgress = 0;
                mDieView.setIsRolling(false);
            }
        }
    };

    public ScatDie(DieView view, String[] letters) {
        mDieView = view;
        mLetters = letters;
    }

    public void setLetters(String[] letters) {
        mLetters = letters;
    }

    public void resetCurrentLetter() {
        mCurrentLetter = 0;
    }

    private static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    private static String getNewRandom(String[] array, String previous) {
        int rnd = new Random().nextInt(array.length);
        if (!TextUtils.equals(array[rnd], previous)) {
            return array[rnd];
        } else {
            return getNewRandom(array, previous);
        }
    }

    public void postDieDelayed() {
        mDieHandler.postDelayed(mDieRunnable, mDieInterval);
    }

    public void removeDieCallbacks() {
        mDieHandler.removeCallbacks(mDieRunnable);
        mDieView.setIsRolling(false);
    }

    public String getRandomLetter() {
        if (mSkipPrevious) {
            return getRandom(mLetters);
        }
        else {
            return getNewRandom(mLetters, mPrevious);
        }
    }

    public void setSkipPrevious(boolean skipPrevious) {
        mSkipPrevious = skipPrevious;
    }
}
