package com.hugey.scattools.Settings;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

//todo parcelable extra Settings object as result

public class SettingsActivity extends AppCompatActivity {

    public static final String EXTRA_SETTINGS_IN = "settings_in";
    public static final String EXTRA_SETTINGS_OUT = "settings_out";

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
