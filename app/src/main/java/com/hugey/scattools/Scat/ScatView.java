package com.hugey.scattools.Scat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hugey.scattools.R;

/**
 * Created by ryanhugenberg on 6/13/17.
 */

public class ScatView extends Fragment implements ScatTimer.TimerView, ScatDie.DieView, View.OnClickListener {

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

    private String mTimerText = TIMER_DEFAULT;
    private String mDieText = DIE_DEFAULT;

    private String mPlayText = PLAY;

    public void setListener(ScatViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void setTimerText(String text) {
        mTimerText = text;
        if (mTvTimer != null) {
            mTvTimer.setText(mTimerText);
        }
    }

    @Override
    public void setDieText(String text) {
        mDieText = text;
        if (mBtnDie != null) {
            mBtnDie.setText(mDieText);
        }

    }

    @Override
    public void setIsTicking(int ticking) {
        if (ticking == ScatTimer.TICKING_PLAY) {
            mPlayText = PLAY;
        } else if (ticking == ScatTimer.TICKING_PAUSE) {
            mPlayText = PAUSE;
        } else if (ticking == ScatTimer.TICKING_RESUME) {
            mPlayText = RESUME;
        }
        if (mBtnPlay != null) {
            mBtnPlay.setText(mPlayText);
        }
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

        mBtnPlay.setText(mPlayText);

        mTvTimer.setText(mTimerText);
        mBtnDie.setText(mDieText);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBtnDie.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play) {
            if (mPlayText.equals(PLAY)) {
                mBtnPlay.setText(PAUSE);
            } else if (mPlayText.equals(PAUSE)) {
                mBtnPlay.setText(RESUME);
            } else if (mPlayText.equals(RESUME)) {
                mBtnPlay.setText(PAUSE);
            }
            mPlayText = mBtnPlay.getText().toString();
            listener.onPlayClicked();
        } else if (v.getId() == R.id.btn_die) {
            listener.onDieClicked();
        } else if (v.getId() == R.id.btn_reset) {
            mTvTimer.setText(TIMER_DEFAULT);
            mBtnDie.setText(DIE_DEFAULT);
            mPlayText = PLAY;
            mBtnPlay.setText(mPlayText);
            listener.onResetClicked();
        }
    }

    public interface ScatViewListener {

        void onPlayClicked();

        void onResetClicked();

        void onDieClicked();
    }
}
