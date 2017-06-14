package com.hugey.scattools;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ryanhugenberg on 6/13/17.
 */

public class ScatView extends Fragment {

    private Button mBtnDie;
    private Button mBtnPlay;
    private Button mBtnReset;

    private Button mBtnList;
    private Button mBtnTools;
    private Button mBtnEditable;

    private TextView mTvTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_main, container, false);

        mBtnDie = (Button) v.findViewById(R.id.btn_go);
        mBtnPlay = (Button) v.findViewById(R.id.btn_play);
        mBtnReset = (Button) v.findViewById(R.id.btn_reset);
        mTvTimer = (TextView) v.findViewById(R.id.tv_timer);

        return v;
    }

    public void init(String timer, String die, View.OnClickListener listener) {

        mBtnDie.setOnClickListener(listener);
        mBtnPlay.setOnClickListener(listener);
        mBtnReset.setOnClickListener(listener);

        mBtnDie.setText(die);
        mTvTimer.setText(timer);
    }





}
