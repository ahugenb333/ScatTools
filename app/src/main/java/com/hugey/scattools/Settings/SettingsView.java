package com.hugey.scattools.Settings;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.hugey.scattools.R;

/**
 * Created by user on 12/29/17.
 *
 * //TODO maintain a Settings object when this page goes up/down
 * **/

public class SettingsView extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

    public static final String KEY_SKIP = "switch_preference_skip_previous";
    public static final String KEY_ALPHABET = "list_preference_alphabet";
    public static final String KEY_TIMER = "list_preference_timer_duration";
    public static final String KEY_EXPIRE = "list_preference_expire_sound";
    public static final String KEY_TICK = "switch_preference_play_tick";

    private static final String OFFICAL_ALPHABET = "Official Alphabet";
    private static final String FULL_ALPHABET = "Full Alphabet";

    private Settings mSettings;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_preferences);

        Preference pSkip = getPreferenceManager().findPreference(KEY_SKIP);
        Preference pAlphabet = getPreferenceManager().findPreference(KEY_ALPHABET);
        Preference pTimer = getPreferenceManager().findPreference(KEY_TIMER);
        Preference pExpire = getPreferenceManager().findPreference(KEY_EXPIRE);
        Preference pTick = getPreferenceManager().findPreference(KEY_TICK);

        mSettings = new Settings();


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
            //preference.setSummary(o.toString()); Done automatically?
        }
        if (TextUtils.equals(key, KEY_ALPHABET)) {
            if (TextUtils.equals(o.toString(), OFFICAL_ALPHABET)) {
                mSettings.setAlphabetDefault(true);
            } else if (TextUtils.equals(o.toString(), FULL_ALPHABET)) {
                mSettings.setAlphabetDefault(false);
            }
        }

        return true;
    }

    public Settings getSettings() {
        return mSettings;
    }

    private boolean isListPreference(String key) {
        return (TextUtils.equals(key, KEY_ALPHABET) || TextUtils.equals(key, KEY_TIMER) || TextUtils.equals(key, KEY_EXPIRE));
    }
}
