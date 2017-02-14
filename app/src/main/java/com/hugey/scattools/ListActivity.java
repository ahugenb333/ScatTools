package com.hugey.scattools;

import android.content.Context;
import android.os.Bundle;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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
            if (is != null) {
                Log.d("We made it!", "hoh");
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.d("no bueno", "hoh");
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
