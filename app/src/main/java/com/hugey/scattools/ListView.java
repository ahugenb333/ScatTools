package com.hugey.scattools;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ryanhugenberg on 6/13/17.
 */

public class ListView extends Fragment implements View.OnClickListener, ScatDie.DieView, ScatTimer.TimerView, TextWatcher {

    private Button mBtnDie;
    private Button mBtnReset;
    private Button mBtnTimer;

    private EditText mEtListId;
    private Button mBtnRandomize;

    private TextView mTvPlay;

    private ListAdapter mAdapter;
    private RecyclerView mList;
    private Categories mCategories;

    private ListViewListener listener;

    private static final String PLAY = "Play";
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";

    private static final String DIE_DEFAULT = "!";
    private static final String TIMER_DEFAULT = "2:30";

    public interface ListViewListener {

        void onDieClicked();

        void onPlayClicked();

        void onResetClicked();

    }


    public void setListener(ListViewListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_list, container, false);


        mBtnDie = (Button) v.findViewById(R.id.list_btn_die);
        mBtnReset = (Button) v.findViewById(R.id.list_btn_reset);
        mBtnTimer = (Button) v.findViewById(R.id.list_btn_timer);

        mEtListId = (EditText) v.findViewById(R.id.list_et_id);
        mBtnRandomize = (Button) v.findViewById(R.id.list_btn_randomize);

        mEtListId.addTextChangedListener(this);

        mTvPlay = (TextView) v.findViewById(R.id.list_tv_play);

        mList = (RecyclerView) v.findViewById(R.id.list_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(manager);

        mAdapter = new ListAdapter(getContext());


        mList.setAdapter(mAdapter);

        Gson gson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();

        mCategories = gson.fromJson(loadJSONFromAsset("category.json"), Categories.class);

        mAdapter.setCategories(mCategories.getListByID(1));

        mAdapter.notifyDataSetChanged();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTvPlay.setText(PLAY);
        mBtnTimer.setText(TIMER_DEFAULT);
        mBtnDie.setText(DIE_DEFAULT);

        mBtnTimer.setOnClickListener(this);
        mBtnDie.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
        mBtnRandomize.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.list_btn_timer) {

            String s = mTvPlay.getText().toString();
            if (s.equals(PLAY)) {
                mTvPlay.setText(PAUSE);
                Log.d("Pressed play: ", s);
            } else if (s.equals(PAUSE)) {
                mTvPlay.setText(RESUME);
                Log.d("Pressed pause: ", s);
            } else if (s.equals(RESUME)) {
                mTvPlay.setText(PAUSE);
                Log.d("Pressed resume: ", s);
            }

            listener.onPlayClicked();
        } else if (v.getId() == R.id.list_btn_die) {
            listener.onDieClicked();
        } else if (v.getId() == R.id.list_btn_reset) {
            mBtnTimer.setText(TIMER_DEFAULT);
            mTvPlay.setText(PLAY);
            mBtnDie.setText(DIE_DEFAULT);
            listener.onResetClicked();
        } else if (v.getId() == R.id.list_btn_randomize) {
            mAdapter.setCategories(mCategories.getRandomizedCategories());
            mAdapter.notifyDataSetChanged();
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(charSequence)){
            Log.d("editText ID: " + charSequence, "asdf");
            int id = Integer.parseInt(charSequence.toString());
            if (id < mCategories.getCategories().size()) {
                List<Category> cat = mCategories.getCategories();
                for (int z = 0; z < cat.size(); z++) {
                    Log.d("category id:" + cat.get(z).getId() + " category: " + cat.get(z).getCategory(), "asdf");
                }
                mAdapter.setCategories(mCategories.getListByID(id));
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void setTimerText(String text) {
        mBtnTimer.setText(text);
    }

    public String loadJSONFromAsset(@NonNull String assetName) {
        String json = null;
        try {
            InputStream is = getResources().getAssets().open(assetName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}