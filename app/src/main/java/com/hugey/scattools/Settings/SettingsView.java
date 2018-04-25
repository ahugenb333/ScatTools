package com.hugey.scattools.Settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.hugey.scattools.R;

/**
 * Created by user on 12/29/17.
 * <p>
 * //TODO maintain a Settings object when this page goes up/down
 **/

public class SettingsView extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    public static final String KEY_SKIP = "switch_preference_skip_previous";
    public static final String KEY_ALPHABET = "list_preference_alphabet";
    public static final String KEY_TIMER = "list_preference_timer_duration";
    public static final String KEY_EXPIRE = "list_preference_expire_sound";
    public static final String KEY_TICK = "switch_preference_play_tick";

    private static final String OFFICAL_ALPHABET = "Official Alphabet";
    private static final String FULL_ALPHABET = "Full Alphabet";

    private Settings mSettings;

    private ListPreference pAlphabet;
    private SwitchPreference pSkip;
    private ListPreference pTimer;
    private ListPreference pExpire;
    private SwitchPreference pTick;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preferences);

        pAlphabet = (ListPreference) getPreferenceManager().findPreference(KEY_ALPHABET);
        pSkip = (SwitchPreference) getPreferenceManager().findPreference(KEY_SKIP);
        pTimer = (ListPreference) getPreferenceManager().findPreference(KEY_TIMER);
        pExpire = (ListPreference) getPreferenceManager().findPreference(KEY_EXPIRE);
        pTick = (SwitchPreference) getPreferenceManager().findPreference(KEY_TICK);

        if (mSettings != null) {
            initSettings();
        }

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

    private void initSettings() {
        if (mSettings.isScatAlphabet()) {
            pAlphabet.setValueIndex(0);
        } else {
            pAlphabet.setValueIndex(1);
        }

        if (mSettings.getSkipPrevious()) {
            pSkip.setChecked(true);
        } else {
            pSkip.setChecked(false);
        }

        if (mSettings.isTimerDuration200()) {
            pTimer.setValueIndex(0);
        } else if (mSettings.isTimerDuration230()) {
            pTimer.setValueIndex(1);
        } else if (mSettings.isTimerDuration300()) {
            pTimer.setValueIndex(2);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();

        if (TextUtils.equals(key, KEY_ALPHABET)) {
            if (TextUtils.equals(o.toString(), OFFICAL_ALPHABET)) {
                mSettings.setAlphabetDefault(true);
            } else if (TextUtils.equals(o.toString(), FULL_ALPHABET)) {
                mSettings.setAlphabetDefault(false);
            }
        } else if (TextUtils.equals(key, KEY_SKIP)) {
            if ((boolean) o) {
                mSettings.setSkipPrevious(true);
            } else {
                mSettings.setSkipPrevious(false);
            }
        } else if (TextUtils.equals(key, KEY_TIMER)) {
            mSettings.setTimerDuration(o.toString());
        } else if (TextUtils.equals(key, KEY_EXPIRE)) {
            mSettings.setExpireSound(o.toString());
        } else if (TextUtils.equals(key, KEY_TIMER)) {
            mSettings.setTickSounds((boolean) o);
        }
        return true;
    }

    public Settings getSettings() {
        return mSettings;
    }

    public void setSettings(Settings settings) {
        if (mSettings == null || !mSettings.equals(settings)) {
            mSettings = settings;
        }
    }
}