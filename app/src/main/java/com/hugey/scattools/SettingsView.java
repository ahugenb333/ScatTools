package com.hugey.scattools;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by user on 12/29/17.
 *
 * //TODO maintain a Settings object when this page goes up/down
 * **/

public class SettingsView extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

    private static final String KEY_SKIP = "switch_preference_skip_previous";
    private static final String KEY_ALPHABET = "list_preference_alphabet";
    private static final String KEY_TIMER = "list_preference_timer_duration";
    private static final String KEY_EXPIRE = "list_preference_expire_sound";
    private static final String KEY_TICK = "switch_preference_play_tick";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_preferences);

        Preference pSkip = getPreferenceManager().findPreference(KEY_SKIP);
        Preference pAlphabet = getPreferenceManager().findPreference(KEY_ALPHABET);
        Preference pTimer = getPreferenceManager().findPreference(KEY_TIMER);
        Preference pExpire = getPreferenceManager().findPreference(KEY_EXPIRE);
        Preference pTick = getPreferenceManager().findPreference(KEY_TICK);


        if (pAlphabet != null) {
            pAlphabet.setOnPreferenceChangeListener(this);
        }
        if (pTimer != null) {
            pTimer.setOnPreferenceChangeListener(this);
        }
        if (pExpire != null) {
            pExpire.setOnPreferenceChangeListener(this);
        }
        if (pTick != null) {
            pTick.setOnPreferenceChangeListener(this);
        }
        if (pSkip != null) {
            pSkip.setOnPreferenceChangeListener(this);
        }

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();

        if (isListPreference(key)) {
            //preference.setSummary(o.toString());
        }

        return true;
    }

    private boolean isListPreference(String key) {
        return (TextUtils.equals(key, KEY_ALPHABET) || TextUtils.equals(key, KEY_TIMER) || TextUtils.equals(key, KEY_EXPIRE));
    }
}
