package com.hugey.scattools;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ryanhugenberg on 6/13/17.
 */

public class ListView extends Fragment implements ScatDie.DieView, ScatTimer.TimerView {

    private Button mBtnDie;
    private Button mBtnPlay;
    private Button mBtnReset;

    private ListAdapter mAdapter;
    private RecyclerView mList;
    private Categories mCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_list, container, false);


        mBtnDie = (Button) v.findViewById(R.id.list_btn_die);
        mBtnPlay = (Button) v.findViewById(R.id.list_btn_play);
        mBtnReset = (Button) v.findViewById(R.id.list_btn_reset);

        mList = (RecyclerView) v.findViewById(R.id.list_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(manager);

        mAdapter = new ListAdapter(getContext());


        mList.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBtnPlay.setText("2:30");
        
    }

    @Override
    public void setDieText(String text) {

    }

    @Override
    public void setIsRolling(boolean rolling) {

    }

    @Override
    public void setTimerText(String text) {

    }
}
