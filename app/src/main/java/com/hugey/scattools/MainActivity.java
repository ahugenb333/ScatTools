package com.hugey.scattools;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    private Button mBtnGo;
    private Button mBtnPlay;
    private Button mBtnReset;
    private TextView mTvTimer;

    private Handler mDieHandler = new Handler();
    private Handler mTimerHandler = new Handler();
    private int mDieProgress = 0;
    private int mDieInterval = 80;
    private int mTimerProgress = 0;
    private int mTimerInterval = 1000;
    private int mTimer = 150;
    private int mCurrentLetter;
    private boolean mIsPlaying;


    private String[] mLetters;

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
                mBtnGo.setText(getRandom(mLetters));
            } else {
                mDieProgress = 0;
            }
        }
    };

    Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            mTimerProgress += mTimerInterval;

            int duration = mTimerProgress / 1000;
            int time = mTimer - duration;
            if (time == 0) {

            }
            String minutes = Integer.toString(time / 60);
            String seconds = Integer.toString(time % 60);
            String displayTime = minutes + ":" + seconds;
            mTvTimer.setText(displayTime);

            mTimerHandler.postDelayed(mTimerRunnable, mTimerInterval);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGo = (Button) findViewById(R.id.btn_go);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mTvTimer = (TextView) findViewById(R.id.tv_timer);

        mBtnPlay.setOnClickListener(this);
        mBtnGo.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);


        mTvTimer.setText("2:30");
        mCurrentLetter = 0;
        mLetters = getResources().getStringArray(R.array.letters);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBtnGo.setText(getRandom(mLetters));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go) {
            mDieHandler.postDelayed(mDieRunnable, mDieInterval);
        } else if (view.getId() == R.id.btn_play) {
            if (!mIsPlaying) {
                mTimerHandler.postDelayed(mTimerRunnable, mTimerInterval);
                mBtnPlay.setText("Pause");
            } else {
                mTimerHandler.removeCallbacks(mTimerRunnable);
                mBtnPlay.setText("Play");
            }
            mIsPlaying = !mIsPlaying;
        } else if (view.getId() == R.id.btn_reset) {
            mTimerHandler.removeCallbacks(mTimerRunnable);
            mBtnPlay.setText("Play");
            mTvTimer.setText("2:30");
            mTimerProgress = 0;
            mIsPlaying = false;
        }
    }

    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }


}
