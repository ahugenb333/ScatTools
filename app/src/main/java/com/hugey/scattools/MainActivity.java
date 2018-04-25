package com.hugey.scattools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.squareup.leakcanary.LeakCanary;

import com.hugey.scattools.EditableList.EditableListView;
import com.hugey.scattools.List.ListView;
import com.hugey.scattools.Scat.ScatDie;
import com.hugey.scattools.Scat.ScatTimer;
import com.hugey.scattools.Scat.ScatView;
import com.hugey.scattools.Settings.Settings;
import com.hugey.scattools.Settings.SettingsView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ScatView.ScatViewListener, ListView.ListViewListener, EditableListView.EditableListViewListener, ScatTimer.TimerView, ScatDie.DieView, MenuItem.OnMenuItemClickListener {

    private TextView mTvList;
    private TextView mTvTools;
    private TextView mTvEditable;

    private boolean mIsTicking = false;
    private boolean mIsRolling = false;

    private MyPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    private ScatTimer mTimer;
    private ScatDie mDie;

    //local Settings copy for applying changes
    private Settings mSettings;

    private static final String DIE_DEFAULT = "!";
    private static final String COLOR_GRAY = "#707070";

    MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());

        mPlayer = MediaPlayer.create(this,  R.raw.phone);

        mTimer = new ScatTimer(this);
        mTimer.setTimerView(this);
        mDie = new ScatDie(this, getResources().getStringArray(R.array.letters));

        //reset settings each time you restart the app
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(SettingsView.KEY_ALPHABET);
        editor.remove(SettingsView.KEY_EXPIRE);
        editor.remove(SettingsView.KEY_SKIP);
        editor.remove(SettingsView.KEY_TIMER);
        editor.remove(SettingsView.KEY_TICK);
        editor.apply();
        PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, true);

        mSettings = new Settings();

        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mPagerAdapter.setScatListener(this);
        mPagerAdapter.setListListener(this);
        mPagerAdapter.setEditableListListener(this);

        mTvList = (TextView) findViewById(R.id.tv_bottom_nav_list);
        mTvTools = (TextView) findViewById(R.id.tv_bottom_nav_tools);
        mTvEditable = (TextView) findViewById(R.id.tv_bottom_nav_editable);

        mTvList.setOnClickListener(this);
        mTvTools.setOnClickListener(this);
        mTvEditable.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SettingsActivity.REQUEST_CODE) {
            Settings settings = data.getParcelableExtra(SettingsActivity.EXTRA_SETTINGS_OUT);

            //settings have changed, need to apply changes
            if (!mSettings.equals(settings)) {
                //see if we need to switch the alphabet on the ScatDie
                if (mSettings.isScatAlphabet() && !settings.isScatAlphabet()) {
                    mDie.removeDieCallbacks();
                    mDie.setLetters(getResources().getStringArray(R.array.letters_full));
                    mDie.resetCurrentLetter();
                    mIsRolling = false;
                } else if (!mSettings.isScatAlphabet() && settings.isScatAlphabet()) {
                    mDie.removeDieCallbacks();
                    mDie.setLetters(getResources().getStringArray(R.array.letters));
                    mDie.resetCurrentLetter();
                    mIsRolling = false;
                }
                //see if we need to skip the previous letter or not
                if (mSettings.getSkipPrevious() != settings.getSkipPrevious()) {
                    mDie.setSkipPrevious(settings.getSkipPrevious());
                }
                //see if the timer duration has changed
                if (!TextUtils.equals(mSettings.getTimerDuration(), settings.getTimerDuration())) {
                    mTimer.removeTimerCallbacks();
                    mTimer.setTimerDuration(settings.getTimerDuration());
                    mTimer.resetTimerProgress();

                    setIsTicking(ScatTimer.TICKING_PLAY);

                    mIsTicking = false;
                }
                //see if expire sound changed
                if (!TextUtils.equals(mSettings.getExpireSound(), settings.getExpireSound())) {
                    if (TextUtils.equals(settings.getExpireSound(), Settings.EXPIRE_SOUND_RING)) {
                        mPlayer = MediaPlayer.create(this, R.raw.phone);
                    } else if (TextUtils.equals(settings.getExpireSound(), Settings.EXPIRE_SOUND_ROOSTER)) {
                        mPlayer = MediaPlayer.create(this, R.raw.rooster);
                    } else if (TextUtils.equals(settings.getExpireSound(), Settings.EXPIRE_SOUND_TRAIN)) {
                        mPlayer = MediaPlayer.create(this, R.raw.train);
                    }
                    //update our new settings value
                    mSettings = settings;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_bottom_nav_tools) {
            mViewPager.setCurrentItem(0);
            mTvTools.setTextColor(Color.WHITE);
            mTvList.setTextColor(Color.parseColor(COLOR_GRAY));
            mTvEditable.setTextColor(Color.parseColor(COLOR_GRAY));
        }
        if (view.getId() == R.id.tv_bottom_nav_list) {
            mViewPager.setCurrentItem(1);
            mTvTools.setTextColor(Color.parseColor(COLOR_GRAY));
            mTvList.setTextColor(Color.WHITE);
            mTvEditable.setTextColor(Color.parseColor(COLOR_GRAY));
        }
        if (view.getId() == R.id.tv_bottom_nav_editable) {
            mViewPager.setCurrentItem(2);
            mTvTools.setTextColor(Color.parseColor(COLOR_GRAY));
            mTvList.setTextColor(Color.parseColor(COLOR_GRAY));
            mTvEditable.setTextColor(Color.WHITE);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.mn_options);
        menuItem.setIcon(android.R.drawable.ic_dialog_info);
        menuItem.setVisible(true);
        menuItem.setOnMenuItemClickListener(this);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scat_menu, menu);
        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Intent settingsIntent = SettingsActivity.getLaunchIntent(this, mSettings);

        startActivityForResult(settingsIntent, SettingsActivity.REQUEST_CODE);
        return false;
    }

    /**
     * @link ScatView.ScatViewListener
     **/
    @Override
    public void onPlayClicked() {
        if (!mIsTicking) {
            mTimer.postTimerDelay();
            setIsTicking(ScatTimer.TICKING_PAUSE);
        } else {
            mTimer.removeTimerCallbacks();
            setIsTicking(ScatTimer.TICKING_RESUME);
        }
        mIsTicking = !mIsTicking;
    }

    @Override
    public void onResetClicked() {
        mTimer.removeTimerCallbacks();
        mTimer.resetTimerProgress();

        mDie.removeDieCallbacks();

        setIsTicking(ScatTimer.TICKING_PLAY);

        setDieText(DIE_DEFAULT);

        mIsTicking = false;
    }

    @Override
    public void onDieClicked() {
        if (!mIsRolling) {
            mDie.postDieDelayed();
            mIsRolling = true;
        }
    }

    @Override
    public void setTimerText(@NonNull String text) {
        if (mSettings.shouldPlaySound(text)) {
            mPlayer.start();
        }
        ((ScatTimer.TimerView) mPagerAdapter.getItem(0)).setTimerText(text);
        ((ScatTimer.TimerView) mPagerAdapter.getItem(1)).setTimerText(text);
        ((ScatTimer.TimerView) mPagerAdapter.getItem(2)).setTimerText(text);
    }

    @Override
    public void setIsTicking(int ticking) {
        ((ScatTimer.TimerView) mPagerAdapter.getItem(0)).setIsTicking(ticking);
        ((ScatTimer.TimerView) mPagerAdapter.getItem(1)).setIsTicking(ticking);
        ((ScatTimer.TimerView) mPagerAdapter.getItem(2)).setIsTicking(ticking);
    }

    @Override
    public void setIsRolling(boolean rolling) {
        mIsRolling = rolling;
    }

    @Override
    public void setDieText(@NonNull String text) {
        ((ScatDie.DieView) mPagerAdapter.getItem(0)).setDieText(text);
        ((ScatDie.DieView) mPagerAdapter.getItem(1)).setDieText(text);
        ((ScatDie.DieView) mPagerAdapter.getItem(2)).setDieText(text);
    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;

        private ScatView mScatView;
        private ListView mListView;
        private EditableListView mEditableListView;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

            mScatView = new ScatView();
            mListView = new ListView();
            mEditableListView = new EditableListView();
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mScatView;
            }
            if (position == 1) {
                return mListView;
            }
            if (position == 2) {
                return mEditableListView;
            }
            return null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

        public void setScatListener(ScatView.ScatViewListener listener) {
            mScatView.setListener(listener);
        }

        public void setListListener(ListView.ListViewListener listener) {
            mListView.setListener(listener);
        }

        public void setEditableListListener(EditableListView.EditableListViewListener listener) {
            mEditableListView.setListener(listener);
        }
    }
}