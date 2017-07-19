package com.hugey.scattools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    private static final String PLAY = "Play";
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";

    private static final String DIE_DEFAULT = "!";
    private static final String TIMER_DEFAULT = "2:30";


    private TextView mTvTimer;

    private ScatViewListener listener;

    public void setListener(ScatViewListener listener) {
        this.listener = listener;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_main, container, false);

        mBtnDie = (Button) v.findViewById(R.id.btn_die);
        mBtnPlay = (Button) v.findViewById(R.id.btn_play);
        mBtnReset = (Button) v.findViewById(R.id.btn_reset);
        mTvTimer = (TextView) v.findViewById(R.id.tv_timer);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTvTimer.setText(TIMER_DEFAULT);
        mBtnDie.setText(DIE_DEFAULT);

        mBtnDie.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play) {

            String s = mBtnPlay.getText().toString();
            if (s.equals(PLAY)) {
                mBtnPlay.setText(PAUSE);
                Log.d("Pressed play: ", s);
            } else if (s.equals(PAUSE)) {
                mBtnPlay.setText(RESUME);
                Log.d("Pressed pause: ", s);
            } else if (s.equals(RESUME)) {
                mBtnPlay.setText(PAUSE);
                Log.d("Pressed resume: ", s);
            }

            listener.onPlayClicked();
        } else if (v.getId() == R.id.btn_die) {
            listener.onDieClicked();
        } else if (v.getId() == R.id.btn_reset) {
            mTvTimer.setText(TIMER_DEFAULT);
            mBtnPlay.setText(PLAY);
            mBtnDie.setText(DIE_DEFAULT);
            listener.onResetClicked();
        }
    }

    public interface ScatViewListener {

        void onPlayClicked();

        void onResetClicked();

        void onDieClicked();
    }
}
