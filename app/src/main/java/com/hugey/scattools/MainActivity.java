package com.hugey.scattools;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
//TODO Timer stuff in ScatTimer, Die stuff in ScatDie

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ScatTimer.TimerView {

    private BottomNavigationView mBtnNav;
    private Button mBtnDie;
    private Button mBtnPlay;
    private Button mBtnReset;
    private Button mBtnList;
    private TextView mTvTimer;

    private Handler mDieHandler = new Handler();
    private int mDieProgress = 0;
    private int mDieInterval = 80;
    private int mCurrentLetter;
    private boolean mIsPlaying;

    private ScatTimer mTimer;

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
                mBtnDie.setText(getRandom(mLetters));
            } else {
                mDieProgress = 0;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer = new ScatTimer(this);

        mBtnNav = (BottomNavigationView) findViewById(R.id.list_nav_view);
        mBtnDie = (Button) findViewById(R.id.btn_go);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mTvTimer = (TextView) findViewById(R.id.tv_timer);
        mBtnList = (Button) findViewById(R.id.activity_list_btn);

        mBtnNav.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnDie.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);


        mTvTimer.setText("2:30");
        mCurrentLetter = 0;
        mLetters = getResources().getStringArray(R.array.letters);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBtnDie.setText(getRandom(mLetters));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go) {
            mDieHandler.postDelayed(mDieRunnable, mDieInterval);
        } else if (view.getId() == R.id.btn_play) {
            if (!mIsPlaying) {
                mTimer.postTimerDelay();
                mBtnPlay.setText("Pause");
            } else {
                mTimer.removeTimerCallbacks();
                mBtnPlay.setText("Play");
            }
            mIsPlaying = !mIsPlaying;
        } else if (view.getId() == R.id.btn_reset) {
            mTimer.removeTimerCallbacks();
            mTimer.resetTimerProgress();
            mBtnPlay.setText("Play");
            mTvTimer.setText("2:30");
            mIsPlaying = false;
        } else if (view.getId() == R.id.activity_list_btn) {
        } else if (view.getId() == R.id.list_nav_view) {
            startActivity(new Intent(this, ListActivity.class));
        }
    }

    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    @Override
    public void setTimerText(String text) {
        mTvTimer.setText(text);
    }


}
