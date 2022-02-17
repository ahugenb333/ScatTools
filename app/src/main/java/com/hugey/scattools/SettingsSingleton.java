package com.hugey.scattools;

import com.hugey.scattools.Settings.Settings;

public class SettingsSingleton {

    private static SettingsSingleton INSTANCE = null;

    private Settings mSettings;

    private SettingsSingleton(){
        mSettings = new Settings();
    }

    public static SettingsSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsSingleton();
        }
        return INSTANCE;
    }

    public void setSettings(Settings settings){
        mSettings = settings;
    }

    public Settings getSettings(){
        return mSettings;
    }
}
