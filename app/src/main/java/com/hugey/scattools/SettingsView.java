package com.hugey.scattools;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by user on 12/29/17.
 */

public class SettingsView extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

    private static final String KEY_TIMER = "list_preference_timer_duration";
    private static final String KEY_ALPHABET = "list_preference_alphabet";
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_preferences);

        Preference pref = getPreferenceManager().findPreference(KEY_ALPHABET);

        if (pref != null) {
            pref.setOnPreferenceChangeListener(this);
        }

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();

        Log.d("Preference String: ", key);


        return true;

    }
}
