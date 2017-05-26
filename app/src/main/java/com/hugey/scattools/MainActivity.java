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
//TODO Timer stuff in ScatTimer, Die stuff in ScatDie, fix tapping die bug, BASE ACTIVITY for button/timer view components

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ScatTimer.TimerView, ScatDie.DieView {


    public static final String EXTRA_PROGRESS = "progress";
    public static final String EXTRA_TEXT = "text";
    private BottomNavigationView mBtnNav;
    private Button mBtnDie;
    private Button mBtnPlay;
    private Button mBtnReset;
    private Button mBtnList;
    private TextView mTvTimer;

    private boolean mIsTicking = false;
    private boolean mIsRolling = false;

    private ScatTimer mTimer;

    private String[] mLetters;

    private ScatDie mDie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer = new ScatTimer(this);
        mTimer.setTimerView(this);
        mDie = new ScatDie(this, getResources().getStringArray(R.array.letters));

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

        mDie.resetCurrentLetter();
        mBtnDie.setText("!");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go && !mIsRolling) {
            mDie.postDieDelayed();
            mIsRolling = true;
        } else if (view.getId() == R.id.btn_play) {
            if (!mIsTicking) {
                mTimer.postTimerDelay();
                mBtnPlay.setText("Pause");
            } else {
                mTimer.removeTimerCallbacks();
                mBtnPlay.setText("Play");
            }
            mIsTicking = !mIsTicking;
        } else if (view.getId() == R.id.btn_reset) {
            mTimer.removeTimerCallbacks();
            mTimer.resetTimerProgress();
            mBtnPlay.setText("Play");
            mTvTimer.setText("2:30");
            mIsTicking = false;
        } else if (view.getId() == R.id.activity_list_btn) {
        } else if (view.getId() == R.id.list_nav_view) {
            int progress = mTimer.getTimerProgress();
            String text = mTvTimer.getText().toString();

            Intent startIntent = new Intent(this, ListActivity.class);

            startIntent.putExtra(EXTRA_PROGRESS, progress);

            startIntent.putExtra(EXTRA_TEXT, text);

            startActivity(startIntent);
        }
    }

    @Override
    public void setTimerText(String text) {
        mTvTimer.setText(text);
    }

    @Override
    public void setIsRolling(boolean rolling) {
        mIsRolling = rolling;
    }

    @Override
    public void setDieText(String text) {
        mBtnDie.setText(text);
    }
}
