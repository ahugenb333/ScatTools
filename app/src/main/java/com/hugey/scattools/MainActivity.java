package com.hugey.scattools;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;

import java.util.Random;
//TODO Timer stuff in ScatTimer, Die stuff in ScatDie, fix tapping die bug, BASE ACTIVITY for button/timer view components

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ScatTimer.TimerView, ScatDie.DieView {


    public static final String EXTRA_PROGRESS = "progress";
    public static final String EXTRA_TEXT = "text";
    public static final String EXTRA_DIE = "die";


    private Button mBtnList;
    private Button mBtnTools;
    private Button mBtnEditable;

    private boolean mIsTicking = false;
    private boolean mIsRolling = false;

    private MyPagerAdapter mPagerAdapter;

    private ViewPager mViewPager;

    private ScatTimer mTimer;

    private Fragment ScatFragment;

    private String[] mLetters;

    private ScatDie mDie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer = new ScatTimer(this);
        mTimer.setTimerView(this);
        mDie = new ScatDie(this, getResources().getStringArray(R.array.letters));

        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);


//        mBtnDie = (Button) findViewById(R.id.btn_go);
//        mBtnPlay = (Button) findViewById(R.id.btn_play);
//        mBtnReset = (Button) findViewById(R.id.btn_reset);
//        mTvTimer = (TextView) findViewById(R.id.tv_timer); z
//
        mBtnList = (Button) findViewById(R.id.btn_list);
        mBtnTools = (Button) findViewById(R.id.btn_tools);
        mBtnEditable = (Button) findViewById(R.id.btn_editable);

        mBtnList.setOnClickListener(this);
        mBtnTools.setOnClickListener(this);
        mBtnEditable.setOnClickListener(this);
//
//        //bottom menu buttons
//        mBtnList.setOnClickListener(this);
//        mBtnTools.setOnClickListener(this);
//        mBtnEditable.setOnClickListener(this);
//
//        mBtnPlay.setOnClickListener(this);
//        mBtnDie.setOnClickListener(this);
//        mBtnReset.setOnClickListener(this);
//
//        mTvTimer.setText("2:30");
//
//        mDie.resetCurrentLetter();
//        mBtnDie.setText("!");
    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.btn_go && !mIsRolling) {
//            mDie.postDieDelayed();
//            mIsRolling = true;
//        } else if (view.getId() == R.id.btn_play) {
//            if (!mIsTicking) {
//                mTimer.postTimerDelay();
//                //mBtnPlay.setText("Pause");
//            } else {
//                mTimer.removeTimerCallbacks();
//                //mBtnPlay.setText("Play");
//            }
//            mIsTicking = !mIsTicking;
//        } else if (view.getId() == R.id.btn_reset) {
//            mTimer.removeTimerCallbacks();
//            mTimer.resetTimerProgress();
//            mBtnPlay.setText("Play");
//            mTvTimer.setText("2:30");
//            mIsTicking = false;
        if (view.getId() == R.id.btn_tools) {
            mViewPager.setCurrentItem(0);
        }
        if (view.getId() == R.id.btn_list) {
            mViewPager.setCurrentItem(1);
        }
        if (view.getId() == R.id.btn_editable) {
            mViewPager.setCurrentItem(3);
        }
    }

    @Override
    public void setTimerText(String text) {
        //mTvTimer.setText(text);
    }

    @Override
    public void setIsRolling(boolean rolling) {
        mIsRolling = rolling;
    }

    @Override
    public void setDieText(String text) {
        //mBtnDie.setText(text);
    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;

        private ScatView mScatView;
        private ListView mListView;
        private ScatView mTab3ScatView;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

            mScatView = new ScatView();
            mListView = new ListView();
            mTab3ScatView = new ScatView();
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mScatView;
            }
            if (position == 1) {
                return mListView;
            }
            if (position == 2) {
                return mTab3ScatView;
            }
            return null;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}
