package com.hugey.scattools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 11/28/16.
 */

public class ListActivity extends AppCompatActivity {

    private Button mBtnTimer;
    private Button mBtnReset;
    private ListAdapter mAdapter;
    private RecyclerView mList;
    private Categories mCategories;
    
    private static final String EXTRA_PROGRESS = "progress";
    private static final String EXTRA_TICKING = "ticking";
    private static final String EXTRA_ROLLING = "rolling";
    private static final String EXTRA_TIME = "time";
    private static final String EXTRA_LETTER = "letter";
    
    public Intent getLaunchIntent(Context context, int progress, boolean ticking, boolean rolling, String currentTime, String currentLetter) {
        return new Intent(context, ListActivity.class)
                .putExtra("" + progress, EXTRA_PROGRESS)
                .putExtra("" + ticking, EXTRA_TICKING)
                .putExtra("" + rolling, EXTRA_ROLLING)
                .putExtra("" + currentTime, EXTRA_TIME)
                .putExtra("" + currentLetter, EXTRA_LETTER);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        int progress = getIntent().getIntExtra(MainActivity.EXTRA_PROGRESS, -1);
        String text = getIntent().getStringExtra(MainActivity.EXTRA_TEXT);

        mBtnTimer = (Button) findViewById(R.id.btn_timer);
        mBtnTimer.setText(text);


        Gson gson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();

        mCategories = gson.fromJson(loadJSONFromAsset("category.json"), Categories.class);


        List<Category> listOne = mCategories.getListByID(1);

        for (int i = 0; i < listOne.size(); i++) {
            Log.d("id: " + listOne.get(i).getId(), "   category asdf: " + listOne.get(i).getCategory());
        }

        mList = (RecyclerView) findViewById(R.id.list_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mList.setLayoutManager(manager);

        mAdapter = new ListAdapter(this);

        mList.setAdapter(mAdapter);


        mAdapter.setCategories(mCategories.getListByID(1));

        mAdapter.notifyDataSetChanged();

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
