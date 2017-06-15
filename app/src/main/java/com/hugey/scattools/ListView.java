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

public class ListView extends Fragment {
    private static final String TAG = "ScatView";

    private Button mBtnDie;
    private Button mBtnPlay;
    private Button mBtnReset;


    private TextView mTvTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_list, container, false);

//        mBtnDie = (Button) v.findViewById(R.id.btn_go);
//        mBtnPlay = (Button) v.findViewById(R.id.btn_play);
//        mBtnReset = (Button) v.findViewById(R.id.btn_reset);
//        mTvTimer = (TextView) v.findViewById(R.id.tv_timer);
//
//        mTvTimer.setText("2:30");
//        mBtnDie.setText("1");

        return v;
    }




}
