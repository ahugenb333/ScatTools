package com.hugey.scattools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hugey.scattools.Settings.Settings;
import com.hugey.scattools.Settings.SettingsView;

//todo parcelable extra Settings object as result

public class SettingsActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 421;

    public static final String EXTRA_SETTINGS_IN = "settings_in";
    public static final String EXTRA_SETTINGS_OUT = "settings_out";

    public static Intent getLaunchIntent(Context context, Settings settings) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra(EXTRA_SETTINGS_IN, settings);
        return intent;
    }

    private SettingsView mSettingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettingsView = new SettingsView();

        Settings settings = getIntent().getParcelableExtra(EXTRA_SETTINGS_IN);

        if (settings != null) {
            mSettingsView.setSettings(settings);
        }

        getFragmentManager().beginTransaction().replace(android.R.id.content, mSettingsView).commit();
        setupActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra(EXTRA_SETTINGS_OUT, mSettingsView.getSettings());
                setResult(RESULT_OK, intent);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
