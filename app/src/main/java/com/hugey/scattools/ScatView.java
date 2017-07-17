package com.hugey.scattools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ryanhugenberg on 6/13/17.
 */

public class ScatView extends Fragment implements ScatTimer.TimerView, ScatDie.DieView, View.OnClickListener {
    
    private int pos;

    private Button mBtnDie;
    private Button mBtnPlay;
    private Button mBtnReset;

    private TextView mTvTimer;

    public void setListener(View.OnClickListener listener) {
        mBtnDie.setOnClickListener(listener);
        mBtnPlay.setOnClickListener(listener);
        mBtnReset.setOnClickListener(listener);
    }

    @Override
    public void setTimerText(String text) {
        if (mTvTimer != null) {
            mTvTimer.setText(text);
        }
    }

    @Override
    public void setDieText(String text) {
        mBtnDie.setText(text);
    }

    @Override
    public void setIsRolling(boolean rolling) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutId(), container, false);

        mBtnDie = (Button) v.findViewById(R.id.btn_die);
        mBtnPlay = (Button) v.findViewById(R.id.btn_play);
        mBtnReset = (Button) v.findViewById(R.id.btn_reset);
        mTvTimer = (TextView) v.findViewById(R.id.tv_timer);


//        mBtnPlay.setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTvTimer.setText("2:30");
//        mBtnDie.setText("1");
    }

    public int getLayoutId() {
        return R.layout.view_main;
    }

    @Override
    public void onClick(View v) {

    }
}
