package com.hugey.scattools;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 11/28/16.
 */

public class ListActivity extends AppCompatActivity {


    private Button mBtnTimer;
    private Button mBtnReset;
    private ArrayList<Category> categories;
    private RecyclerView mCategoryView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("Category.json"));
            List<Category> data = gson.fromJson(reader, Category.class);

            Log.d("data asdf: " + data.size(), " size");
        }
        catch (FileNotFoundException fnf) {
            Log.d("FNF ", fnf.toString());
            finish();
        }
    }




}
