package com.hugey.scattools;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

//todo parcelable extra Settings object as result

public class SettingsActivity extends AppCompatActivity {
    public static final int RESULT_OK = 111;
    public static final int RESULT_CANCEL = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scat_settings);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsView()).commit();
        //setupActionBar();
    }

//    private void setupActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            // Show the Up button in the action bar.
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                setResult(RESULT_CANCEL);
//                onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
